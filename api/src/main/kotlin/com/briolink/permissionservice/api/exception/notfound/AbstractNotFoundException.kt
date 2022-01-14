package com.briolink.permissionservice.api.exception.notfound

import com.briolink.permissionservice.api.exception.ExceptionInterface
import org.springframework.http.HttpStatus
import javax.persistence.EntityNotFoundException

abstract class AbstractNotFoundException() : EntityNotFoundException(), ExceptionInterface {
    override val httpsStatus = HttpStatus.NO_CONTENT
}
