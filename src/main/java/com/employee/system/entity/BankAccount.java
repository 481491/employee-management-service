package com.employee.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    private String bankName;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    // Optimistic-locking alternative:
/*     @Version
     private Long version;*/
}
