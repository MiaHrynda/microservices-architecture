FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /song-service

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine

WORKDIR /song-service

ARG JAR_FILE=/song-service/target/*.jar
COPY --from=build ${JAR_FILE} song-service.jar

CMD exec java -jar song-service.jar