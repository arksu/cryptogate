package com.crypt.gate.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*


class ApiError {
    val status: HttpStatus
    val message: String?
    val errors: List<String>

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    val timestamp: Date = Date()

    constructor(status: HttpStatus, message: String?, errors: List<String>) {
        this.status = status
        this.message = message
        this.errors = errors
    }

    constructor(status: HttpStatus, message: String?, error: String) {
        this.status = status
        this.message = message
        this.errors = listOf(error)
    }
}