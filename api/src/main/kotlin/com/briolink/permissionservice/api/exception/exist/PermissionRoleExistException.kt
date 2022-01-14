package com.briolink.permissionservice.api.exception.exist

class PermissionRoleExistException() : AbstractExistsException() {
    override val code: String = "permission-role.exist"
}
