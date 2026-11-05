package com.employee.system.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PolandTaxDto {
    private BigDecimal pensionRate;
    private BigDecimal sicknessRate;
    private BigDecimal disabilityRate;
    private BigDecimal healthRate;
    private BigDecimal taxRate;
    private  BigDecimal pitRate;

}
