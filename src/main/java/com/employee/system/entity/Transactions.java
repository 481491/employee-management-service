package com.employee.system.entity;

/*
* └── Salary Credit
    ├── Payment Date
    ├── Amount
    ├── Status (PENDING, SUCCESS, FAILED)
    └── Transaction Reference
* */

import com.employee.system.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;
    private BigDecimal transactionAmount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String reference;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_account_id")
    private BankAccount sourceAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_account_id")
    private BankAccount destinationAccount;

    private Instant createdDate;
}
