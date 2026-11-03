package com.employee.system.model;

import com.employee.system.enums.TransactionStatus;

import java.math.BigDecimal;

public record AccountTransferResponse(
        long transactionId,
        String reference,
        long sourceAccountId,
        long destinationAccountId,
        BigDecimal amount,
        TransactionStatus status
) {
}
