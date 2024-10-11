FROM openjdk:21-slim AS build

RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY . .
RUN mvn clean package

FROM eclipse-temurin:21-jre-jammy

COPY --from=build /target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
