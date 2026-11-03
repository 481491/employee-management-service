package com.employee.system.entity;

import com.employee.system.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "employee_name")
    private String name;
    @Column(name = "employee_department")
    private String department;
    @Column(name = "employee_email")
    private String email;
    @Column(name = "active_status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;
    @Column(name = "salary")
    private BigDecimal salary;

    @OneToOne(mappedBy = "employee")
    private BankAccount account;
}
