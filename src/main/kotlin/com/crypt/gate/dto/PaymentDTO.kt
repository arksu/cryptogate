package com.crypt.gate.dto

import com.crypt.gate.model.PaymentCurrency
import com.crypt.gate.model.PaymentStatus
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@JsonInclude(JsonInclude.Include.NON_NULL)
class PaymentDTO(
    val id: Long,
    val currency: PaymentCurrency,
    val amount: BigDecimal,
    val merchantId: Long,
    val status: PaymentStatus?,

    @NotEmpty(message = "sdsad")
    @NotBlank(message = "211111")
    val callbackUrl: String
)