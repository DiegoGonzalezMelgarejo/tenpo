package com.tenpo.diego.infraestructure.adapter.in.rest.controller;

import com.tenpo.diego.application.usecase.ApiCallLogUseCase;
import com.tenpo.diego.application.usecase.CalculateUseCase;
import com.tenpo.diego.domain.models.ApiCallLog;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.CalculateDto;
import com.tenpo.diego.infraestructure.aspects.annotations.LogEndpointCall;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class ApiCallLogController {
    private final ApiCallLogUseCase apiCallLogUseCase;

    @GetMapping("/log/{size}/{offset}")
    public Flux<ApiCallLog> getPage(@PathVariable int offset, @PathVariable int size) {
        return apiCallLogUseCase.findAll(size,offset);
    }
}
