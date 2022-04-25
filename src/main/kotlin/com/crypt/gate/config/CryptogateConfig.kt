package com.crypt.gate.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cryptogate")
data class CryptogateConfig(
    val eth: EthConfig
)

data class EthConfig(
    val wallets: List<String>
)