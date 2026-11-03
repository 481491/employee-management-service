package com.employee.system.model;

import java.math.BigDecimal;

public record TaxRequest (BigDecimal grossSalary,
                          int age,
                          boolean ppk,
                          boolean pit2) {

}