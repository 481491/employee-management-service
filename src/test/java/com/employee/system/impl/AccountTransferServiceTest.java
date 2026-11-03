package com.employee.system.impl;

import com.employee.system.entity.BankAccount;
import com.employee.system.entity.OutboxEvent;
import com.employee.system.entity.Transactions;
import com.employee.system.enums.TransactionStatus;
import com.employee.system.exception.AccountNotFoundException;
import com.employee.system.exception.AccountTransferException;
import com.employee.system.model.AccountTransferRequest;
import com.employee.system.model.AccountTransferResponse;
import com.employee.system.repositories.BankAccountRepository;
import com.employee.system.repositories.OutBoxEventRepository;
import com.employee.system.repositories.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountTransferServiceTest {

    private final BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
    private final TransactionsRepository transactionsRepository = mock(TransactionsRepository.class);
    private final ObjectMapper mapper = mock(ObjectMapper.class);
    private final OutBoxEventRepository eventRepository = mock(OutBoxEventRepository.class);
    private final AccountTransferService service = new AccountTransferService(
            bankAccountRepository,
            transactionsRepository,
            eventRepository,
            mapper
    );
    private BankAccount source;
    private BankAccount destination;

    @BeforeEach
    void setUp() {
        source = new BankAccount(1L, "Bank", "source", new BigDecimal("100.00"), null);
        destination = new BankAccount(2L, "Bank", "destination", new BigDecimal("25.00"), null);
    }

    @Test
    void transfersFundsAndRecordsTransaction() {
        when(bankAccountRepository.lockByAccountIds(List.of(1L, 2L)))
                .thenReturn(List.of(source, destination));
        when(transactionsRepository.save(any(Transactions.class))).thenAnswer(invocation -> {
            Transactions transaction = invocation.getArgument(0);
            transaction.setTransactionId(7L);
            return transaction;
        });

        AccountTransferResponse response = service.transfer(
                new AccountTransferRequest(1L, 2L, new BigDecimal("30.00"))
        );

        assertEquals(new BigDecimal("70.00"), source.getBalance());
        assertEquals(new BigDecimal("55.00"), destination.getBalance());
        assertEquals(7L, response.transactionId());
        assertEquals(TransactionStatus.SUCCESS, response.status());
        verify(transactionsRepository).save(any(Transactions.class));
        verify(eventRepository).save(any(OutboxEvent.class));
    }

    @Test
    void rejectsTransferWhenFundsAreInsufficient() {
        when(bankAccountRepository.lockByAccountIds(List.of(1L, 2L)))
                .thenReturn(List.of(source, destination));

        assertThrows(AccountTransferException.class, () -> service.transfer(
                new AccountTransferRequest(1L, 2L, new BigDecimal("100.01"))
        ));

        assertEquals(new BigDecimal("100.00"), source.getBalance());
        assertEquals(new BigDecimal("25.00"), destination.getBalance());
        verify(transactionsRepository, never()).save(any(Transactions.class));
    }

    @Test
    void rejectsTransfersToTheSameAccount() {
        assertThrows(AccountTransferException.class, () -> service.transfer(
                new AccountTransferRequest(1L, 1L, BigDecimal.ONE)
        ));

        verify(bankAccountRepository, never()).lockByAccountIds(any());
    }

    @Test
    void rejectsTransferWhenAnAccountDoesNotExist() {
        when(bankAccountRepository.lockByAccountIds(List.of(1L, 2L)))
                .thenReturn(List.of(source));

        assertThrows(AccountNotFoundException.class, () -> service.transfer(
                new AccountTransferRequest(1L, 2L, BigDecimal.ONE)
        ));

        verify(transactionsRepository, never()).save(any(Transactions.class));
    }
}
