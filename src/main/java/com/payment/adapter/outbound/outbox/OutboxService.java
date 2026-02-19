package com.payment.adapter.outbound.outbox;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.domain.model.PaymentEvent;
import com.payment.domain.port.PaymentEventPublisher;

import jakarta.transaction.Transactional;

public class OutboxService implements PaymentEventPublisher {


    ObjectMapper objectMapper = new ObjectMapper();
    
    
    OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    @Override
    public void savePaymentEvent(PaymentEvent event) {
        // Convert PaymentEvent to OutboxEntity and save to DB with PENDING status
        try {
            OutboxEntity entity = new OutboxEntity();
            entity.setAggregateId(event.getEventId());
            entity.setAggregateType("Payment");
            entity.setEventType("PaymentCreated");
            entity.setPayload(objectMapper.writeValueAsString(event));
            entity.setStatus(OutboxStatus.PENDING);
            entity.setCreatedAt(LocalDateTime.now());

            outboxRepository.save(entity);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize PaymentEvent", e);
        }
    }

}
