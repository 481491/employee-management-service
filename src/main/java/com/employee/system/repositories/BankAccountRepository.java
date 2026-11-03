package com.employee.system.repositories;

import com.employee.system.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByAccountNumberAndAccountIdNot(String accountNumber, long accountId);

    boolean existsByEmployee_Id(UUID employeeId);

    boolean existsByEmployee_IdAndAccountIdNot(UUID employeeId, long accountId);

    /*@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select account from BankAccount account where account.accountId in :accountIds order by account.accountId")
    List<BankAccount> lockByAccountIds(@Param("accountIds") List<Long> accountIds);*/

    List<BankAccount> findAllByAccountIdInOrderByAccountIdAsc(List<Long> accountIds);
}
