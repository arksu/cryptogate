package com.crypt.gate.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping

@ControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(NumberFormatException::class)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun handleNumberFormatException(): ResponseEntity<String> {

        // TODO error message json body
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("number")
    }
}