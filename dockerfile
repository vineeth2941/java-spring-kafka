FROM openjdk:17-jdk-slim-buster AS BUILD
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY . .
RUN ./mvnw package

FROM openjdk:17-jdk-slim-buster
ENV APP_HOME=/usr/app
COPY --from=BUILD $APP_HOME/target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
