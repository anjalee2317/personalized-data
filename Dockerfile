FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/personalized-data.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]