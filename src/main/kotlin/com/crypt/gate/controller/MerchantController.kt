package com.crypt.gate.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Торговцы
 */
@RestController
@RequestMapping("/api/merchant")
class MerchantController {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMerchant() {
        TODO("getMerchant")
    }
}