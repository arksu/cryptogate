package com.crypt.gate.dto

import com.crypt.gate.model.Invoice
import com.crypt.gate.util.bigIntegerToBigDecimal

fun toInvoiceDTO(invoice: Invoice): InvoiceDTO {
    return InvoiceDTO(
        invoice.hash,
        invoice.status,
        invoice.currency,
        bigIntegerToBigDecimal(invoice.currency, invoice.amount),
    )
}