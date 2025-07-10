FROM amazoncorretto:21 AS base
LABEL maintainer="fellipems14@gmail.com"

FROM maven:3.9.6-amazoncorretto-21 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=builder /app/target/interview-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]