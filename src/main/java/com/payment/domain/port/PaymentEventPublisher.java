package com.payment.domain.port;

import com.payment.domain.model.PaymentEvent;

public interface PaymentEventPublisher {
    void publish(PaymentEvent paymentEvent);
}
