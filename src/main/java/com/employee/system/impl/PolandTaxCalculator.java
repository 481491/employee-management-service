package com.employee.system.impl;

import com.employee.system.model.TaxResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PolandTaxCalculator {

    private static final BigDecimal PENSION_RATE = new BigDecimal("0.0976");
    private static final BigDecimal DISABILITY_RATE = new BigDecimal("0.015");
    private static final BigDecimal SICKNESS_RATE = new BigDecimal("0.0245");
    private static final BigDecimal HEALTH_RATE = new BigDecimal("0.09");

    public TaxResponse calculate(
            BigDecimal grossSalary,
            boolean pit2) {

        BigDecimal pension =
                grossSalary.multiply(PENSION_RATE);

        BigDecimal disability =
                grossSalary.multiply(DISABILITY_RATE);

        BigDecimal sickness =
                grossSalary.multiply(SICKNESS_RATE);

        BigDecimal social =
                pension.add(disability)
                        .add(sickness);

        BigDecimal taxableBase =
                grossSalary.subtract(social);

        BigDecimal health =
                taxableBase.multiply(HEALTH_RATE);

        BigDecimal tax =
                taxableBase.multiply(new BigDecimal("0.12"));

        if (pit2) {
            tax = tax.subtract(new BigDecimal("300"));
        }

        if (tax.compareTo(BigDecimal.ZERO) < 0) {
            tax = BigDecimal.ZERO;
        }

        BigDecimal netSalary =
                grossSalary
                        .subtract(social)
                        .subtract(health)
                        .subtract(tax);

        return new TaxResponse(
                social.setScale(2, RoundingMode.HALF_UP),
                health.setScale(2, RoundingMode.HALF_UP),
                tax.setScale(2, RoundingMode.HALF_UP),
                netSalary.setScale(2, RoundingMode.HALF_UP)
        );
    }
}
