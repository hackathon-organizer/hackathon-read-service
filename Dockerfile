FROM maven:3.8.4-openjdk-17-slim as builder

RUN mkdir -p /build

WORKDIR /build

COPY pom.xml /build

RUN mvn -B dependency:resolve dependency:resolve-plugins

COPY src /build/src

RUN mvn clean compile package



FROM openjdk:17-slim as runtime

ENV APP_HOME /app

RUN mkdir $APP_HOME

WORKDIR $APP_HOME

COPY --from=builder /build/target/*.jar hackathon-read-service.jar

ENTRYPOINT ["java","-jar","hackathon-read-service.jar"]