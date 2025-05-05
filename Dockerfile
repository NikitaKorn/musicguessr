FROM eclipse-temurin:17-jre-jammy

# Настройка безопасности
# RUN useradd -m appuser && \
#    mkdir -p /app/logs && \
#    chown -R appuser:appuser /app
#USER appuser
WORKDIR /app

# Копируем собранный JAR
COPY /target/*.jar ./app.jar
COPY /target/classes ./src

# Порт для WebFlux и WebSocket
EXPOSE 8080

# Параметры запуска
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=prod", \
    "-jar", "app.jar"]