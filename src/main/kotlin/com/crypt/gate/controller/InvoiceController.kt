package com.crypt.gate.controller

import com.crypt.gate.config.CryptogateConfig
import com.crypt.gate.dto.CreateInvoiceDTO
import com.crypt.gate.dto.InvoiceDTO
import com.crypt.gate.dto.toInvoiceDTO
import com.crypt.gate.exception.ResourceNotFoundException
import com.crypt.gate.model.Invoice
import com.crypt.gate.model.PaymentStatus
import com.crypt.gate.repo.InvoiceRepo
import com.crypt.gate.repo.MerchantRepo
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
@RequestMapping("/api/invoice")
class InvoiceController(
    val invoiceRepo: InvoiceRepo,
    val merchantRepo: MerchantRepo,
    val config: CryptogateConfig
) {
    /**
     * Создать счет на оплату
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPayment(@Valid @RequestBody dto: CreateInvoiceDTO): InvoiceDTO {
        return toInvoiceDTO(
            invoiceRepo.save(
                Invoice(
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
     * Получить счет по его ид (проверить статус и тд)
     */
    @GetMapping("{id}")
    fun getInvoice(@PathVariable id: String): InvoiceDTO {
        // TODO проверять можно ли смотреть статус этого ид (надо указать данные мерчанта)
        val invoice = invoiceRepo.findByHash(id) ?: throw ResourceNotFoundException("Invoice not found")
        return toInvoiceDTO(invoice)
    }
}