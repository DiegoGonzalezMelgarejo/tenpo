package com.tenpo.diego.application.usecase;

import com.tenpo.diego.domain.models.ApiCallLog;
import reactor.core.publisher.Flux;

public interface ApiCallLogUseCase {
    Flux<ApiCallLog> findAll(int size, int offSet);
}
