package com.employee.system.builders;


import com.employee.system.entity.Employee;
import com.employee.system.model.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeBuilderDto {

    public static Employee of(EmployeeDto dto) {
        return Employee
                .builder()
                .name(dto.getName())
                .department(dto.getDepartment())
                .email(dto.getEmail())
                .employeeStatus(dto.getEmployeeStatus())
                .salary(dto.getSalary())
                .build();

    }

    public static EmployeeDto of(Employee entity) {
        return EmployeeDto
                .builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .email(entity.getEmail())
                .department(entity.getDepartment())
                .employeeStatus(entity.getEmployeeStatus())
                .salary(entity.getSalary())
                .build();
    }
}
