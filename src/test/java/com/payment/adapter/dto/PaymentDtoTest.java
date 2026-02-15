package com.payment.adapter.dto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentDtoTest {
     private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateSuccessfullyWhenAllFieldsAreValid() {
        PaymentDto dto = buildValidDto();

        Set<ConstraintViolation<PaymentDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenOrderIdIsBlank() {
        PaymentDto dto = buildValidDto();
        dto.setOrderId("");

        Set<ConstraintViolation<PaymentDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Order ID is required",
                violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenAmountIsZero() {
        PaymentDto dto = buildValidDto();
        dto.setAmount(BigDecimal.ZERO);

        Set<ConstraintViolation<PaymentDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        PaymentDto dto = buildValidDto();
        dto.setCustomerEmail("invalid-email");

        Set<ConstraintViolation<PaymentDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Invalid email format",
                violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenPaymentMethodIsInvalid() {
        PaymentDto dto = buildValidDto();
        dto.setPaymentMethod("BITCOIN");

        Set<ConstraintViolation<PaymentDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    private PaymentDto buildValidDto() {
        PaymentDto dto = new PaymentDto();
        dto.setOrderId("ORD-123");
        dto.setAmount(new BigDecimal("100.50"));
        dto.setCustomerId("CUST-001");
        dto.setPaymentMethod("CREDIT_CARD");
        dto.setCardNumber("1234567890123456");
        dto.setCardExpiry("12/29");
        dto.setCardCvv("123");
        dto.setStatus("AUTHORIZED");
        dto.setDescription("Payment for order");
        dto.setTransactionId("TX-12345");
        dto.setBillingAddress("Street 1, City");
        dto.setShippingAddress("Street 2, City");
        dto.setCustomerEmail("customer@email.com");
        dto.setCustomerPhone("+5511999999999");
        dto.setAuthorizationCode("AUTH123");

        return dto;
    }
}
