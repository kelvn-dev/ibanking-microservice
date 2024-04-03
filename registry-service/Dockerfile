FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/registry-service

COPY target/registry-service-1.0-SNAPSHOT.jar registry-service-1.0-SNAPSHOT.jar

RUN chmod +x registry-service-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "registry-service-1.0-SNAPSHOT.jar"]