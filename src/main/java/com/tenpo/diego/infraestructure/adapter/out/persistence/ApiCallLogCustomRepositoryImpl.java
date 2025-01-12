package com.tenpo.diego.infraestructure.adapter.out.persistence;

import com.tenpo.diego.infraestructure.adapter.out.persistence.entity.ApiCallLogEntity;
import com.tenpo.diego.infraestructure.adapter.out.persistence.repository.ApiCallLogCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApiCallLogCustomRepositoryImpl implements ApiCallLogCustomRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Flux<ApiCallLogEntity> findAllBy(int size, int offSet) {
        String query = "SELECT * FROM api_call_logs LIMIT :limit OFFSET :offset";

        return databaseClient.sql(query)
                .bind("limit", size)
                .bind("offset", offSet)
                .map((row, metadata) -> ApiCallLogEntity.builder()
                        .id(row.get("id", Long.class))
                        .endpoint(row.get("endpoint", String.class))
                        .timestamp(row.get("timestamp", LocalDateTime.class))
                        .parameters(row.get("parameters", String.class))
                        .response(row.get("response", String.class))
                        .error(row.get("error", String.class))
                        .build()
                )
                .all();
    }
}
