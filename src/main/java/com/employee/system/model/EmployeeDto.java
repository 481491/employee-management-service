package com.employee.system.model;


import com.employee.system.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private String id;

    @NotNull(message = "the name should not be empty")
    private String name;
    @Email(message = "email should be global format")
    private String email;

    private String department;
    private EmployeeStatus employeeStatus;
    private BigDecimal salary;
}
