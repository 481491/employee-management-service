package com.employee.system.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountRequest(
        @NotBlank String bankName,
        @NotBlank String accountNumber,
        @NotNull @DecimalMin(value = "0.0") BigDecimal balance,
        UUID employeeId
) {
}
