package com.tenpo.diego.domain.models;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
public class ApiCallLog {
    private Long id;

    private String endpoint;

    private LocalDateTime timestamp;

    private String parameters;

    private String response;

    private String error;
}
