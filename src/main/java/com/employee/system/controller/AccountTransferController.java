package com.employee.system.controller;

import com.employee.system.impl.AccountTransferService;
import com.employee.system.model.AccountTransferRequest;
import com.employee.system.model.AccountTransferResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountTransferController {

    private final AccountTransferService accountTransferService;

    public AccountTransferController(AccountTransferService accountTransferService) {
        this.accountTransferService = accountTransferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountTransferResponse> transfer(
            @Valid @RequestBody AccountTransferRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountTransferService.transfer(request));
    }
}
