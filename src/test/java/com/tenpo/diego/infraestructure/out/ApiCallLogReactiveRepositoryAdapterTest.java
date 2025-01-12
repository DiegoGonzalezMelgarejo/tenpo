package com.tenpo.diego.infraestructure.out;

import com.tenpo.diego.domain.models.ApiCallLog;
import com.tenpo.diego.infraestructure.adapter.out.persistence.ApiCallLogAdapterReactiveRepository;
import com.tenpo.diego.infraestructure.adapter.out.persistence.entity.ApiCallLogEntity;
import com.tenpo.diego.infraestructure.adapter.out.persistence.repository.ApiCallLogCustomRepository;
import com.tenpo.diego.infraestructure.adapter.out.persistence.repository.ApiCallLogReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiCallLogReactiveRepositoryAdapterTest {

    @Mock
    private ApiCallLogReactiveRepository apiCallLogReactiveRepositoryMock;
    @Mock
    private ApiCallLogCustomRepository apiCallLogCustomRepositoryMock;
    @InjectMocks
    private ApiCallLogAdapterReactiveRepository apiCallLogReactiveRepositoryAdapter;

    @BeforeEach
    public void setUp() {
        reset(apiCallLogCustomRepositoryMock,apiCallLogReactiveRepositoryMock);
    }

    @Test
    void testSave() {
        // Dado
        ApiCallLog apiCallLog = ApiCallLog.builder()
                .error(null)
                .endpoint("endpoint")
                .parameters("param")
                .response("response")
                .timestamp(LocalDateTime.now())
                .build();
        ApiCallLogEntity apiCallLogEntity =ApiCallLogEntity.builder()
                .error(null)
                .endpoint("endpoint")
                .parameters("param")
                .response("response")
                .timestamp(LocalDateTime.now())
                .build();

        when(apiCallLogReactiveRepositoryMock.save(any(ApiCallLogEntity.class)))
                .thenReturn(Mono.just(apiCallLogEntity));


        StepVerifier.create(apiCallLogReactiveRepositoryAdapter.save(apiCallLog))
                .verifyComplete();


        verify(apiCallLogReactiveRepositoryMock, times(1)).save(any(ApiCallLogEntity.class));
    }

    @Test
    void testFindAllBy() {
        // Dado
        ApiCallLogEntity apiCallLogEntity = ApiCallLogEntity.builder()
                .error(null)
                .endpoint("endpoint")
                .parameters("param")
                .response("response")
                .timestamp(null)
                .build();
        Flux<ApiCallLogEntity> entityFlux = Flux.just(apiCallLogEntity);

        when(apiCallLogCustomRepositoryMock.findAllBy(10, 0))
                .thenReturn(entityFlux);

        StepVerifier.create(apiCallLogReactiveRepositoryAdapter.findAllBy(10, 0))
                .expectNextMatches(apiCallLog -> apiCallLog.getEndpoint().equals("endpoint") && apiCallLog.getResponse().equals("response"))
                .verifyComplete();


        verify(apiCallLogCustomRepositoryMock, times(1)).findAllBy(10, 0);
    }
}
