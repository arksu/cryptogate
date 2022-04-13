package com.crypt.gate.repo

import com.crypt.gate.model.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepo : JpaRepository<Payment, Long> {
}