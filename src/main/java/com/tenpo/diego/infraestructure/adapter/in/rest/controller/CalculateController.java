package com.tenpo.diego.infraestructure.adapter.in.rest.controller;

import com.tenpo.diego.application.usecase.CalculateUseCase;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.CalculateDto;
import com.tenpo.diego.infraestructure.aspects.annotations.LogEndpointCall;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CalculateController {
    private final CalculateUseCase calculateUseCase;

    @PostMapping("/calculate")
    @LogEndpointCall(endpoint = "/calculate")
    public Mono<BigDecimal> calculate(@RequestBody CalculateDto calculateDto) {
        return calculateUseCase.calculate(calculateDto);
    }
}
