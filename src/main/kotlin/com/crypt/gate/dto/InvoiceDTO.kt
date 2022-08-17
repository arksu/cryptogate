package com.crypt.gate.dto

import com.crypt.gate.model.CryptoCurrency
import com.crypt.gate.model.PaymentStatus
import java.math.BigDecimal

data class InvoiceDTO(
    val id: String,
    val status: PaymentStatus,
    val currency: CryptoCurrency,
    val amount: BigDecimal
)