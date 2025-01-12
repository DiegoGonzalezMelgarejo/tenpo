package com.tenpo.diego.infraestructure.adapter.out.persistence.repository;

import com.tenpo.diego.infraestructure.adapter.out.persistence.entity.ApiCallLogEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ApiCallLogReactiveRepository extends ReactiveCrudRepository<ApiCallLogEntity, Long> {


}
