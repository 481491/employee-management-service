package com.employee.system.mappers;

import com.employee.system.entity.PolandTax;
import com.employee.system.model.PolandTaxDto;
import com.employee.system.model.PolandTaxRequest;

public class PolandTaxMapper {

    public static PolandTax mapToEntity(PolandTaxRequest request) {
        return PolandTax.builder()
                .healthRate(request.healthRate())
                .sicknessRate(request.sicknessRate())
                .disabilityRate(request.disabilityRate())
                .taxRate(request.taxRate())
                .pensionRate(request.pensionRate())
                .pitRate(request.pitRate()).build();
    }

    public static PolandTaxDto mapToDto(PolandTax entity) {
        return PolandTaxDto.builder()
                .healthRate(entity.getHealthRate())
                .sicknessRate(entity.getSicknessRate())
                .disabilityRate(entity.getDisabilityRate())
                .taxRate(entity.getTaxRate())
                .pensionRate(entity.getPensionRate())
                .pitRate(entity.getPitRate()).build();
    }
}
