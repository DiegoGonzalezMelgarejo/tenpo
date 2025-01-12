package com.tenpo.diego.infraestructure.adapter.out.persistence.repository;

import com.tenpo.diego.infraestructure.adapter.out.persistence.entity.ApiCallLogEntity;
import reactor.core.publisher.Flux;

public interface ApiCallLogCustomRepository {
    Flux<ApiCallLogEntity> findAllBy(int size, int offSet);
}
