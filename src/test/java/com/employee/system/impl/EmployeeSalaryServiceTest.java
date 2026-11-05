package com.employee.system.impl;

import com.employee.system.enums.EmployeeStatus;
import com.employee.system.model.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeSalaryServiceTest {
    @Mock
    private EmployeeService employeeService;
    @Mock
    private PolandTaxCalculator taxCalculator;
    private  EmployeeSalaryService sut;

    @BeforeEach
    public void setUp() {
        sut = new EmployeeSalaryService(employeeService, taxCalculator);
    }

    @Test
    public void testFindEmployeeNetSalary() {
        UUID employeeId = UUID.fromString("bd5b8f37-c2b0-4dc5-b81c-2d5bd9b9cc84");
        EmployeeDto anyEmploee = EmployeeDto.builder()
                .salary(new BigDecimal(5000))
                .id("bd5b8f37-c2b0-4dc5-b81c-2d5bd9b9cc84")
                .employeeStatus(EmployeeStatus.ACTIVE)
                .email("any@email.com")
                .name("anyName")
                .build();

        when(employeeService.getEmployeeById(employeeId)).thenReturn(anyEmploee);
        sut.findEmployeeNetSalary(employeeId);
    }
}
