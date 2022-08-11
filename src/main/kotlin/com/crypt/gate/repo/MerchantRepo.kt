package com.crypt.gate.repo

import com.crypt.gate.model.Merchant
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantRepo : JpaRepository<Merchant, Long> {
    fun findBySecretKey(key: String): Merchant?
}