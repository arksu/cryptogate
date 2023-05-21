package com.crypt.gate.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigInteger

@Entity
class EthTransaction(
    @Id
    val hash: String,

    @Column(nullable = false)
    val method: String,

    @Column(nullable = false)
    val block: Long,

    @Column(nullable = false)
    val sender: String,

    @Column(nullable = false)
    val receiver: String,

    @Column(nullable = false)
    val value: BigInteger,

    @Column(nullable = false)
    val gas: BigInteger
)