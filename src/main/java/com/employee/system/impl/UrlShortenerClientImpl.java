package com.employee.system.impl;

import com.employee.system.model.ShortenRequestVo;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerClientImpl {
    private final WebClient webClient;

    @CircuitBreaker(name = "faulttolencetest",
            fallbackMethod = "fallback")
    @Bulkhead(name = "urlShortener", fallbackMethod = "bulkheadFallback")
    public @Nullable String getUrlShortenerCall(ShortenRequestVo dto) {
        return webClient.post().uri("shortenV1")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                // Timeout
                .timeout(Duration.ofSeconds(5))
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(5))
                                .maxBackoff(Duration.ofSeconds(10))
                                .jitter(0.5)
                                .doBeforeRetry(rs ->
                                        log.warn("Timeout happened, retry started..."))
                                .doAfterRetry(
                                        rs ->
                                                log.warn(
                                                        "Retry attempt {} due to {}",
                                                        rs.totalRetries() + 1,
                                                        rs.failure().getClass().getSimpleName()
                                                )
                                ))
                /* .onErrorResume(ex ->
                         {
                             System.out.println("timeout occurs");
                             return Mono.just("please try after some times");
                         }
                 )*/
                .block();
    }

    public String fallback(ShortenRequestVo dto, Exception ex) {
        return "Please try again later-"+ex.getMessage();
    }

    public String bulkheadFallback(ShortenRequestVo dto, Exception ex) {
        return "Bulk Head pattern activated...-"+ex.getMessage();
    }
}
