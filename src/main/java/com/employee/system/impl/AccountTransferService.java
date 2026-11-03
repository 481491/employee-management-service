package com.employee.system.impl;

import com.employee.system.entity.BankAccount;
import com.employee.system.entity.OutboxEvent;
import com.employee.system.entity.Transactions;
import com.employee.system.enums.EventStatus;
import com.employee.system.enums.TransactionStatus;
import com.employee.system.exception.AccountNotFoundException;
import com.employee.system.exception.AccountTransferException;
import com.employee.system.model.AccountTransferRequest;
import com.employee.system.model.AccountTransferResponse;
import com.employee.system.model.outbox.TransactionCreatedEvent;
import com.employee.system.repositories.BankAccountRepository;
import com.employee.system.repositories.OutBoxEventRepository;
import com.employee.system.repositories.TransactionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountTransferService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionsRepository transactionsRepository;
    private final OutBoxEventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public AccountTransferService(
            BankAccountRepository bankAccountRepository,
            TransactionsRepository transactionsRepository,
            OutBoxEventRepository eventRepository,
            ObjectMapper objectMapper
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionsRepository = transactionsRepository;
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public BigDecimal getTotalBalance() {
        return bankAccountRepository.findAll().stream().map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountTransferResponse transfer(AccountTransferRequest request) {
        if (request.sourceAccountId().equals(request.destinationAccountId())) {
            throw new AccountTransferException("Source and destination accounts must be different");
        }

        List<Long> accountIds = List.of(request.sourceAccountId(), request.destinationAccountId())
                .stream()
                .sorted()
                .toList();
        Map<Long, BankAccount> accountsById = bankAccountRepository
                //.lockByAccountIds(accountIds)
                .findAllByAccountIdInOrderByAccountIdAsc(accountIds)
                .stream()
                .collect(Collectors.toMap(BankAccount::getAccountId, Function.identity()));

        BankAccount source = accountsById.get(request.sourceAccountId());
        BankAccount destination = accountsById.get(request.destinationAccountId());
        if (source == null || destination == null) {
            throw new AccountNotFoundException("Source or destination account was not found");
        }

        BigDecimal amount = request.amount();
        if (source.getBalance() == null || destination.getBalance() == null) {
            throw new AccountTransferException("Account balance is not initialized");
        }
        if (source.getBalance().compareTo(amount) < 0) {
            throw new AccountTransferException("Insufficient funds");
        }

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));

        Transactions transaction = Transactions.builder()
                .transactionAmount(amount)
                .status(TransactionStatus.SUCCESS)
                .reference(UUID.randomUUID().toString())
                .sourceAccount(source)
                .destinationAccount(destination)
                .createdDate(Instant.now())
                .build();
        Transactions savedTransaction = transactionsRepository.save(transaction);

        TransactionCreatedEvent event = new TransactionCreatedEvent(
                transaction.getTransactionId(),
                transaction.getSourceAccount().getAccountId(),
                transaction.getDestinationAccount().getAccountId(),
                transaction.getTransactionAmount()
        );


        OutboxEvent outbox = OutboxEvent.builder()
                .aggregateType("TRANSACTION")
                .aggregateId(transaction.getTransactionId())
                .eventType("TRANSACTION_CREATED")
                .payload(objectMapper.writeValueAsString(event))
                .status(EventStatus.PENDING)
                .createdAt(Instant.now())
                .processedAt(Instant.now())
                .build();

       eventRepository.save(outbox);

        return new AccountTransferResponse(
                savedTransaction.getTransactionId(),
                savedTransaction.getReference(),
                source.getAccountId(),
                destination.getAccountId(),
                savedTransaction.getTransactionAmount(),
                savedTransaction.getStatus()
        );
    }
}
