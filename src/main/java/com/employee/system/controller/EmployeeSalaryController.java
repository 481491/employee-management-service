package com.employee.system.controller;

import com.employee.system.impl.EmployeeSalaryService;
import com.employee.system.model.TaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/salary")
public class EmployeeSalaryController {
    private final EmployeeSalaryService employeeSalaryService;

    @GetMapping("/netsalary/{employeeId}")
    public ResponseEntity<TaxResponse> findEmployeeNetSalary(@PathVariable UUID employeeId) {
        TaxResponse taxResponse = employeeSalaryService.findEmployeeNetSalary(employeeId);
        return new ResponseEntity<>(taxResponse, HttpStatus.OK);

    }
}
