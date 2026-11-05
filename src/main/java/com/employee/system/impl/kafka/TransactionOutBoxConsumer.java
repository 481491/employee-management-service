package com.employee.system.impl.kafka;

import com.employee.system.exception.PermanentProcessingException;
import com.employee.system.impl.IdempotencyService;
import com.employee.system.model.outbox.TransactionCreatedEvent;
import com.employee.system.repositories.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionOutBoxConsumer implements Consumer<JsonNode> {
    private final ObjectMapper objectMapper;
    private final IdempotencyService idempotencyService;


    @Override
    public void accept(JsonNode message) {
        JsonNode eventPayload = extractEventPayload(message);
        TransactionCreatedEvent event = objectMapper.treeToValue(eventPayload, TransactionCreatedEvent.class);
        if (event.transactionId() == null
                || event.sourceAccountId() == null
                || event.destinationAccountId() == null
                || event.amount() == null) {
            throw new PermanentProcessingException("Transaction-created event is missing required fields");
        }

        if(idempotencyService.alreadyProcessed(event.sourceAccountId())) {
            log.info("Duplicate event ignored");
        }
        log.info("Received transaction-created event: {}", event);
    }

    private JsonNode extractEventPayload(JsonNode message) {
        JsonNode record = message;
        if (record.has("payload") && record.get("payload").isObject()) {
            record = record.get("payload");
        }
        if (record.has("after")) {
            record = record.get("after");
        }

        JsonNode payloadJson = record.has("payloadJson")
                ? record.get("payloadJson")
                : record.get("payload_json");
        if (payloadJson != null && !payloadJson.isNull()) {
            return payloadJson;
        }

        JsonNode payload = record.get("payload");
        if (payload == null || payload.isNull()) {
            throw new IllegalArgumentException("Kafka message does not contain an outbox event payload");
        }
        return payload.isString() ? objectMapper.readTree(payload.stringValue()) : payload;
    }
}
