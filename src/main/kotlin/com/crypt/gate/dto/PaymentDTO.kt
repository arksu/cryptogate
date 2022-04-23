package com.crypt.gate.dto

import com.crypt.gate.model.PaymentCurrency
import com.crypt.gate.model.PaymentStatus
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentDTO(
    val id: Long,
    val currency: PaymentCurrency,
    val amount: BigDecimal,
    val merchantId: Long,
    val status: PaymentStatus?
)