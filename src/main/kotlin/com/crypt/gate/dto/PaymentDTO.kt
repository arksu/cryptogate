package com.crypt.gate.dto

import com.crypt.gate.model.PaymentCurrency
import java.math.BigDecimal

data class PaymentDTO(
    val id : Long,
    val currency: PaymentCurrency,
    val amount: BigDecimal,
    val merchantId: Long
)