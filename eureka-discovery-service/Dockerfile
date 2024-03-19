FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /eureka-discovery-service

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /eureka-discovery-service

ARG JAR_FILE=/eureka-discovery-service/target/*.jar
COPY --from=build ${JAR_FILE} eureka-discovery-service.jar

CMD exec java -jar eureka-discovery-service.jar