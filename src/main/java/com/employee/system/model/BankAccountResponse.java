package com.employee.system.model;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountResponse(
        long accountId,
        String bankName,
        String accountNumber,
        BigDecimal balance,
        UUID employeeId
) {
}
