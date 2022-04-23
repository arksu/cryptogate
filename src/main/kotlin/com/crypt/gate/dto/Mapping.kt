package com.crypt.gate.dto

import com.crypt.gate.model.Payment
import com.crypt.gate.util.Eth.bigIntegerToBigDecimal

fun toPaymentDTO(payment: Payment): PaymentDTO {
    return PaymentDTO(payment.id, payment.currency, bigIntegerToBigDecimal(payment.amount), payment.merchant.id, payment.status)
}