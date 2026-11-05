package com.employee.system.controller;

import com.employee.system.entity.Employee;
import com.employee.system.impl.EmployeeSalaryService;
import com.employee.system.model.TaxResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeSalaryControllerTest {
    private EmployeeSalaryService employeeSalaryService = mock(EmployeeSalaryService.class);
    private EmployeeSalaryController salaryController;

    @BeforeEach
    public void setUp() {
        salaryController = new EmployeeSalaryController(employeeSalaryService);
    }

    @Test
    public void testFindEmployeeNetSalary() {
        UUID employeeId = UUID.fromString("bd5b8f37-c2b0-4dc5-b81c-2d5bd9b9cc84");
        when(employeeSalaryService.findEmployeeNetSalary(employeeId)).thenReturn(null);
        ResponseEntity<TaxResponse> netSalary = salaryController.findEmployeeNetSalary(employeeId);

        //assertEquals(null, netSalary);
    }


}
