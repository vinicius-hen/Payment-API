package com.payment.adapter.outbound.outbox;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@EnableScheduling
public class OutboxEventPublisher {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxEventPublisher(OutboxRepository outboxRepository,
                           KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000) // runs every 5 seconds
    @Transactional
    public void processOutbox() {

        List<OutboxEntity> events =
            outboxRepository.findByStatus(OutboxStatus.PENDING);

        for (OutboxEntity event : events) {
            try {
                // Publish to Kafka and wait for ack. If successful, mark as PROCESSED. If failed, mark as FAILED.
               kafkaTemplate.send("payment-created", event.getAggregateId() ,event.getPayload()).get(); // wait for Kafka ack (important)

                event.setStatus(OutboxStatus.PROCESSED);

            } catch (Exception e) {

                event.setStatus(OutboxStatus.FAILED);
            }
        }
    }
}
