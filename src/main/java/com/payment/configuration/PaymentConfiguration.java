package com.payment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.payment.adapter.outbound.kafka.KafkaPaymentEventPublisher;
import com.payment.domain.model.PaymentEvent;
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
    public PaymentEventPublisher paymentEventPublisher(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        return new KafkaPaymentEventPublisher(kafkaTemplate);
    }
}
