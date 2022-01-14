package com.briolink.permissionservice.api.exception.exist

class PermissionRightExistException() : AbstractExistsException() {
    override val code: String = "permission-right.permission-right.exist"
}
