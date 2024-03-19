FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /storage-service

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /storage-service

ARG JAR_FILE=/storage-service/target/*.jar
COPY --from=build ${JAR_FILE} storage-service.jar

CMD exec java -jar storage-service.jar