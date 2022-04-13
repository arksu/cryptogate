package com.crypt.gate.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Создание и работа с платежами
 */
@RestController
@RequestMapping("/api/payment")
class PaymentController {
    @PostMapping
    fun createPayment() {

    }
}