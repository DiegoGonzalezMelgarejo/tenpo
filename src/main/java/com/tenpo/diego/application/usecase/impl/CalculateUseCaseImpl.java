package com.tenpo.diego.application.usecase.impl;

import com.tenpo.diego.application.usecase.CalculateUseCase;
import com.tenpo.diego.domain.ports.PercentagePort;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.CalculateDto;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class CalculateUseCaseImpl implements CalculateUseCase {

    private final PercentagePort percentagePort;
    Logger log= LoggerFactory.getLogger(CalculateUseCaseImpl.class);
    @Override
    public Mono<BigDecimal> calculate(CalculateDto calculateDto) {
        return percentagePort.getPercentage()
                .map(percentage -> resolveCalculate(percentage,calculateDto.getNum1().add(calculateDto.getNum2())))
                .doOnSuccess(result -> log.info("The result was {}", result))
                .doOnError(error -> log.info("Error {}", error));
    }

    private BigDecimal resolveCalculate(BigDecimal percentage, BigDecimal result){
        return result.add(result.multiply(percentage));
    }
}
