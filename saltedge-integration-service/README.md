# saltedge-integration-service

Fill out this file with some information about your Integration Service.

Requires a running Eureka registry, by default on port 8761.
Requires a running ActiveMQ broker, by default on port 61616.

Service configuration is under src/main/resources/application.yml.

To run the service in development mode, use:
- mvn spring-boot:run

To run the service from the built binaries, use:
- java -jar target/saltedge-integration-service-1.0.0-SNAPSHOT-boot.war
