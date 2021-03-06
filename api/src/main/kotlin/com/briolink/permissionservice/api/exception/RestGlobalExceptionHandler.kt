package com.briolink.permissionservice.api.exception

import com.briolink.lib.permission.exception.ErrorResponse
import com.briolink.lib.permission.exception.ExceptionInterface
import com.briolink.lib.permission.exception.exist.PermissionRightExistException
import com.briolink.lib.permission.exception.exist.PermissionRoleExistException
import com.briolink.lib.permission.exception.notfound.AbstractNotFoundException
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

    @ExceptionHandler(value = [EntityNotFoundException::class, AbstractNotFoundException::class])
    fun notFoundException(ex: ExceptionInterface): ResponseEntity<ErrorResponse> {
        return getResponseEntityWithTranslateMessage(ex)
    }

    @ExceptionHandler(value = [BindException::class])
    fun validationBindException(ex: BindException): ResponseEntity<ErrorResponse> {

        val msg = ex.fieldErrors.joinToString("\n") {
            it.field + ": " + it.defaultMessage?.let { key -> localeMessage.getMessage(key) }
        }
        return ResponseEntity(ErrorResponse(message = msg, status = HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(message = ex.message, status = HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE)
    }
}
