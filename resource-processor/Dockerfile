FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /resource-processor

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /resource-processor

ARG JAR_FILE=/resource-processor/target/*.jar
COPY --from=build ${JAR_FILE} resource-processor.jar

CMD exec java -jar resource-processor.jar