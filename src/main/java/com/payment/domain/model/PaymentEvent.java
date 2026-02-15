package com.payment.domain.model;

import java.math.BigDecimal;

import com.payment.adapter.dto.PaymentDto;

public class PaymentEvent {

    private String orderId;
    private BigDecimal amount;
    private String customerId;
    private String paymentMethod;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
    private String status;
    private String description;
    private String transactionId;
    private String billingAddress;
    private String shippingAddress;
    private String customerEmail;
    private String customerPhone;
    private String authorizationCode;
    private String eventId;

   


    public PaymentEvent(PaymentDto dto ){
        this.orderId = dto.getOrderId();
        this.amount = dto.getAmount();
        this.customerId = dto.getCustomerId();
        this.paymentMethod = dto.getPaymentMethod();
        this.cardNumber = dto.getCardNumber();
        this.cardExpiry = dto.getCardExpiry();
        this.cardCvv = dto.getCardCvv();
        this.status = dto.getStatus();
        this.description = dto.getDescription();
        this.transactionId = dto.getTransactionId();
        this.billingAddress = dto.getBillingAddress();
        this.shippingAddress = dto.getShippingAddress();
        this.customerEmail = dto.getCustomerEmail();
        this.customerPhone = dto.getCustomerPhone();
        this.authorizationCode = dto.getAuthorizationCode();
        this.eventId = java.util.UUID.randomUUID().toString(); //IDEMPOTENCIA
    }
    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiry() {
        return this.cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCardCvv() {
        return this.cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBillingAddress() {
        return this.billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCustomerEmail() {
        return this.customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return this.customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getAuthorizationCode() {
        return this.authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }


    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
     public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


}
