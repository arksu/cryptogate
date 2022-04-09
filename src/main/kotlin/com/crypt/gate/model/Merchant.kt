package com.crypt.gate.model

import javax.persistence.*

/**
 * Покупатель
 */
@Entity
class Merchant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String

)