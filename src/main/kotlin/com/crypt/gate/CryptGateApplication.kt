package com.crypt.gate

import com.crypt.gate.config.CryptogateConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = [CryptogateConfig::class])
class CryptGateApplication

fun main(args: Array<String>) {
    Locale.setDefault(Locale.ROOT)
    runApplication<CryptGateApplication>(*args)
}
