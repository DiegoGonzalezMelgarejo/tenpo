package com.tenpo.diego.infraestructure.adapter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RateLimitException extends ResponseStatusException {

    public RateLimitException(String reason) {
        super(HttpStatus.TOO_MANY_REQUESTS, reason);
    }

}
