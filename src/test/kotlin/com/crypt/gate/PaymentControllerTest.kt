package com.crypt.gate

import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional  // ??????
@SpringBootTest(
//    properties = [
//    "spring.datasource.url=jdbc:tc:mariadb:10.7-alpine:///cryptogate"
//]
)
@Testcontainers
class PaymentControllerTest(
    @Autowired
    val mockMvc: MockMvc
) {
    companion object {
        @Container
        private val dbContainer = MariaDBContainer("mariadb:10.7").withReuse(true)

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", dbContainer::getJdbcUrl)
            registry.add("spring.datasource.username", dbContainer::getUsername)
            registry.add("spring.datasource.password", dbContainer::getPassword)
        }
    }

    @Test
    fun testDatabaseIsRunning() {
        assert(dbContainer.isRunning)
    }

    @Test
    fun testCreatePayment() {
        val body = "{\n" +
                "  \"currency\": \"ETH\",\n" +
                "  \"amount\": \"0.01\",\n" +
                "  \"callbackUrl\": \"http://ccc\",\n" +
                "  \"merchantId\": 1\n" +
                "}"
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/payment")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun testValidateError() {
        val body = "{\n" +
                "  \"currency\": \"ETH\",\n" +
                "  \"amount\": \"0.01\",\n" +
                "  \"merchantId\": 1\n" +
                "}"
        mockMvc.perform(
            post("/api/payment")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(2)))
            .andExpect(jsonPath("$.errors", hasItem(containsString("paymentDTO.callbackUrl : Must not be null"))))
            .andExpect(jsonPath("$.errors", hasItem(containsString("paymentDTO.callbackUrl : Can't be blank"))))
            .andDo(print())
    }

    @Test
    fun testValidateErrorBlank() {
        val body = "{\n" +
                "  \"currency\": \"ETH\",\n" +
                "  \"amount\": \"0.01\",\n" +
                "  \"merchantId\": 1,\n" +
                "  \"callbackUrl\": \"\"\n" +
                "}"
        mockMvc.perform(
            post("/api/payment")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(1)))
            .andExpect(jsonPath("$.errors", hasItem(containsString("paymentDTO.callbackUrl : Can't be blank"))))
            .andDo(print())
    }

    @Test
    fun test404() {
        mockMvc.perform(get("/api/payment/9999").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(1)))
            .andExpect(jsonPath("$.errors", hasItem("Not found")))
            .andDo(print())
    }

    @Test
    fun testWrongNumberFormat() {
        mockMvc.perform(get("/api/payment/wrong").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(1)))
            .andExpect(jsonPath("$.errors", hasItem("Wrong number format")))
            .andDo(print())
    }
}