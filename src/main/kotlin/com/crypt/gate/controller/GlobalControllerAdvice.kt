package com.crypt.gate.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer


@ControllerAdvice
class GlobalControllerAdvice : ResponseEntityExceptionHandler() {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong number format")
    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException() {
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return super.handleMethodArgumentNotValid(ex, headers, status, request)
    }

}