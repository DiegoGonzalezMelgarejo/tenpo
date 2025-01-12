package com.tenpo.diego.domain.ports;

import com.tenpo.diego.domain.models.ApiCallLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiCallLogPersistencePort {

    Mono<Void> save(ApiCallLog apiCallLog);
    Flux<ApiCallLog> findAllBy(int size, int offSet);

}
