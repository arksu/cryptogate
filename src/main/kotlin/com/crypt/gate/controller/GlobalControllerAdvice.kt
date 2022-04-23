package com.crypt.gate.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalControllerAdvice : ResponseEntityExceptionHandler() {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong number format")
    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException() {
    }
}