package com.payment.domain.service;

import com.payment.domain.model.PaymentEvent;
import com.payment.domain.port.CreatePaymentEventUseCase;
import com.payment.domain.port.PaymentEventPublisher;

//This is the service class that implements the use case to create a payment event, it will contain the business logic related to creating a payment event, and it will use the PaymentEventPublisher to publish the event to Kafka.
public class PaymentService implements CreatePaymentEventUseCase {
    //When we use hexagonal architecture we want to decouple the domain logic from the infrastructure, so we use a port to define the interface for the infrastructure to implement, and then we use a service class to implement the business logic and use the port to interact with the infrastructure.
    private final PaymentEventPublisher paymentEventPublisher;

    // When we use hexagonal architecture we want to decouple the domain logic from the infrastructure, so we use constructor injection to inject the dependencies, this way we can easily swap the implementation of the dependencies without affecting the domain logic.
    public PaymentService(PaymentEventPublisher paymentEventPublisher) {
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Override
    public void createPaymentEvent(PaymentEvent paymentEvent) {    
        // Publish the payment event using the publisher
        paymentEventPublisher.publish(paymentEvent);
    }

}
