package com.employee.system.entity;

import com.employee.system.enums.EventStatus;
import com.employee.system.model.outbox.TransactionCreatedEvent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long outboxId;

    private String aggregateType; // ACCOUNT, TRANSACTION

    private Long aggregateId;     // 123

    private String eventType;     // TRANSACTION_CREATED

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String payload;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private TransactionCreatedEvent payloadJson;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private Instant createdAt;
    private Instant processedAt;
}
