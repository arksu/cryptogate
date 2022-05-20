package com.crypt.gate

import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@AutoConfigureMockMvc
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
//@WebMvcTest
@SpringBootTest
class PaymentControllerTest(
    @Autowired
    val mockMvc: MockMvc
) {
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