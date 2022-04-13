package com.crypt.gate.model

import java.math.BigInteger
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

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