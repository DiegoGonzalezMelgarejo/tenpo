package com.tenpo.diego.infraestructure.adapter.out.rest;

import com.tenpo.diego.domain.ports.PercentagePort;
import com.tenpo.diego.infraestructure.adapter.exception.PercentageException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.Random;

@Component

public class PercentageRest implements PercentagePort {
    private static final int MAX_RETRIES = 3;
    private final Random random;

    public PercentageRest(Random random) {
        this.random = random;
    }

    Logger log = LoggerFactory.getLogger(PercentageRest.class);



    @Override
    @Cacheable(value = "percentages", key = "'percentage'")
    public Mono<BigDecimal> getPercentage() {
        return Mono.defer(this::callExternalService)
                .retryWhen(
                        reactor.util.retry.Retry.backoff(MAX_RETRIES-1, java.time.Duration.ofSeconds(1))
                                .doBeforeRetry(retrySignal -> log.error("Retrying due to: " + retrySignal.failure().getMessage()))
                )
                .onErrorResume( e -> {
                    log.error("Recovering after retries: " + e.getMessage());
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve percentage after multiple attempts. Please try again later."));
                });
    }

    private Mono<BigDecimal> callExternalService() {
        log.info("Attempting to obtain percentage...");
        return Mono.just(random.nextBoolean())
                .filter(success -> success)
                .map(success -> {
                    log.info("Successful field service");
                    return BigDecimal.valueOf(0.1);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Simulating external service failure...");
                    return Mono.error(new PercentageException("External service failed randomly"));
                }));
    }

}
