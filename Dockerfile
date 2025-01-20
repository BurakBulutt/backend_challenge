FROM maven:3.8.2-openjdk-17-slim as BUILD
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=BUILD /app/src/target/backend_challange-0.0.1-SNAPSHOT.jar /app/application.jar
ENTRYPOINT ["java","-jar","application.jar"]