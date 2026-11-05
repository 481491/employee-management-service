package com.employee.system.controller;

import com.employee.system.impl.PolandTaxService;
import com.employee.system.model.PolandTaxRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tax")
@RequiredArgsConstructor
public class PolandTaxController {

    private final PolandTaxService polandTaxService;

    @PostMapping("/save")
    public ResponseEntity<Void> addTaxSlabs(@RequestBody PolandTaxRequest request) {
        polandTaxService.saveTaxSlabs(request);
        return null;
    }
}
