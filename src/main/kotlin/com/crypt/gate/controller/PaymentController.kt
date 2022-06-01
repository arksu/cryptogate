package com.crypt.gate.controller

import com.crypt.gate.config.CryptogateConfig
import com.crypt.gate.dto.CreatePaymentDTO
import com.crypt.gate.dto.PaymentDTO
import com.crypt.gate.dto.toPaymentDTO
import com.crypt.gate.exception.ResourceNotFoundException
import com.crypt.gate.model.Payment
import com.crypt.gate.model.PaymentStatus
import com.crypt.gate.repo.MerchantRepo
import com.crypt.gate.repo.PaymentRepo
import com.crypt.gate.util.Eth
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Создание и работа с платежами
 */
@CrossOrigin
@RestController
@RequestMapping("/api/payment")
class PaymentController(
    val paymentRepo: PaymentRepo,
    val merchantRepo: MerchantRepo,
    val config: CryptogateConfig
) {
    /**
     * Создать платеж
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPayment(@Valid @RequestBody dto: CreatePaymentDTO): PaymentDTO {
//        println(config.eth.wallets)
        return toPaymentDTO(
            paymentRepo.save(
                Payment(
                    currency = dto.currency,
                    amount = Eth.bigDecimalToBigInteger(dto.amount),
                    merchant = merchantRepo.findBySecretKey(dto.secretKey!!),
                    status = PaymentStatus.WAITING,
                    callbackUrl = dto.callbackUrl!!,
                    walletAddress = "!2" // TODO select wallet
                )
            )
        )
    }

    /**
     * Получить платеж по его ид (проверить статус и тд)
     */
    @GetMapping("{id}")
    fun getPayment(@PathVariable id: String): PaymentDTO {
        // TODO проверять можно ли смотреть статус этого ид (надо указать данные мерчанта)
        val payment = paymentRepo.findById(id.toLong())
        return toPaymentDTO(payment.orElseThrow { ResourceNotFoundException("payment not found") })
    }
}