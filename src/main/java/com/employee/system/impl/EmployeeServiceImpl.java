package com.employee.system.impl;



import com.employee.system.builders.EmployeeBuilderDto;
import com.employee.system.entity.Employee;
import com.employee.system.model.EmployeeDto;
import com.employee.system.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDto> employeesList() {
        return null;
    }

    @Override
    public EmployeeDto getEmployee() {
        return null;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto dto) {

        Employee entity = EmployeeBuilderDto.of(dto);
        Employee save = employeeRepository.save(entity);
        return EmployeeBuilderDto.of(save);
    }

    @Override
    public EmployeeDto getEmployeeById(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(EmployeeBuilderDto::of)
                .orElseThrow(() -> new RuntimeException("save terminated"));
    }
}
