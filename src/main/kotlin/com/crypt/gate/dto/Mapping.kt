package com.crypt.gate.dto

import com.crypt.gate.model.Invoice
import com.crypt.gate.util.Eth.bigIntegerToBigDecimal

fun toPaymentDTO(invoice: Invoice): InvoiceDTO {
    return InvoiceDTO(
        invoice.id,
        invoice.status,
        invoice.currency,
        bigIntegerToBigDecimal(invoice.amount),
    )
}