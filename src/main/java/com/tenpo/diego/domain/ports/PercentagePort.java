package com.tenpo.diego.domain.ports;

import com.tenpo.diego.infraestructure.adapter.out.persistence.entity.ApiCallLogEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PercentagePort {

    Mono<BigDecimal> getPercentage();
}
