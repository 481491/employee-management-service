package com.employee.system.controller;

import com.employee.system.impl.BankAccountService;
import com.employee.system.model.BankAccountRequest;
import com.employee.system.model.BankAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllAccounts());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable long accountId) {
        return ResponseEntity.ok(bankAccountService.getAccount(accountId));
    }

    @PostMapping
    public ResponseEntity<BankAccountResponse> createAccount(@Valid @RequestBody BankAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.createAccount(request));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<BankAccountResponse> updateAccount(
            @PathVariable long accountId,
            @Valid @RequestBody BankAccountRequest request
    ) {
        return ResponseEntity.ok(bankAccountService.updateAccount(accountId, request));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable long accountId) {
        bankAccountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
