package com.briolink.permissionservice.api.exception

import com.briolink.permissionservice.api.enumeration.PermissionRightEnum
import com.briolink.permissionservice.api.enumeration.PermissionRoleEnum

class PermissionRightDontConfigurableException(
    private val permissionRightEnum: PermissionRightEnum,
    private val permissionRoleEnum: PermissionRoleEnum
) : Exception() {
    override val message: String
        get() = "Rights ${permissionRightEnum.name} don`t configurable with role ${permissionRoleEnum.name}"
}
