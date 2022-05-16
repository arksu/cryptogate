package com.crypt.gate

import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class PaymentControllerTest(
    @Autowired
    val mockMvc: MockMvc

) {
    @Test
    fun test404() {

        mockMvc.perform(get("/api/payment/9999").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.timestamp", `is`(notNullValue())))
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors", hasSize<String>(1)))
            .andExpect(jsonPath("$.errors", hasItem("Not found")))
            .andDo(print())
            .andReturn()
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
            .andReturn()
    }
}