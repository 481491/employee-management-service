package com.employee.system.impl.kafka;

import com.employee.system.repositories.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionOutBoxConsumer {
    private final AuditRepository auditRepository;

}
