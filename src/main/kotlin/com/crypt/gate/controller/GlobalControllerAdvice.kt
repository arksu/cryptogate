package com.crypt.gate.controller

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


@ControllerAdvice
class GlobalControllerAdvice : ResponseEntityExceptionHandler() {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong number format")
    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException() {
    }

//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun bbb(): ResponseEntity<Any> {
//        return ResponseEntity(HttpStatus.CREATED)
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptionsFF(ex: MethodArgumentNotValidException, request: WebRequest): Map<String, String?>? {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        }
        return errors
    }


//    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//        return ResponseEntity("fd " + ex.message, HttpStatus.BAD_REQUEST)
//        return super.handleMethodArgumentNotValid(ex, headers, status, request)
//    }

//    protected fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders?, status: HttpStatus?, request: WebRequest?): ResponseEntity<Any?>? {
//        val fieldErrors = ex.bindingResult.fieldErrors
//        val globalErrors = ex.bindingResult.globalErrors
//        val errors: MutableList<String> = ArrayList(fieldErrors.size + globalErrors.size)
//        var error: String
//        for (fieldError in fieldErrors) {
//            error = fieldError.field + ", " + fieldError.defaultMessage
//            errors.add(error)
//        }
//        for (objectError in globalErrors) {
//            error = objectError.objectName + ", " + objectError.defaultMessage
//            errors.add(error)
//        }
//        val errorMessage = ErrorMessage(errors)
//        return ResponseEntity<Any?>(errorMessage, headers, status)
//    }

}