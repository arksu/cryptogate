package com.crypt.gate.dto

import com.crypt.gate.model.PaymentCurrency
import com.crypt.gate.model.PaymentStatus
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentDTO(
    var id: Long,
    var currency: PaymentCurrency,
    var amount: BigDecimal,
    var merchantId: Long,
    var status: PaymentStatus?,

    @field:NotNull(message = "sdsad")
//    @field:NotBlank(message = "211111")
    var callbackUrl: String?
)