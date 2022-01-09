package com.briolink.permissionservice.api.dto

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.enumeration.PermissionRoleEnum
import io.swagger.annotations.ApiModelProperty
import java.util.UUID
import javax.validation.constraints.NotNull

data class DefaultPermissionRightDto(
    @get:NotNull(message = "default-permission-right.access-object-type.invalid")
    @ApiModelProperty(value = "Object type", required = true)
    val accessObjectType: AccessObjectTypeEnum?,

    @get:NotNull(message = "default-permission-right.access-object-id.invalid")
    @ApiModelProperty(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c")
    val accessObjectId: UUID?,

    @get:NotNull(message = "default-permission-right.permission-role.invalid")
    @ApiModelProperty(value = "Permission role", required = true)
    val permissionRole: PermissionRoleEnum?,

    @get:NotNull(message = "default-permission-right.user-id.invalid")
    @ApiModelProperty(value = "User id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c", required = true)
    val userId: UUID?,
)
