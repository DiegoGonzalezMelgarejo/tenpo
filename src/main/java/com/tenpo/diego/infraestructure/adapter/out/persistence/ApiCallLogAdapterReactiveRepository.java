package com.tenpo.diego.infraestructure.adapter.out.persistence;

import com.tenpo.diego.domain.models.ApiCallLog;
import com.tenpo.diego.domain.ports.ApiCallLogPersistencePort;
import com.tenpo.diego.infraestructure.adapter.out.persistence.entity.ApiCallLogEntity;
import com.tenpo.diego.infraestructure.adapter.out.persistence.repository.ApiCallLogCustomRepository;
import com.tenpo.diego.infraestructure.adapter.out.persistence.repository.ApiCallLogReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiCallLogAdapterReactiveRepository implements ApiCallLogPersistencePort {

    private final ApiCallLogReactiveRepository apiCallLogReactiveRepository;
    private final ApiCallLogCustomRepository apiCallLogCustomRepository;

    public ApiCallLogAdapterReactiveRepository(ApiCallLogReactiveRepository apiCallLogReactiveRepository, ApiCallLogCustomRepository apiCallLogCustomRepository) {
        this.apiCallLogReactiveRepository = apiCallLogReactiveRepository;
        this.apiCallLogCustomRepository = apiCallLogCustomRepository;
    }

    Logger log= LoggerFactory.getLogger(ApiCallLogAdapterReactiveRepository.class);

    @Override
    public Mono<Void> save(ApiCallLog apiCallLog) {
        return apiCallLogReactiveRepository.save(getApiCallLogEntity(apiCallLog))
                .doOnSuccess(ignored -> log.info("Log saved in database"))
                .doOnError(e -> log.error("Error saving log in database {}",e.getMessage()))
                .then();
    }

    @Override
    public Flux<ApiCallLog> findAllBy(int size, int offSet) {
        return apiCallLogCustomRepository.findAllBy(size,offSet)
                .map(this::getApiCallLog);
    }



    private ApiCallLogEntity getApiCallLogEntity(ApiCallLog apiCallLog) {
        return ApiCallLogEntity.builder()
                .error(apiCallLog.getError())
                .endpoint(apiCallLog.getEndpoint())
                .parameters(apiCallLog.getParameters())
                .response(apiCallLog.getResponse())
                .timestamp(apiCallLog.getTimestamp())
                .build();
    }
    private ApiCallLog getApiCallLog(ApiCallLogEntity apiCallLogEntity) {
        return ApiCallLog.builder()
                .id(apiCallLogEntity.getId())
                .error(apiCallLogEntity.getError())
                .endpoint(apiCallLogEntity.getEndpoint())
                .parameters(apiCallLogEntity.getParameters())
                .response(apiCallLogEntity.getResponse())
                .timestamp(apiCallLogEntity.getTimestamp())
                .build();
    }
}
