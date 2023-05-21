package com.crypt.gate.config

import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cryptogate")
data class CryptogateConfig(
    val eth: EthConfig
) {
    /**
     * Проверим адреса, которые задали в конфиге
     */
    @PostConstruct
    fun checkAddress() {
        val regex = "^(0x)?[0-9a-f]{40}$".toRegex(setOf(RegexOption.IGNORE_CASE))
        eth.wallets.forEach {
            if (!regex.matches(it)) {
                throw IllegalArgumentException("Wrong config ETH address $it")
            }
        }
    }
}

data class EthConfig(
    val wallets: List<String>
)