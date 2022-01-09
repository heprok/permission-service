package com.briolink.permissionservice.api.dto

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import com.briolink.permissionservice.api.enumeration.PermissionRoleEnum
import io.swagger.annotations.ApiModelProperty
import java.util.UUID
import javax.validation.constraints.NotNull

data class UserPermissionRightDto(

    @get:NotNull(message = "permission-right.access-object-type.not-null")
    @ApiModelProperty(value = "Object type", required = true)
    val accessObjectType: AccessObjectTypeEnum?,

    @get:NotNull(message = "permission-right.access-object-id.not-null")
    @ApiModelProperty(value = "Object id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c")
    val accessObjectId: UUID?,

    @get:NotNull(message = "permission-right.permission-role.not-null")
    @ApiModelProperty(value = "Permission role", required = true)
    val permissionRole: PermissionRoleEnum?,

    @get:NotNull(message = "permission-right.permission-right.not-null")
    @ApiModelProperty(value = "Permission right", required = true)
    val permissionRight: PermissionRightEnum?,

    @get:NotNull(message = "permission-right.user-id.not-null")
    @ApiModelProperty(value = "User id", example = "d0a2312e-2d63-4404-b215-7ef94bebdc5c", required = true)
    val userId: UUID?,
)
