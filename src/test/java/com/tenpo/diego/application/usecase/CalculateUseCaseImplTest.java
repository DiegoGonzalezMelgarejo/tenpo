package com.tenpo.diego.application.usecase;

import com.tenpo.diego.application.usecase.impl.CalculateUseCaseImpl;
import com.tenpo.diego.domain.ports.PercentagePort;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.CalculateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;


class CalculateUseCaseImplTest {

    private CalculateUseCaseImpl calculateUseCaseImpl;
    private PercentagePort percentagePortMock;

    @BeforeEach
    void setUp() {
        percentagePortMock = Mockito.mock(PercentagePort.class);

        calculateUseCaseImpl = new CalculateUseCaseImpl(percentagePortMock);
    }

    @Test
    void testCalculateSuccess() {
        BigDecimal num1 = BigDecimal.valueOf(100);
        BigDecimal num2 = BigDecimal.valueOf(50);
        CalculateDto calculateDto = new CalculateDto();
        calculateDto.setNum1(num1);
        calculateDto.setNum2(num2);

        BigDecimal percentage = BigDecimal.valueOf(0.2);
        Mockito.when(percentagePortMock.getPercentage()).thenReturn(Mono.just(percentage));


        BigDecimal expectedResult = num1.add(num2).add(num1.add(num2).multiply(percentage));

        StepVerifier.create(calculateUseCaseImpl.calculate(calculateDto))
                .expectNext(expectedResult)
                .verifyComplete();

        Mockito.verify(percentagePortMock, Mockito.times(1)).getPercentage();  // Verificamos que el método getPercentage() fue llamado
    }

    @Test
    void testCalculateError() {
        // Dado
        BigDecimal num1 = BigDecimal.valueOf(100);
        BigDecimal num2 = BigDecimal.valueOf(50);
        CalculateDto calculateDto = new CalculateDto();
        calculateDto.setNum1(num1);
        calculateDto.setNum2(num2);
        Mockito.when(percentagePortMock.getPercentage()).thenReturn(Mono.error(new RuntimeException("Error obtaining percentage")));

        StepVerifier.create(calculateUseCaseImpl.calculate(calculateDto))
                .expectError(RuntimeException.class)
                .verify();

        Mockito.verify(percentagePortMock, Mockito.times(1)).getPercentage();  // Verificamos que el método getPercentage() fue llamado
    }
}
