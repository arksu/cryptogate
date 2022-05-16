package com.crypt.gate

import com.crypt.gate.controller.PaymentController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CryptGateApplicationTests(
    @Autowired
    val controller: PaymentController,
) {

    /**
     * smoke test
     */
    @Test
    fun contextLoads() {
        assertThat(controller).isNotNull
    }

}
