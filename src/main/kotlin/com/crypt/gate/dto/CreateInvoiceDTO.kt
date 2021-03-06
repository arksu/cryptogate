package com.crypt.gate.dto

import com.crypt.gate.model.PaymentCurrency
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateInvoiceDTO(
    val currency: PaymentCurrency,

    @field:Positive
    val amount: BigDecimal,

    @field:NotNull(message = "Must not be null")
    @field:NotBlank(message = "Can't be blank")
    val orderNumber : String?,

    @field:NotNull(message = "Must not be null")
    @field:NotBlank(message = "Can't be blank")
    val callbackUrl: String?,

    @field:NotNull(message = "Must not be null")
    @field:NotBlank(message = "Can't be blank")
    val secretKey : String?
)