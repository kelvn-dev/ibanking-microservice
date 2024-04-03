FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/api-gateway

COPY target/api-gateway-1.0-SNAPSHOT.jar api-gateway-1.0-SNAPSHOT.jar

RUN chmod +x api-gateway-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "api-gateway-1.0-SNAPSHOT.jar"]