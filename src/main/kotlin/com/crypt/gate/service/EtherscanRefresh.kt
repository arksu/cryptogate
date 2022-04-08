package com.crypt.gate.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * Обновление данных с etherscan
 */
@Service
class EtherscanRefresh {

    @Scheduled(cron = "\${refresh.etherscan.cron}")
    fun run() {
        println("shce")
    }
}