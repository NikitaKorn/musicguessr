# Build
FROM maven:3.9.6-eclipse-temurin-17 as build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Run
FROM eclipse-temurin:17-jre-alpine

ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MaxMetaspaceSize=128m -XX:+UseSerialGC"

WORKDIR /app

COPY --from=build /app/module/target/*.jar ./app.jar
COPY --from=build /app/module/target/classes ./src

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/info/liveness || exit 1

CMD ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]