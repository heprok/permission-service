package com.briolink.permissionservice.api.controller

import com.briolink.lib.permission.exception.PermissionRightNotConfigurableException
import com.briolink.lib.permission.exception.notfound.PermissionRightNotFoundException
import com.briolink.permissionservice.api.dto.UserPermissionRightsDto
import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRightEntity
import com.briolink.permissionservice.api.service.UserPermissionRightService
import com.briolink.permissionservice.api.validation.ValidUUID
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@Validated
@RequestMapping("/api/v1/user_permission_rights")
@Api(description = "User permission rights")
@ApiResponses(
    ApiResponse(code = 200, message = "Right found"),
    ApiResponse(code = 201, message = "Right created"),
    ApiResponse(code = 204, message = "Right not found"),
    ApiResponse(code = 403, message = "Unauthorized"),
    ApiResponse(code = 406, message = "Properties not valid"),
    ApiResponse(code = 409, message = "Right exists"),
)
class UserPermissionRightController(
    private val userPermissionRightService: UserPermissionRightService,
) {

    @GetMapping("/")
    @ApiOperation("Get enabled user permission rights by user Id and object id")
    @Throws(PermissionRightNotFoundException::class)
    fun get(
        @NotNull @ValidUUID @ApiParam(value = "User id", example = "5332b172-d84c-4643-9b16-98366bb03e22", required = true)
        userId: String,
        @NotNull @ValidUUID @ApiParam(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c", required = true)
        accessObjectId: String,
    ): List<UserPermissionRightEntity> =
        userPermissionRightService.findByUserIdAndAccessObjectIdAndEnabled(
            UUID.fromString(userId),
            UUID.fromString(accessObjectId),
        ).also {
            if (it.isEmpty()) throw PermissionRightNotFoundException()
        }

    @Throws(PermissionRightNotConfigurableException::class, PermissionRightNotFoundException::class)
    @PutMapping("/")
    @ApiOperation("Enable user permission right by user id and object id")
    fun enabled(
        @NotNull @ValidUUID @ApiParam(value = "User id", example = "5332b172-d84c-4643-9b16-98366bb03e22", required = true)
        userId: String,
        @NotNull @ValidUUID @ApiParam(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c", required = true)
        accessObjectId: String,
        @NotNull(message = "permission-right.permission-right.not-null")
        @ApiParam(value = "Permission right", required = true)
        permissionRight: PermissionRightEnum,
        @NotNull(message = "permission-right.enabled.not-null")
        @ApiParam(value = "Enabled", required = true)
        enabled: Boolean
    ) = ResponseEntity<UserPermissionRightEntity>(
        userPermissionRightService.enablePermissionRight(
            UUID.fromString(userId),
            UUID.fromString(accessObjectId),
            permissionRight,
            enabled,
        ),
        HttpStatus.OK,
    )

    @PostMapping("/")
    @ApiOperation("Set permission rights by userId and objectId if right configurable")
    fun setRights(
        @Valid @RequestBody dto: UserPermissionRightsDto
    ): List<UserPermissionRightEntity> {
        return userPermissionRightService.setPermissionRights(
            userId = dto.userId,
            accessObjectId = dto.accessObjectId,
            permissionRole = dto.permissionRole,
            accessObjectTypeEnum = dto.accessObjectType,
            listEnabledPermissionRight = dto.permissionRights,
        )
    }

    @GetMapping("/check-permission/", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation("Check user permission right by user id and object id")
    @ResponseBody
    fun checkPermission(
        @NotNull @ValidUUID @ApiParam(value = "User id", example = "5332b172-d84c-4643-9b16-98366bb03e22", required = true)
        userId: String,
        @NotNull @ValidUUID @ApiParam(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c", required = true)
        accessObjectId: String,
        @NotNull(message = "permission-right.permission-right.not-null")
        @ApiParam(value = "Permission right", required = true)
        permissionRight: PermissionRightEnum
    ): Boolean =
        userPermissionRightService.existsByUserIdAndAccessObjectIdAndRightId(
            UUID.fromString(userId),
            UUID.fromString(accessObjectId),
            permissionRight,
            true,
        )
}
