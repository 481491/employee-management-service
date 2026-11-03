package com.employee.system.controller;


import com.employee.system.impl.EmployeeService;
import com.employee.system.model.EmployeeDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<String> getEmployees() {
        return new ResponseEntity<>("find all employees", HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeDetails(@PathVariable UUID id) {
        EmployeeDto employeeById = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeById, HttpStatus.OK);
    }
    @PostMapping("/employee/save")
    public ResponseEntity<EmployeeDto> saveEmployees(@Valid @RequestBody EmployeeDto dto) {
        EmployeeDto employeeDto = employeeService.saveEmployee(dto);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

}
