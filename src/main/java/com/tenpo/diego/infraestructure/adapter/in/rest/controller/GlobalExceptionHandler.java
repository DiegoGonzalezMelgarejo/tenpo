package com.tenpo.diego.infraestructure.adapter.in.rest.controller;

import com.tenpo.diego.infraestructure.adapter.exception.PercentageException;
import com.tenpo.diego.infraestructure.adapter.exception.RateLimitException;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.MessageError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<MessageError> handleRateLimitException(RateLimitException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(MessageError.builder().message(ex.getReason()).build());
    }
    @ExceptionHandler(PercentageException.class)
    public ResponseEntity<MessageError> handlePercentageException(PercentageException ex) {
        return ResponseEntity.status(500).body(MessageError.builder().message(ex.getMessage()).build());
    }

}

