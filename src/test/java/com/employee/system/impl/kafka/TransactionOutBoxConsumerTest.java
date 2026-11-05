package com.employee.system.impl.kafka;

import com.employee.system.impl.IdempotencyService;
import com.employee.system.model.outbox.TransactionCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionOutBoxConsumerTest {
    private final ObjectMapper objectMapper = mock(ObjectMapper.class);
    private final ObjectMapper testMapper = new ObjectMapper();
    private final IdempotencyService idempotencyService = mock(IdempotencyService.class);

    @InjectMocks
    private TransactionOutBoxConsumer sut;

    @Test
    public void shouldProcessValidEvent() {
        JsonNode message = testMapper.readTree("""
                {
                  "schema": {
                    "type": "string",
                    "optional": true,
                    "name": "io.debezium.data.Json",
                    "version": 1
                  },
                  "payload":
                    "{\\"amount\\":500,\\"transactionId\\":3165,\\"sourceAccountId\\":1,\\"destinationAccountId\\":2}"
                }
                """);

        JsonNode payload = message.get("payload");
        JsonNode jsonNode = testMapper.readTree(payload.stringValue());
        when(objectMapper.readTree(payload.stringValue())).thenReturn(jsonNode);
        when(objectMapper.treeToValue(jsonNode, TransactionCreatedEvent.class)).thenReturn(
                new TransactionCreatedEvent(3156L,
                        1L, 2L, new BigDecimal("500"))
        );
        when(idempotencyService.alreadyProcessed(3156L)).thenReturn(Boolean.TRUE);
        sut.accept(message);
        // when(objectMapper.treeToValue())

        verify(objectMapper)
                .treeToValue(jsonNode,
                        TransactionCreatedEvent.class);
    }


}
