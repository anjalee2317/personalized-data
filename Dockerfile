FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/personalized-data.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]