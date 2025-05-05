# Build
FROM eclipse-temurin:17-jdk-jammy as build

RUN apt-get update && apt-get install -y maven

COPY . .
RUN mvn package

# Run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /target/*.jar ./app.jar
#COPY --from=build /target/classes ./src
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]