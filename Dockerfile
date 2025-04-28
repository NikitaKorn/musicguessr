# Этап 1: Сборка приложения
FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app
COPY pom.xml .
# Кэшируем зависимости
RUN mvn dependency:go-offline

COPY src ./src
# Сборка с пропуском тестов и OWASP проверки (для ускорения)
RUN mvn clean package -DskipTests -Ddependency-check.skip=true

# Этап 2: Запуск приложения
FROM eclipse-temurin:17-jre-jammy

# Настройка безопасности
# RUN useradd -m appuser && \
#    mkdir -p /app/logs && \
#    chown -R appuser:appuser /app
#USER appuser
WORKDIR /app

# Копируем собранный JAR
COPY --from=builder /app/target/music-guessr-backend-0.0.1-SNAPSHOT.jar ./app.jar
COPY /target/classes ./src

# Порт для WebFlux и WebSocket
EXPOSE 8080

# Параметры запуска
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=prod", \
    "-jar", "app.jar"]

HEALTHCHECK --interval=30s --timeout=5s \
    CMD curl -f http://localhost:8080/actuator/health || exit 1