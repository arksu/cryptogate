package com.crypt.gate.controller

import com.crypt.gate.dto.PaymentDTO
import com.crypt.gate.model.Payment
import com.crypt.gate.repo.MerchantRepo
import com.crypt.gate.repo.PaymentRepo
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

/**
 * Создание и работа с платежами
 */
@RestController
@RequestMapping("/api/payment")
class PaymentController(
    val paymentRepo: PaymentRepo,
    val merchantRepo: MerchantRepo
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPayment(@RequestBody paymentDTO: PaymentDTO): PaymentDTO {

        paymentRepo.save(
            Payment(
                currency = paymentDTO.currency,
                amount = paymentDTO.amount.multiply(BigDecimal(1000000000000000000L)).toBigInteger(),
                merchant = merchantRepo.getReferenceById(paymentDTO.merchantId)
            )
        )

        return paymentDTO
    }
}