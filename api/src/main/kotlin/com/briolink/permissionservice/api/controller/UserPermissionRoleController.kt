package com.briolink.permissionservice.api.controller

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.exception.PermissionRoleNotFoundException
import com.briolink.permissionservice.api.jpa.entity.UserPermissionRoleEntity
import com.briolink.permissionservice.api.service.UserPermissionRoleService
import com.briolink.permissionservice.api.validation.ValidUUID
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.constraints.NotNull

@RestController
@Validated
@RequestMapping("/api/v1/user_permission_roles")
@Api(description = "User permission role")
@ApiResponses(
    ApiResponse(code = 200, message = "Role found"),
//        ApiResponse(code = 201, message = "Role created"),
    ApiResponse(code = 204, message = "Role deleted"),
    ApiResponse(code = 403, message = "Unauthorized"),
    ApiResponse(code = 404, message = "Role not found"),
    ApiResponse(code = 406, message = "Properties not valid"),
//        ApiResponse(code = 409, message = "Role exists"),
)
class UserPermissionRoleController(
    private val userPermissionRoleService: UserPermissionRoleService,
) {

    @GetMapping("/")
    @ApiOperation("Get user permission role by user Id and object id, object type")
    @Throws(PermissionRoleNotFoundException::class)
    fun get(
        @NotNull @ValidUUID @ApiParam(value = "User id", example = "5332b172-d84c-4643-9b16-98366bb03e22", required = true) userId: String,
        @NotNull @ValidUUID @ApiParam(
            value = "Object id",
            example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c",
            required = true,
        ) accessObjectId: String,
        @NotNull(message = "permission-role.access-object-type.not-null") @ApiParam(
            value = "Object type",
            required = true,
        ) accessObjectType: AccessObjectTypeEnum,
    ): UserPermissionRoleEntity = userPermissionRoleService.find(UUID.fromString(userId), UUID.fromString(accessObjectId)).orElseThrow {
        throw PermissionRoleNotFoundException(UUID.fromString(userId), UUID.fromString(accessObjectId))
    }

    @Throws(PermissionRoleNotFoundException::class)
    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete user permission role by user id and object id, object type ")
    fun delete(
        @NotNull @ValidUUID @ApiParam(value = "User id", example = "5332b172-d84c-4643-9b16-98366bb03e22", required = true) userId: String,
        @NotNull @ValidUUID @ApiParam(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c") accessObjectId: String,
        @NotNull(message = "permission-role.access-object-type.not-null") @ApiParam(
            value = "Object type",
            required = true,
        ) accessObjectType: AccessObjectTypeEnum,
    ) {
        if (userPermissionRoleService.delete(UUID.fromString(userId), accessObjectType, UUID.fromString(accessObjectId))
            .toInt() == 0
        ) throw PermissionRoleNotFoundException(UUID.fromString(userId), UUID.fromString(accessObjectId))
    }
}
