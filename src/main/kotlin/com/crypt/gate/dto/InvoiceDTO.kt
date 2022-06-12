package com.crypt.gate.dto

import com.crypt.gate.model.PaymentCurrency
import com.crypt.gate.model.PaymentStatus
import java.math.BigDecimal

data class InvoiceDTO(
    val id: String,
    val status: PaymentStatus,
    val currency: PaymentCurrency,
    val amount: BigDecimal
)