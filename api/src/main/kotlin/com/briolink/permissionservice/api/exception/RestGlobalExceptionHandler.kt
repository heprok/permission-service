package com.briolink.permissionservice.api.exception

import com.briolink.permissionservice.api.util.LocaleMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class RestGlobalExceptionHandler(
    private val localeMessage: LocaleMessage
) {
    @ExceptionHandler(
        value = [
            ExistsUserIdAndAccessObjectIdException::class,
            ExistsUserIdAndAccessObjectIdAndRightIdException::class
        ]
    )
    fun existsUserIdAndAccessObjectException(ex: Exception): ResponseEntity<*> {
        return ResponseEntity(ex.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun notFoundException(ex: EntityNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [BindException::class])
    fun validationBindException(ex: BindException): ResponseEntity<Any> {

        val msg = ex.fieldErrors.joinToString("\n") {
            it.field + ": " + it.defaultMessage?.let { key -> localeMessage.getMessage(key) }
        }
        return ResponseEntity(msg, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationException(ex: ConstraintViolationException): ResponseEntity<Any> {
        return ResponseEntity(ex.message, HttpStatus.NOT_ACCEPTABLE)
    }
}
