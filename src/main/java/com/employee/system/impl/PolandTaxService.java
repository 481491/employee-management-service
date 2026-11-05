package com.employee.system.impl;

import com.employee.system.entity.PolandTax;
import com.employee.system.model.PolandTaxRequest;
import com.employee.system.repositories.PolandTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.employee.system.mappers.PolandTaxMapper.mapToEntity;

@Service
@RequiredArgsConstructor
public class PolandTaxService {

    private final PolandTaxRepository repository;


    @Transactional
    public void saveTaxSlabs(PolandTaxRequest request) {
        PolandTax polandTax = mapToEntity(request);
        repository.save(polandTax);
    }

}
