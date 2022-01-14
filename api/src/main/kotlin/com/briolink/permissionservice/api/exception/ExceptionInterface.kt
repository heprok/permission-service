package com.briolink.permissionservice.api.exception

import org.springframework.http.HttpStatus

interface ExceptionInterface {
    val code: String // i18n code
    val httpsStatus: HttpStatus
    val errorResponse: ErrorResponse
        get() = ErrorResponse(
            message = this.code,
            status = this.httpsStatus.value(),
        )
}
