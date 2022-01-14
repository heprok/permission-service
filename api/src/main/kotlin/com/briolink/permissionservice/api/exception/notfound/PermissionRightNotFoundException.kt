package com.briolink.permissionservice.api.exception.notfound

class PermissionRightNotFoundException() : AbstractNotFoundException() {
    override val code: String = "permission-right.not-found"
}
