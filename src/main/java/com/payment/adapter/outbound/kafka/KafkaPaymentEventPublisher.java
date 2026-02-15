package com.payment.adapter.outbound.kafka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.adapter.outbound.outbox.OutboxEntity;
import com.payment.adapter.outbound.outbox.OutboxRepository;
import com.payment.adapter.outbound.outbox.OutboxStatus;
import com.payment.domain.model.PaymentEvent;
import com.payment.domain.port.PaymentEventPublisher;

import jakarta.transaction.Transactional;

public class KafkaPaymentEventPublisher implements PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public KafkaPaymentEventPublisher(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }
    Logger logger = Logger.getLogger(KafkaPaymentEventPublisher.class.getName());

    private final ObjectMapper objectMapper;

    @Autowired
    OutboxRepository outboxRepository;
    
    @Override
    public void publish(PaymentEvent paymentEvent) {

        logger.info("Publishing payment event to Kafka: " + paymentEvent.getEventId());
        kafkaTemplate.send("payment-created", paymentEvent.getEventId() ,paymentEvent);
        // After publishing the event to Kafka, we save the event to the outbox table with status PENDING, so that the outbox processor can pick it up and update the status to PROCESSED or FAILED based on the result of the processing.
        savePaymentEvent(paymentEvent);
        
    }

     @Transactional
    public void savePaymentEvent(PaymentEvent event) {
        try {
            OutboxEntity entity = new OutboxEntity();
            entity.setAggregateId(event.getEventId());
            entity.setAggregateType("PaymentEvent");
            entity.setEventType("PaymentCreated");
            entity.setPayload(objectMapper.writeValueAsString(event));
            entity.setStatus(OutboxStatus.PENDING);

            outboxRepository.save(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize PaymentEvent", e);
        }
    }

}
