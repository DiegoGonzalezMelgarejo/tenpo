package com.tenpo.diego.infraestructure.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.diego.application.usecase.CalculateUseCase;
import com.tenpo.diego.infraestructure.adapter.in.rest.controller.CalculateController;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.CalculateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc()
@SpringBootTest
public class CalculateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculate() throws Exception {
        CalculateDto dto = new CalculateDto();
        dto.setNum2(BigDecimal.valueOf(10));
        dto.setNum1(BigDecimal.valueOf(20));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/calculate").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();
        int message = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), message);

    }

    @Test
    void testCalculateRateLimitExceeded() throws Exception {
        int allowedRequests = 3;
        int additionalRequests = 1;

        CalculateDto dto = createCalculateDto(BigDecimal.valueOf(20), BigDecimal.valueOf(10));

        for (int i = 0; i < allowedRequests; i++) {
            sendPostRequest(dto, HttpStatus.OK.value());
        }

        for (int i = 0; i < additionalRequests; i++) {
            sendPostRequest(dto, HttpStatus.TOO_MANY_REQUESTS.value());
        }
    }

    private CalculateDto createCalculateDto(BigDecimal num1, BigDecimal num2) {
        CalculateDto dto = new CalculateDto();
        dto.setNum1(num1);
        dto.setNum2(num2);
        return dto;
    }

    private void sendPostRequest(CalculateDto dto, int expectedStatus) throws Exception {
        int actualStatus = mockMvc.perform(MockMvcRequestBuilders.post("/calculate").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();

        assertEquals(expectedStatus, actualStatus, "El código de estado no coincide con el esperado");
    }
}