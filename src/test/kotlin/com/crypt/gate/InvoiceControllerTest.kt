package com.crypt.gate

import com.crypt.gate.model.Merchant
import com.crypt.gate.repo.MerchantRepo
import com.jayway.jsonpath.JsonPath
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InvoiceControllerTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val merchantRepo: MerchantRepo
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

    @BeforeAll
    fun before() {
        // создаем тестового мерчанта
        val merchant = Merchant(0, "test", "superSecretKey")
        merchantRepo.save(merchant)
    }

    @Test
    fun testDatabaseIsRunning() {
        Assertions.assertTrue(dbContainer.isRunning)
    }

    @Test
    fun testCreatePayment() {
        // проверяем создание платежа
        val body = "{" +
                "  \"currency\": \"ETH\"," +
                "  \"amount\": \"0.01\"," +
                "  \"callbackUrl\": \"http://test.callback\"," +
                "  \"secretKey\": \"superSecretKey\"" +
                "}"
        val result = mockMvc.perform(
            post("/api/invoice")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andDo(print())
            .andExpect(jsonPath("$.currency", `is`("ETH")))
            .andExpect(jsonPath("$.status", `is`("WAITING")))
            .andReturn()

        val hash: String = JsonPath.read(result.response.contentAsString, "$.id")

        // получим созданный платеж
        mockMvc.perform(get("/api/invoice/$hash"))
            .andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$.id", `is`(hash)))
            .andExpect(jsonPath("$.currency", `is`("ETH")))
            .andExpect(jsonPath("$.status", `is`("WAITING")))
    }

    @Test
    fun testValidateError() {
        val body = "{" +
                "  \"currency\": \"ETH\"," +
                "  \"amount\": \"-0.01\"," +
                "  \"merchantId\": 1" +
                "}"
        mockMvc.perform(
            post("/api/invoice")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(5)))
            .andExpect(jsonPath("$.errors", hasItem(containsString("callbackUrl : Must not be null"))))
            .andExpect(jsonPath("$.errors", hasItem(containsString("callbackUrl : Can't be blank"))))
            .andExpect(jsonPath("$.errors", hasItem(containsString("secretKey : Must not be null"))))
            .andExpect(jsonPath("$.errors", hasItem(containsString("secretKey : Can't be blank"))))
            .andExpect(jsonPath("$.errors", hasItem(containsString("amount : must be greater than 0"))))
            .andDo(print())
    }

    @Test
    fun testValidateErrorBlank() {
        val body = "{" +
                "  \"currency\": \"ETH\"," +
                "  \"amount\": \"0.01\"," +
                "  \"merchantId\": 1," +
                "  \"callbackUrl\": \"\"," +
                "  \"secretKey\": \"\"" +
                "}"
        mockMvc.perform(
            post("/api/invoice")
                .content(body)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(2)))
            .andExpect(jsonPath("$.errors", hasItem(containsString("callbackUrl : Can't be blank"))))
            .andExpect(jsonPath("$.errors", hasItem(containsString("secretKey : Can't be blank"))))
            .andDo(print())
    }

    @Test
    fun test404() {
        mockMvc.perform(get("/api/invoice/9999").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.message", `is`("Invoice not found")))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(1)))
            .andExpect(jsonPath("$.errors", hasItem("Invoice not found")))
            .andDo(print())
    }

    @Test
    fun testInvoiceNotFound() {
        mockMvc.perform(get("/api/invoice/wrong").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.message", `is`("Invoice not found")))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(1)))
            .andExpect(jsonPath("$.errors", hasItem("Invoice not found")))
            .andDo(print())
    }
}