package com.briolink.permissionservice.api.exception

import com.briolink.permissionservice.api.exception.exist.PermissionRightExistException
import com.briolink.permissionservice.api.exception.exist.PermissionRoleExistException
import com.briolink.permissionservice.api.util.LocaleMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@ControllerAdvice
@RequestMapping(produces = ["application/json"])
class RestGlobalExceptionHandler(private val localeMessage: LocaleMessage) {

    private fun getResponseEntityWithTranslateMessage(ex: ExceptionInterface) =
        ResponseEntity<ErrorResponse>(ex.errorResponse.apply { message = localeMessage.getMessage(ex.code) }, ex.httpsStatus)

    @ExceptionHandler(
        value = [
            PermissionRoleExistException::class,
            PermissionRightExistException::class,
        ],
    )
    fun existsException(ex: ExceptionInterface): ResponseEntity<ErrorResponse> {
        return getResponseEntityWithTranslateMessage(ex)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun notFoundException(ex: ExceptionInterface): ResponseEntity<ErrorResponse> {
        return getResponseEntityWithTranslateMessage(ex)
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
