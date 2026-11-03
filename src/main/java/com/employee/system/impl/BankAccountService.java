package com.employee.system.impl;

import com.employee.system.entity.BankAccount;
import com.employee.system.entity.Employee;
import com.employee.system.exception.AccountConflictException;
import com.employee.system.exception.AccountNotFoundException;
import com.employee.system.exception.EmployeeNotFoundException;
import com.employee.system.model.BankAccountRequest;
import com.employee.system.model.BankAccountResponse;
import com.employee.system.repositories.BankAccountRepository;
import com.employee.system.repositories.EmployeeRepository;
import com.employee.system.repositories.TransactionsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final EmployeeRepository employeeRepository;
    private final TransactionsRepository transactionsRepository;

    public BankAccountService(
            BankAccountRepository bankAccountRepository,
            EmployeeRepository employeeRepository,
            TransactionsRepository transactionsRepository
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.employeeRepository = employeeRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Transactional(readOnly = true)
    public List<BankAccountResponse> getAllAccounts() {
        return bankAccountRepository.findAll(Sort.by("accountId"))
                .stream()
                .map(BankAccountService::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BankAccountResponse getAccount(long accountId) {
        return toResponse(findAccount(accountId));
    }

    @Transactional
    public BankAccountResponse createAccount(BankAccountRequest request) {
        ensureAccountNumberAvailable(request.accountNumber(), null);
        Employee employee = findAvailableEmployee(request.employeeId(), null);

        BankAccount account = new BankAccount();
        updateAccount(account, request, employee);
        return toResponse(bankAccountRepository.save(account));
    }

    @Transactional
    public BankAccountResponse updateAccount(long accountId, BankAccountRequest request) {
        BankAccount account = findAccount(accountId);
        ensureAccountNumberAvailable(request.accountNumber(), accountId);
        Employee employee = findAvailableEmployee(request.employeeId(), accountId);

        updateAccount(account, request, employee);
        return toResponse(bankAccountRepository.save(account));
    }

    @Transactional
    public void deleteAccount(long accountId) {
        BankAccount account = findAccount(accountId);
        if (transactionsRepository.existsBySourceAccount_AccountIdOrDestinationAccount_AccountId(accountId, accountId)) {
            throw new AccountConflictException("Account cannot be deleted because it has transaction history");
        }
        bankAccountRepository.delete(account);
    }

    private BankAccount findAccount(long accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " was not found"));
    }

    private void ensureAccountNumberAvailable(String accountNumber, Long currentAccountId) {
        boolean alreadyUsed = currentAccountId == null
                ? bankAccountRepository.existsByAccountNumber(accountNumber)
                : bankAccountRepository.existsByAccountNumberAndAccountIdNot(accountNumber, currentAccountId);
        if (alreadyUsed) {
            throw new AccountConflictException("Account number is already in use");
        }
    }

    private Employee findAvailableEmployee(UUID employeeId, Long currentAccountId) {
        if (employeeId == null) {
            return null;
        }
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee " + employeeId + " was not found"));
        boolean alreadyAssigned = currentAccountId == null
                ? bankAccountRepository.existsByEmployee_Id(employeeId)
                : bankAccountRepository.existsByEmployee_IdAndAccountIdNot(employeeId, currentAccountId);
        if (alreadyAssigned) {
            throw new AccountConflictException("Employee already has a bank account");
        }
        return employee;
    }

    private void updateAccount(BankAccount account, BankAccountRequest request, Employee employee) {
        account.setBankName(request.bankName());
        account.setAccountNumber(request.accountNumber());
        account.setBalance(request.balance());
        account.setEmployee(employee);
    }

    private static BankAccountResponse toResponse(BankAccount account) {
        return new BankAccountResponse(
                account.getAccountId(),
                account.getBankName(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getEmployee() == null ? null : account.getEmployee().getId()
        );
    }
}
