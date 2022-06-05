package com.crypt.gate.repo

import com.crypt.gate.model.Invoice
import org.springframework.data.jpa.repository.JpaRepository

interface InvoiceRepo : JpaRepository<Invoice, Long> {
    fun findByHash(hash: String): Invoice?
}