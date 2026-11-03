package com.employee.system.repositories;

import com.employee.system.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    boolean existsBySourceAccount_AccountIdOrDestinationAccount_AccountId(long sourceAccountId, long destinationAccountId);
}
