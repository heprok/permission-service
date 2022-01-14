package com.briolink.permissionservice.api.exception.exist

import com.briolink.permissionservice.api.exception.ExceptionInterface
import org.springframework.http.HttpStatus

abstract class AbstractExistsException() : RuntimeException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.CONFLICT
}
