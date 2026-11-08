package com.employee.system.impl;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHealthIndicator implements HealthIndicator {
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public @Nullable Health health() {
        try {

            redisTemplate.opsForValue()
                    .set("health", "UP");

            return Health.up()
                    .withDetail("redis", "Redis Connected and Healthy")
                    .build();

        } catch (Exception ex) {

            return Health.down()
                    .withException(ex)
                    .build();
        }
    }

}
