package com.crypt.gate.model

import javax.persistence.*

/**
 * платеж для мерчанта
 */
@Entity
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val currency: PaymentCurrency,

    @Column(nullable = false)
    val amount : Long,

    @ManyToOne(optional = false)
    val merchant: Merchant

)
