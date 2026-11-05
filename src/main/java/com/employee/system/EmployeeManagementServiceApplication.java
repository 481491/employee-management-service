package com.employee.system;

import com.employee.system.entity.BankAccount;
import com.employee.system.impl.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching
public class EmployeeManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(EmployeeService employeeService) {
        return args -> {

           /* BankAccount
            ExecutorService executor = Executors.newFixedThreadPool(100);

            for (int i = 0; i < 100; i++) {
                int id = i;

                executor.submit(() ->
                       // employeeService.createEmployee("Employee-" + id)
                );
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);*/
        };
    }
}
