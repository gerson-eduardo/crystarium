FROM eclipse-temurin:21

ARG SPRING_PROFILE=local
ENV DEPLOY "java -jar -Dspring.profiles.active=${SPRING_PROFILE} app.jar"

RUN echo $PROFILE

WORKDIR /app

COPY target/*.jar ./app.jar

CMD $DEPLOY
