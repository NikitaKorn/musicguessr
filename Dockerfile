# Build
FROM eclipse-temurin:17-jdk-jammy as build

WORKDIR /app
RUN apt-get update && apt-get install -y maven

COPY . .
RUN mvn package

# Run
FROM eclipse-temurin:17-jre-alpine
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MaxMetaspaceSize=128m -XX:+UseSerialGC"
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
COPY --from=build /app/target/classes ./src
EXPOSE 8080
CMD ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]