package com.crypt.gate

import com.crypt.gate.controller.PaymentController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
class CryptGateApplicationTests(
    @Autowired
    val controller: PaymentController,
) {
    companion object {
        @Container
        private val dbContainer = MariaDBContainer("mariadb:10.7")
            .withReuse(true)

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            dbContainer.start()
            registry.add("spring.datasource.url", dbContainer::getJdbcUrl)
            registry.add("spring.datasource.username", dbContainer::getUsername)
            registry.add("spring.datasource.password", dbContainer::getPassword)
        }
    }

    /**
     * smoke test
     */
    @Test
    fun contextLoads() {
        assertThat(controller).isNotNull
    }

}
