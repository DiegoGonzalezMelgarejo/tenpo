package com.tenpo.diego.application.usecase;
import com.tenpo.diego.application.usecase.impl.ApiCallLogUseCaseImpl;
import com.tenpo.diego.domain.models.ApiCallLog;
import com.tenpo.diego.domain.ports.ApiCallLogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class ApiCallLogUseCaseImplTest {

    private ApiCallLogUseCaseImpl apiCallLogUseCaseImpl;
    private ApiCallLogPersistencePort apiCallLogPersistencePortMock;

    @BeforeEach
    void setUp() {
        apiCallLogPersistencePortMock = mock(ApiCallLogPersistencePort.class);

        apiCallLogUseCaseImpl = new ApiCallLogUseCaseImpl(apiCallLogPersistencePortMock);
    }

    @Test
    void testFindAllSuccess() {
        ApiCallLog apiCallLog1 = ApiCallLog.builder()
                .id(null)
                .endpoint("endpoint1")
                .parameters("param1")
                .response("response1")
                .error("no")
                .timestamp(null)
                .build();

        ApiCallLog apiCallLog2 = ApiCallLog.builder()
                .id(null)
                .endpoint("endpoint2")
                .parameters("param2")
                .response("response2")
                .error("yes")
                .timestamp(null)
                .build();

        when(apiCallLogPersistencePortMock.findAllBy(2, 0)).thenReturn(Flux.fromIterable(Arrays.asList(apiCallLog1, apiCallLog2)));

        StepVerifier.create(apiCallLogUseCaseImpl.findAll(2, 0))
                .expectNext(apiCallLog1, apiCallLog2)
                .verifyComplete();

        verify(apiCallLogPersistencePortMock, times(1)).findAllBy(2, 0);
    }

    @Test
    void testFindAllWithLogging() {
        ApiCallLog apiCallLog1 = ApiCallLog.builder()
                .id(null)
                .endpoint("endpoint1")
                .parameters("param1")
                .response("response1")
                .error("no")
                .timestamp(null)
                .build();

        when(apiCallLogPersistencePortMock.findAllBy(1, 0)).thenReturn(Flux.just(apiCallLog1));

        StepVerifier.create(apiCallLogUseCaseImpl.findAll(1, 0))
                .expectNext(apiCallLog1)
                .verifyComplete();


        verify(apiCallLogPersistencePortMock, times(1)).findAllBy(1, 0);
    }

    @Test
    void testFindAllEmpty() {
        when(apiCallLogPersistencePortMock.findAllBy(2, 0)).thenReturn(Flux.empty());

        StepVerifier.create(apiCallLogUseCaseImpl.findAll(2, 0))
                .expectComplete()
                .verify();

        verify(apiCallLogPersistencePortMock, times(1)).findAllBy(2, 0);
    }
}
