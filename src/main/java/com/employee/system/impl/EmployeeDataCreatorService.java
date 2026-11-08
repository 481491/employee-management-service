/*
package com.employee.system.impl;

import com.employee.system.enums.EmployeeStatus;
import com.employee.system.model.BankAccountRequest;
import com.employee.system.model.BankAccountResponse;
import com.employee.system.model.EmployeeDto;
import com.employee.system.model.TaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Order(1)
@RequiredArgsConstructor
public class EmployeeDataCreatorService implements CommandLineRunner {

    private final EmployeeService employeeService;

    private final static String PREFIX_EMP_NAME = "Emp_";
    private final static String EMAIl_POSTFIX = "@email.com";
    private final Executor employeeExecutor;
    private final BankAccountService accountService;
    private final EmployeeSalaryService salaryService;


    @Override
    public void run(String... args)  {
        List<EmployeeDto> newEmployees = getEmployeeDate();
        List<CompletableFuture<TaxResponse>> futures = newEmployees.stream()
                .map(dto ->
                        CompletableFuture.supplyAsync(
                                () -> employeeService.saveEmployee(dto), employeeExecutor
                        ).thenApply(
                                saved ->
                                {
                                    System.out.println("Employee :" + saved.getName() + " account processing....");
                                    BankAccountRequest accountRequest = new BankAccountRequest(
                                            "ING",
                                            String.valueOf(ThreadLocalRandom.current()
                                                    .nextInt(100000, 999999)),
                                            BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(3500, 10000)),
                                            UUID.fromString(saved.getId())
                                    );
                                    return accountService.createAccount(accountRequest);
                                }).thenApply(account ->
                                salaryService.findEmployeeNetSalary(account.employeeId())
                        )).toList();
        futures.stream().map(CompletableFuture::join).toList()
                .forEach(e -> System.out.println(
                        e.netSalary() + "-" + e.healthInsurance() + "-" + e.incomeTax()));
    }

    private List<EmployeeDto> getEmployeeDate() {
        List<EmployeeDto> employees = new ArrayList<>();
        List<String> depart = List.of("HR", "Finance", "IT", "Operations", "Sales");
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            EmployeeDto dto = EmployeeDto.builder()
                    .name(PREFIX_EMP_NAME.concat(String.valueOf(c)))
                    .email(Character.toLowerCase(c) + EMAIl_POSTFIX)
                    .employeeStatus(EmployeeStatus.ACTIVE)
                    .salary(new BigDecimal(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999))))
                    .department(depart.get(ThreadLocalRandom.current().nextInt(depart.size()))).build();
            i++;
            if (i == depart.size()) {
                i = 0;
            }
            employees.add(dto);
        }
        return employees;

    }
}

*/
