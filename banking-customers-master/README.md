# Customers Microservice
Customers Microservice using Event Sourcing & CQRS with Spring Boot & Axon Framework

## Download Axon Server
https://download.axoniq.io/quickstart/AxonQuickStart.zip

## Running Axon Server
Unzip AxonQuickStart.zip and execute the following command:
```
$ cd documents/tools/AxonServer-4.5.12
$ java -jar axonserver.jar
```

## Axon Server URL
http://localhost:8024

## Set Based Consistency Validation
https://axoniq.io/blog-overview/set-based-validation

## Environment Variables
### MYSQL_CUSTOMERS_AXON_JDBC_URL
```
jdbc:mysql://{YOUR-HOST}:3306/{YOUR_DATABASE}?verifyServerCertificate=false&useSSL=false&useTimezone=true&serverTimezone=UTC
```

## Swagger
http://localhost:8081/v3/api-docs
http://localhost:8081/api-docs/swagger-ui/index.html

## Maven
$ mvn clean package