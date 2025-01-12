package com.tenpo.diego.infraestructure.adapter.out.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Builder
@Table(name = "api_call_logs")
public class ApiCallLogEntity {
    @Id
    private  Long id;

    private  String endpoint;

    private  LocalDateTime timestamp;

    private  String parameters;

    private  String response;

    private  String error;
}
