# Build
FROM eclipse-temurin:17-jdk-jammy as build

WORKDIR /app
RUN apt-get update && apt-get install -y maven

COPY . .
RUN mvn package

# Run
FROM eclipse-temurin:17-jre-jammy
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MaxMetaspaceSize=128m"
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
COPY --from=build /app/target/classes ./src
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]