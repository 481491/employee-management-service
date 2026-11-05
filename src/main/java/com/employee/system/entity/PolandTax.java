package com.employee.system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolandTax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal pensionRate;
    private BigDecimal disabilityRate;
    private BigDecimal sicknessRate;
    private BigDecimal healthRate;
    private BigDecimal taxRate;
    private BigDecimal pitRate;


}
