package com.employee.system.model;

import java.math.BigDecimal;

public record TaxResponse (BigDecimal socialInsurance,
                           BigDecimal healthInsurance,
                           BigDecimal incomeTax,
                           BigDecimal netSalary) {
}
