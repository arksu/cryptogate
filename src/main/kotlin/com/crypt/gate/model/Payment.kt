package com.crypt.gate.model

import java.math.BigInteger
import javax.persistence.*

/**
 * платеж для мерчанта
 */
@Entity
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val currency: PaymentCurrency,

    @Column(nullable = false, columnDefinition = "DECIMAL(22,2)")
    val amount: BigInteger,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var merchant: Merchant
)
