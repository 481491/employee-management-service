package com.employee.system.impl;

import com.employee.system.model.EmployeeDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<EmployeeDto> employeesList();
    EmployeeDto getEmployee();
    EmployeeDto saveEmployee(EmployeeDto dto);
    EmployeeDto getEmployeeById(UUID id);
}
