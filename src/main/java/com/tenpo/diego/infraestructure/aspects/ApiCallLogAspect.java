package com.tenpo.diego.infraestructure.aspects;

import com.tenpo.diego.domain.models.ApiCallLog;
import com.tenpo.diego.domain.ports.ApiCallLogPersistencePort;
import com.tenpo.diego.infraestructure.aspects.annotations.LogEndpointCall;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiCallLogAspect {

    private final ApiCallLogPersistencePort apiCallLogPersistencePort;
    Logger log = LoggerFactory.getLogger(ApiCallLogAspect.class);

    @Around("@annotation(logEndpointCall)")
    public Object logResult(ProceedingJoinPoint joinPoint, LogEndpointCall logEndpointCall) throws Throwable {
        Object result = joinPoint.proceed();
        String endpoint = logEndpointCall.endpoint();
        Object[] params = Arrays.stream(joinPoint.getArgs())
                .map(param -> param.toString())
                .toArray();
        if (result instanceof Mono) {
            return ((Mono<?>) result)
                    .doOnTerminate(() -> System.out.println("Método ejecutado: " + joinPoint.getSignature().getName()))
                    .doOnSuccess(value ->
                            save(Arrays.toString(params), endpoint, value != null ? value.toString() : StringUtil.EMPTY_STRING,  StringUtil.EMPTY_STRING)
                                    .subscribe()
                    )
                    .doOnError(error -> save(Arrays.toString(params), endpoint,  StringUtil.EMPTY_STRING, error.getMessage())
                            .subscribe());
        }
        return result;
    }

    private Mono<Void> save(String params, String endpoint, String response, String error) {
        ApiCallLog apiCallLog = ApiCallLog.builder()
                .timestamp(LocalDateTime.now())
                .parameters(params)
                .endpoint(endpoint)
                .response(response)
                .error(error)
                .build();
        return apiCallLogPersistencePort.save(apiCallLog);
    }
}
