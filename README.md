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