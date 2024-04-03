FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/payment-service

COPY target/payment-service-1.0-SNAPSHOT.jar payment-service-1.0-SNAPSHOT.jar

RUN chmod +x payment-service-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "payment-service-1.0-SNAPSHOT.jar"]