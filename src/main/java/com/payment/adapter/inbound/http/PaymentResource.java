package com.payment.adapter.inbound.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.adapter.dto.PaymentDto;
import com.payment.adapter.outbound.kafka.KafkaPaymentEventPublisher;
import com.payment.adapter.outbound.outbox.OutboxRepository;
import com.payment.domain.model.PaymentEvent;
import com.payment.domain.port.CreatePaymentEventUseCase;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments API", description = "Operations to recieve payments")
public class PaymentResource {

    @Autowired
    OutboxRepository outboxRepository;

    private final ObjectMapper objectMapper;

    private final CreatePaymentEventUseCase createPaymentEventUseCase;
    
    public PaymentResource(CreatePaymentEventUseCase createPaymentEventUseCase) {
        this.objectMapper = new ObjectMapper();
        this.createPaymentEventUseCase = createPaymentEventUseCase;
    }

    Logger logger = Logger.getLogger(KafkaPaymentEventPublisher.class.getName());

    @Operation(summary = "Create a new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentDto paymentDto) {
        //This API is only to recieve the payment request and publish the event, the actual processing of the payment will be done by another service that will consume the event from Kafka.
        //The validation of the payment will be done by the consumer service, this API will only validate the request body and publish the event to Kafka.
        logger.info("Resource rest: " + paymentDto.getCustomerId()+ paymentDto.getAmount()+ paymentDto.getOrderId());
        

        // Convert PaymentDto to PaymentEvent witch one the event that will be published to Kafka, this is done to decouple the API from the domain model, so we can change the domain model without affecting the API and vice versa.
        PaymentEvent paymentEvent = new PaymentEvent(paymentDto);
        paymentEvent.setOrderId(paymentDto.getOrderId());
        paymentEvent.setAmount(paymentDto.getAmount());
        paymentEvent.setCustomerId(paymentDto.getCustomerId());

        logger.info("Resource rest: " + paymentEvent.getCustomerId()+ paymentEvent.getAmount()+ paymentEvent.getOrderId());
        
        // When we use hexagonal architecture we want to decouple the API from the domain model, so we use a DTO to transfer the data from the API to the domain model, and then we use a use case to handle the business logic and publish the event to Kafka.
        createPaymentEventUseCase.createPaymentEvent(paymentEvent);

        return ResponseEntity.ok("Payment created successfully");
        
    }

}
