package com.employee.system.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdempotencyService {
    private final StringRedisTemplate redisTemplate;

    public boolean alreadyProcessed(Long transactionId) {

        String key = "txn:" + transactionId;

        Boolean inserted = redisTemplate.opsForValue().setIfAbsent(key, "PROCESSED", Duration.ofMinutes(5000));

       log.info(inserted? "Cache miss":"Cache hit");
        return Boolean.FALSE.equals(inserted);
    }
}
