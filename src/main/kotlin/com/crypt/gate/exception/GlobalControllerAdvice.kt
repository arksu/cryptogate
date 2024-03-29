package com.crypt.gate.exception

import com.crypt.gate.dto.ApiError
import com.crypt.gate.util.LoggerDelegate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalControllerAdvice {
    val log by LoggerDelegate()

    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException(ex: NumberFormatException): ResponseEntity<Any?>? {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST, ex.localizedMessage, "Wrong number format"
        )
        return ResponseEntity(apiError, apiError.status)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<Any?>? {
        val apiError = ApiError(
            HttpStatus.NOT_FOUND, ex.localizedMessage, ex.localizedMessage
        )
        return ResponseEntity(apiError, apiError.status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun handleNotFound(ex: IllegalArgumentException): ResponseEntity<ApiError>? {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST, ex.localizedMessage, ex.localizedMessage
        )
        return ResponseEntity(apiError, apiError.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun handleNotValid(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val fieldErrors = ex.bindingResult.fieldErrors
        val globalErrors = ex.bindingResult.globalErrors
        val errors: MutableList<String> = ArrayList(fieldErrors.size + globalErrors.size)
        for (fieldError in fieldErrors) {
            errors.add("${fieldError.field} : ${fieldError.defaultMessage}, rejected value [${fieldError.rejectedValue}]")
        }
        for (objectError in globalErrors) {
            errors.add("${objectError.objectName} : ${objectError.defaultMessage}")
        }

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)

        return ResponseEntity(
            apiError, apiError.status
        )
    }

    @ExceptionHandler(Throwable::class)
    fun handleAll(ex: Throwable): ResponseEntity<Any?>? {
        log.error("Throwable: ${ex.message}", ex)
        val apiError = ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, "error occurred"
        )
        return ResponseEntity(
            apiError, apiError.status
        )
    }
}