package com.payment.domain.port;

import com.payment.domain.model.PaymentEvent;

public interface CreatePaymentEventUseCase {
    void createPaymentEvent(PaymentEvent paymentEvent);
}
