FROM maven:3.9.16-eclipse-temurin-25 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM eclipse-temurin:25-jre

WORKDIR /ecom_app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]