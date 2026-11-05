package com.employee.system.impl;

import com.employee.system.model.EmployeeDto;
import com.employee.system.model.TaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeSalaryService {

    private final EmployeeService employeeService;
    private final PolandTaxCalculator polandTaxCalculator;

    public TaxResponse findEmployeeNetSalary(UUID employeeId) {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
        return polandTaxCalculator.calculate(employee.getSalary(), true);
    }
}
