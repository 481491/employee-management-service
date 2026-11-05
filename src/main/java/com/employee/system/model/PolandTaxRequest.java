package com.employee.system.model;

import java.math.BigDecimal;

public record PolandTaxRequest(BigDecimal pensionRate,
                               BigDecimal sicknessRate,
                               BigDecimal disabilityRate,
                               BigDecimal healthRate,
                               BigDecimal taxRate,
                               BigDecimal pitRate) {
}
