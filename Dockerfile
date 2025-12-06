# Build
FROM maven:3.9.6-eclipse-temurin-17 as build

WORKDIR /app

COPY . .

# Собираем
RUN mvn clean package -DskipTests

# Run
FROM eclipse-temurin:17-jre-alpine
ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:MaxMetaspaceSize=128m -XX:+UseSerialGC"

WORKDIR /app
COPY --from=build /app/module/target/*.jar ./app.jar
COPY --from=build /app/module/target/classes ./src

# ADD entry-point.sh /app/
# ADD maven/*.jar /app/

EXPOSE 8080
CMD ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]

# ENTRYPOINT ["sh", "./entry-point.sh"]