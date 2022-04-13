package com.crypt.gate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@SpringBootApplication
@EnableScheduling
class CryptGateApplication

fun main(args: Array<String>) {
    Locale.setDefault(Locale.ROOT)
    runApplication<CryptGateApplication>(*args)
}
