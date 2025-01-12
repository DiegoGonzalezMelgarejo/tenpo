package com.tenpo.diego.infraestructure.adapter.in.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageError {
    private final String message;
}
