package com.crypt.gate.controller

import com.crypt.gate.dto.PaymentDTO
import com.crypt.gate.dto.toPaymentDTO
import com.crypt.gate.exception.ResourceNotFoundException
import com.crypt.gate.model.Payment
import com.crypt.gate.model.PaymentStatus
import com.crypt.gate.repo.MerchantRepo
import com.crypt.gate.repo.PaymentRepo
import com.crypt.gate.util.Eth
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Создание и работа с платежами
 */
@RestController
@RequestMapping("/api/payment")
class PaymentController(
    val paymentRepo: PaymentRepo,
    val merchantRepo: MerchantRepo
) {
    /**
     * Создать платеж
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createPayment(@RequestBody paymentDTO: PaymentDTO): PaymentDTO {
        return toPaymentDTO(
            paymentRepo.save(
                Payment(
                    currency = paymentDTO.currency,
                    amount = Eth.bigDecimalToBigInteger(paymentDTO.amount),
                    merchant = merchantRepo.getReferenceById(paymentDTO.merchantId),
                    status = PaymentStatus.WAITING
                )
            )
        )
    }

    @GetMapping("{id}")
    fun getPayment(@PathVariable id: String): PaymentDTO {
        val payment = paymentRepo.findById(id.toLong())
        return toPaymentDTO(payment.orElseThrow { ResourceNotFoundException() })
    }
}