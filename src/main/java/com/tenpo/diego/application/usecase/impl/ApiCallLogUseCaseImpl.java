package com.tenpo.diego.application.usecase.impl;

import com.tenpo.diego.application.usecase.ApiCallLogUseCase;
import com.tenpo.diego.domain.models.ApiCallLog;
import com.tenpo.diego.domain.ports.ApiCallLogPersistencePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
@Component
@RequiredArgsConstructor
public class ApiCallLogUseCaseImpl implements ApiCallLogUseCase {
    Logger log= LoggerFactory.getLogger(ApiCallLogUseCaseImpl.class);
    private final ApiCallLogPersistencePort apiCallLogPersistencePort;
    @Override
    public Flux<ApiCallLog> findAll(int size, int offSet) {
        return apiCallLogPersistencePort.findAllBy(size,offSet)
                .doFinally(ignored -> log.info("ApiCallLogUseCaseImpl executed"));
    }
}
