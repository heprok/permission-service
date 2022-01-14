package com.briolink.permissionservice.api.exception.notfound

class PermissionRoleNotFoundException() : AbstractNotFoundException() {
    override val code: String = "permission-role.not-found"
}
