FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21

ARG SPRING_PROFILE=local
ENV DEPLOY "java -jar -Dspring.profiles.active=${SPRING_PROFILE} app.jar"

WORKDIR /app

COPY --from=build /app/target/*.jar ./app.jar

CMD $DEPLOY