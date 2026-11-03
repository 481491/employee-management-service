package com.employee.system.model.outbox;

import java.math.BigDecimal;

public record TransactionCreatedEvent(
        Long transactionId,
        Long sourceAccountId,
        Long destinationAccountId,
        BigDecimal amount
) {}
