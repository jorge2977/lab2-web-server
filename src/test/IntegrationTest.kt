package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should display custom error page for 404`() {
        mockMvc.perform(get("/non-existent-page"))
            .andExpect(status().isNotFound)
            .andExpect(view().name("error"))
            .andExpect(model().attributeExists("status"))
            .andExpect(model().attributeExists("error"))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Error")))
    }

    @Test
    fun `should show error status in custom error page`() {
        mockMvc.perform(get("/invalid-endpoint"))
            .andExpect(status().isNotFound)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("404")))
    }

}