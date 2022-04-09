package com.crypt.gate.model

class EthTransaction(
    val hash: String,

    val block: Long,

    val from: String,

    val to: String,

    val value: Long
)