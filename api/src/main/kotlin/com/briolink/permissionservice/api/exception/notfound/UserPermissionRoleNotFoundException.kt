package com.briolink.permissionservice.api.exception.notfound

class UserPermissionRoleNotFoundException() : AbstractNotFoundException() {
    override val code: String = "user-permission-role.not-found"
}
