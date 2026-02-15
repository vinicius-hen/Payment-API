This project uses SpringDoc OpenAPI to automatically generate API documentation.
After starting the application, access Swagger UI at:
http://localhost:8080/swagger-ui.html

📌 Creating the PaymentCreated Topic

After starting Kafka with Docker Compose, you need to create the PaymentCreated topic manually.

Run the following command:

docker exec -it kafka kafka-topics \
  --create \
  --topic PaymentCreated \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 3

Explanation

--create → Creates a new topic

--topic → Defines the topic name

--bootstrap-server → Kafka broker address

--replication-factor → Number of replicas (use 1 for local development)

--partitions → Number of partitions (used for parallelism and scalability)

✅ Verify the Topic

To confirm that the topic was successfully created:

docker exec -it kafka kafka-topics \
  --list \
  --bootstrap-server localhost:9092


You should see:

PaymentCreated

a good payment below: 
{
  "orderId": "ORD123456",
  "amount": 150.75,
  "customerId": "CUST987654",
  "paymentMethod": "CREDIT_CARD",
  "cardNumber": "4111111111111111",
  "cardExpiry": "12/28",
  "cardCvv": "123",
  "status": "PENDING",
  "description": "Pagamento de teste",
  "transactionId": "TXN20260214001",
  "billingAddress": "Rua Exemplo, 123, São Paulo, SP",
  "shippingAddress": "Rua Exemplo, 123, São Paulo, SP",
  "customerEmail": "cliente@example.com",
  "customerPhone": "+5511999999999",
  "authorizationCode": "AUTH20260214"
}
