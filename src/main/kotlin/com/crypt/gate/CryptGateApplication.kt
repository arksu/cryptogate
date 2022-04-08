package com.crypt.gate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CryptGateApplication

fun main(args: Array<String>) {
    runApplication<CryptGateApplication>(*args)
}
