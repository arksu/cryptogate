package com.crypt.gate.dto

import com.crypt.gate.const.NOT_BLANK_MESSAGE
import com.crypt.gate.const.NOT_NULL_MESSAGE
import com.crypt.gate.model.CryptoCurrency
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateInvoiceRequestDTO(
    val currency: CryptoCurrency,

    @field:Positive
    val amount: BigDecimal,

    @field:NotNull(message = NOT_NULL_MESSAGE)
    @field:NotBlank(message = NOT_BLANK_MESSAGE)
    val orderNumber : String?,

    @field:NotNull(message = NOT_NULL_MESSAGE)
    @field:NotBlank(message = NOT_BLANK_MESSAGE)
    val callbackUrl: String?,

    @field:NotNull(message = NOT_NULL_MESSAGE)
    @field:NotBlank(message = NOT_BLANK_MESSAGE)
    val secretKey : String?
)