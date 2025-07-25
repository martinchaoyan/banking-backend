FROM openjdk:21-jdk-slim

ARG JAR_FILE=build/libs/*.jar

WORKDIR /app
COPY build/libs/Banking-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
