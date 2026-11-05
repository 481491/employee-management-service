package com.employee.system.impl;

import com.employee.system.entity.PolandTax;
import com.employee.system.model.PolandTaxDto;
import com.employee.system.model.PolandTaxRequest;
import com.employee.system.repositories.PolandTaxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;

import static com.employee.system.mappers.PolandTaxMapper.mapToDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaxCacheLoader {

    private final PolandTaxRepository repository;
    private final RedisTemplate<Object, Object> redisTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void loadCache() {

        repository.findAll().stream()
                .sorted(Comparator.comparing(PolandTax::getId).reversed())
                .limit(1)
                .findFirst()
                .ifPresent(tax ->
                        redisTemplate.opsForValue().set(
                                "poland-tax-rates",
                                mapToDto(tax)
                        ));
        log.info("# -> the cache has been loaded for Poland tax slab.......");
    }
}