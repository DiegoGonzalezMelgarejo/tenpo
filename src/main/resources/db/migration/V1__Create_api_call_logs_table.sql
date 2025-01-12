CREATE TABLE api_call_logs (
                               id SERIAL PRIMARY KEY,
                               endpoint VARCHAR(255),
                               timestamp TIMESTAMP,
                               parameters TEXT,
                               response TEXT,
                               error TEXT
);