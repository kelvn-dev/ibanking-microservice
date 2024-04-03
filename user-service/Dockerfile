FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/user-service

COPY target/user-service-1.0-SNAPSHOT.jar user-service-1.0-SNAPSHOT.jar

RUN chmod +x user-service-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "user-service-1.0-SNAPSHOT.jar"]