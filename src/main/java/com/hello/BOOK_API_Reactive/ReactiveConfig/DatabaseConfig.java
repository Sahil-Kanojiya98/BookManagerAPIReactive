package com.hello.BOOK_API_Reactive.ReactiveConfig;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
public class DatabaseConfig {

    @Bean
    public ApplicationRunner initializeDatabase(DatabaseClient databaseClient) {
        return args -> {
            Mono<Void> createTable = databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS books_tbl (
                    book_id INT PRIMARY KEY,
                    book_name VARCHAR(255) NOT NULL,
                    book_price INT NOT NULL
                );
                """).then();
            createTable.block();
        };
    }

}

