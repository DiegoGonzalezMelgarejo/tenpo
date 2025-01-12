package com.tenpo.diego.application.usecase;

import com.tenpo.diego.infraestructure.adapter.in.rest.dto.CalculateDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
public interface CalculateUseCase {
    Mono<BigDecimal> calculate(CalculateDto calculateDto);
}
