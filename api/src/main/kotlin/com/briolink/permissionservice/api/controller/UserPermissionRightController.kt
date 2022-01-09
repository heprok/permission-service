package com.briolink.permissionservice.api.controller

import com.briolink.permissionservice.api.dto.DefaultPermissionRightDto
import com.briolink.permissionservice.api.dto.UserPermissionRightDto
import com.briolink.permissionservice.api.exception.ExistsUserIdAndAccessObjectIdAndRightIdException
import com.briolink.permissionservice.api.exception.ExistsUserIdAndAccessObjectIdException
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
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.persistence.EntityNotFoundException
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@Validated
@RequestMapping("/api/v1/user_permission_rights")
@Api(description = "User permission rights")
@ApiResponses(
    ApiResponse(code = 200, message = "Right found"),
    ApiResponse(code = 201, message = "Right created"),
    ApiResponse(code = 403, message = "Unauthorized"),
    ApiResponse(code = 404, message = "Right not found"),
    ApiResponse(code = 406, message = "Properties not valid"),
    ApiResponse(code = 409, message = "Right exists"),
)
class UserPermissionRightController(
    private val userPermissionRightService: UserPermissionRightService,
) {

    @GetMapping("/")
    @ApiOperation("User permission right by user Id and object id")
    @Throws(EntityNotFoundException::class)
    fun get(
        @NotNull @ValidUUID @ApiParam(
            value = "User id",
            example = "5332b172-d84c-4643-9b16-98366bb03e22",
            required = true
        )
        userId: String,
        @NotNull @ValidUUID @ApiParam(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c")
        accessObjectId: String,
    ): List<UserPermissionRightEntity> =
        userPermissionRightService.findByUserIdAndAccessObjectId(
            UUID.fromString(userId),
            UUID.fromString(accessObjectId)
        ).also {
            if (it.isEmpty()) throw EntityNotFoundException("User permission right not found")
        }

    @PostMapping("/")
    @Throws(ExistsUserIdAndAccessObjectIdAndRightIdException::class, EntityNotFoundException::class)
    fun create(
        @Valid @RequestBody dto: UserPermissionRightDto
    ): ResponseEntity<UserPermissionRightEntity> {
        with(dto) {
            if (userId == null || accessObjectType == null || permissionRole == null || accessObjectId == null || permissionRight == null)
                throw RuntimeException("Properties must be not null in permissionRightDto")

            return ResponseEntity(
                userPermissionRightService.create(
                    userId = userId,
                    accessObjectId = accessObjectId,
                    accessObjectTypeEnum = accessObjectType,
                    permissionRoleEnum = permissionRole,
                    permissionRightEnum = permissionRight
                ),
                HttpStatus.CREATED
            )
        }
    }

    @Throws(ExistsUserIdAndAccessObjectIdException::class)
    @PostMapping("/default/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createDefault(
        @Valid @RequestBody dto: DefaultPermissionRightDto
    ): List<UserPermissionRightEntity> {
        with(dto) {
            if (userId == null || accessObjectId == null || accessObjectType == null || permissionRole == null)
                throw RuntimeException("Properties must be not null in defaultPermissionRightDto")

            return userPermissionRightService.createFromDefault(
                userId = userId,
                accessObjectId = accessObjectId,
                accessObjectTypeEnum = accessObjectType,
                permissionRoleEnum = permissionRole
            )
        }
    }
}
