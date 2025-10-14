package es.unizar.webeng.lab2

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import org.hamcrest.Matchers.matchesPattern

// Integration tests for TimeController

@SpringBootTest
@AutoConfigureMockMvc
class TimeControllerTest {


    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var timeService: TimeProvider

    // Tests for /time endpoint

    @Test
    fun `should return current time in JSON format`() {
        val fixedTime = LocalDateTime.of(2025, 10, 9, 14, 30, 00)
        `when`(timeService.now()).thenReturn(fixedTime)

        mockMvc.perform(get("/time"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.time").value("2025-10-09T14:30:00"))
    }

    @Test
    fun `should return TimeDTO with correct structure`() {
        val fixedTime = LocalDateTime.of(2025, 10, 12, 10, 20, 00)
        `when`(timeService.now()).thenReturn(fixedTime)

        mockMvc.perform(get("/time"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.time").exists())
            .andExpect(jsonPath("$.time").isString)
    }

    @Test
    fun `should call TimeProvider service`() {
        val currentTime = LocalDateTime.now()
        `when`(timeService.now()).thenReturn(currentTime)

        mockMvc.perform(get("/time"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should return valid ISO datetime format`() {
        val fixedTime = LocalDateTime.of(2025, 12, 25, 23, 59, 59)
        `when`(timeService.now()).thenReturn(fixedTime)

        mockMvc.perform(get("/time"))
            .andExpect(status().isOk)
            .andExpect(
                jsonPath("$.time")
                    .value(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"))
            )
    }

    @Test
    fun `should deserialize response to TimeDTO`() {
    
        val fixedTime = LocalDateTime.of(2025, 6, 1, 12, 0, 0)
        `when`(timeService.now()).thenReturn(fixedTime)

        val result = mockMvc.perform(get("/time"))
            .andExpect(status().isOk)
            .andReturn()

        val responseBody = result.response.contentAsString
        val timeDTO = objectMapper.readValue(responseBody, TimeDTO::class.java)
        
        assert(timeDTO.time == fixedTime) { 
            "Expected time $fixedTime but got ${timeDTO.time}" 
        }
    }
}


class TimeDTOExtensionTest {

    @Test
    fun `LocalDateTime toDTO extension should create TimeDTO`() {
        val dateTime = LocalDateTime.of(2025, 3, 20, 8, 15, 30)

        val dto = dateTime.toDTO()

        assert(dto.time == dateTime) { "DTO time should match original LocalDateTime" }
        assert(dto is TimeDTO) { "Result should be a TimeDTO instance" }
    }
}