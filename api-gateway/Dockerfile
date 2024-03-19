FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /api-gateway

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /api-gateway

ARG JAR_FILE=/api-gateway/target/*.jar
COPY --from=build ${JAR_FILE} api-gateway.jar

CMD exec java -jar api-gateway.jar