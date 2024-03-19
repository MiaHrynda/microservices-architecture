FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /resource-service

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /resource-service

ARG JAR_FILE=/resource-service/target/*.jar
COPY --from=build ${JAR_FILE} resource-service.jar

CMD exec java -jar resource-service.jar