package com.employee.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class EmployeeConfig {

    @Bean
    public Executor employeeExecutor() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8089/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
