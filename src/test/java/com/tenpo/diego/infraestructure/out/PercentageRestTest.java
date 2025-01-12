package com.tenpo.diego.infraestructure.out;

import com.tenpo.diego.infraestructure.adapter.out.rest.PercentageRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import java.util.Random;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PercentageRestTest {

    @Mock
    private Random random;

    @InjectMocks
    private PercentageRest percentageRest;

    @BeforeEach
    public void setUp() {
        reset(random);
    }

    @Test
    public void testGetPercentageSuccess() {
        when(random.nextBoolean()).thenReturn(true);

        StepVerifier.create(percentageRest.getPercentage())
                .expectNext(BigDecimal.valueOf(0.1))
                .verifyComplete();

        verify(random, times(1)).nextBoolean();
    }

    @Test
    public void testGetPercentageFailureAndRetry() {
        when(random.nextBoolean()).thenReturn(false);
        StepVerifier.create(percentageRest.getPercentage())
                .expectError(ResponseStatusException.class)
                .verify();
        verify(random, times(3)).nextBoolean();
    }

    @Test
    public void testGetPercentageErrorAfterRetries() {
        when(random.nextBoolean()).thenReturn(false);

        StepVerifier.create(percentageRest.getPercentage())
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
                        && throwable.getMessage().contains("Unable to retrieve percentage after multiple attempts. Please try again later."))
                .verify();
        verify(random, times(3)).nextBoolean();

    }
    @Test
    void testGetPercentageRetriesThenSucceeds() {
        when(random.nextBoolean()).thenReturn(false)
                .thenReturn(true) ;
        StepVerifier.create(percentageRest.getPercentage())
                .expectNext(BigDecimal.valueOf(0.1))
                .verifyComplete();

        verify(random, times(2)).nextBoolean();
    }

}
