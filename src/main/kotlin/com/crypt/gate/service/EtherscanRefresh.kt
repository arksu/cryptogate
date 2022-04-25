package com.crypt.gate.service

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * Обновление данных с etherscan
 */
@Service
@ConditionalOnProperty(prefix = "cryptogate.refresh.etherscan", name = ["enabled"], matchIfMissing = false)
class EtherscanRefresh {

    @Scheduled(cron = "\${cryptogate.refresh.etherscan.cron}")
    fun run() {
    }
}