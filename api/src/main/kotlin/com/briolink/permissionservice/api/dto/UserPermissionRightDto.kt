package com.briolink.permissionservice.api.dto

import com.briolink.permissionservice.api.enumeration.AccessObjectTypeEnum
import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import com.briolink.permissionservice.api.enumeration.PermissionRoleEnum
import java.util.UUID

data class UserPermissionRightDto(
    val userId: UUID,
    val accessObjectType: AccessObjectTypeEnum,
    val accessObjectId: UUID,
    val permissionRightEnum: PermissionRightEnum,
    val permissionRoleEnum: PermissionRoleEnum
)
