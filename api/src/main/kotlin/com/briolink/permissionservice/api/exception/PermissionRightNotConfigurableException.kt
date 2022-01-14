package com.briolink.permissionservice.api.exception

import org.springframework.http.HttpStatus

class PermissionRightNotConfigurableException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.CONFLICT
    override val code: String = "permission-right.not-configurable"
}
