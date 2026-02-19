package com.payment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.payment.adapter.outbound.outbox.OutboxRepository;
import com.payment.adapter.outbound.outbox.OutboxService;
import com.payment.domain.port.CreatePaymentEventUseCase;
import com.payment.domain.port.PaymentEventPublisher;
import com.payment.domain.service.PaymentService;

@Configuration
public class PaymentConfiguration {

    @Bean
    public CreatePaymentEventUseCase createPaymentEventUseCase(PaymentEventPublisher publisher) {
        return new PaymentService(publisher);
    }

    @Bean
    public PaymentEventPublisher paymentEventPublisher(OutboxRepository outboxRepository) {
        return new OutboxService(outboxRepository);
    }
}
