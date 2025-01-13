package com.tenpo.diego.infraestructure.adapter.in.rest.controller;

import com.tenpo.diego.infraestructure.adapter.exception.PercentageException;
import com.tenpo.diego.infraestructure.adapter.exception.RateLimitException;
import com.tenpo.diego.infraestructure.adapter.in.rest.dto.MessageError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageError.builder().message(errors.toString()).build());
    }

}

