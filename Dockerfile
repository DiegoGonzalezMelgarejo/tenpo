FROM maven:3.9.9-sapmachine-21 AS builder
ENV TZ="America/Bogota"
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY --from=builder /app/target/diego-0.0.1-SNAPSHOT.jar .

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "diego-0.0.1-SNAPSHOT.jar"]