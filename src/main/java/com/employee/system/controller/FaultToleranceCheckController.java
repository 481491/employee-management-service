package com.employee.system.controller;

import com.employee.system.impl.UrlShortenerClientImpl;
import com.employee.system.model.ShortenRequestVo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@Slf4j
public class FaultToleranceCheckController {

    private final UrlShortenerClientImpl webClient;

    @GetMapping("fault-check")
    public ResponseEntity<String> checkFaultTolerance() {
        ShortenRequestVo dto = new ShortenRequestVo();
        dto.setUrl("https://test.company.com/applications/" +
                "banking/customer-profile/details/account-summary/" +
                "transaction-history/export/download?customerId=987654321&branchCode=KRK001&" +
                "accountNumber=12345678901234567890&currency=PLN&" +
                "fromDate=2026-01-01T00:00:00Z&toDate=2026-12-31T23:59:59Z" +
                "&includePendingTransactions=true&includeClosedAccounts=false" +
                "&exportFormat=CSV&compressionEnabled=true&notificationEmail=test.user@example.com&requestTrackingId=84f7d9c2-6e89-4f23-a3b5-1b1234567890&sessionId=abcdef1234567890abcdef1234567890");

        List<CompletableFuture<String>> list = IntStream.rangeClosed(1, 100)
                .mapToObj(e -> CompletableFuture.supplyAsync(() -> webClient.getUrlShortenerCall(dto))
                        .thenApply(str -> {
                            assert str != null;
                            return str.toUpperCase();
                        })).toList();
        list.stream().map(CompletableFuture::join).toList()
                .forEach(System.out::println);
        //String shorten = webClient.getUrlShortenerCall(dto);
        return ResponseEntity.ok("shorten.........................");
    }


}