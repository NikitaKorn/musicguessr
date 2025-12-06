#!/bin/bash

IMAGE_NAME="song-guesser-backend:latest"
# Получить директорию где лежит скрипт
PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
# или для всех shell
PROJECT_DIR=$(dirname "$(realpath "$0")")

echo "Директория скрипта: PROJECT_DIR"
echo "Текущая директория: $PROJECT_DIR"

# Перейти в директорию скрипта
cd "$PROJECT_DIR" || exit 1

# Проверяем существует ли образ
if docker image inspect "$IMAGE_NAME" >/dev/null 2>&1; then
    echo "Образ $IMAGE_NAME уже существует, пропускаем сборку"
else
    echo "Образ не найден, собираем..."
    docker build -t "$IMAGE_NAME" .
fi

CONTAINER_NAME="song-guesser-$(date +%s)"
echo "Текущая имя контейнера: $CONTAINER_NAME"

# Функция для очистки
cleanup() {
    echo "Останавливаем контейнер $CONTAINER_NAME..."
    docker stop "$CONTAINER_NAME" 2>/dev/null || true
    docker rm "$CONTAINER_NAME" 2>/dev/null || true
    echo "Контейнер остановлен"
}

# Ловим сигналы завершения
trap cleanup EXIT INT TERM

mvn clean package -DskipTests

docker run \
  --name "$CONTAINER_NAME" \
  -p 8080:8080 \
  -v "$PROJECT_DIR/module/target/test-classes/application-docker.yaml:/app/config/application.yaml" \
  -v "$PROJECT_DIR/module/target/test-classes/logback-docker.xml:/app/config/logback.xml" \
  -v "$PROJECT_DIR/module/target/test-classes/names-test.txt:/app/config/names-test.txt" \
  -v "$PROJECT_DIR/module/target/test-classes/words-test.txt:/app/config/words-test.txt" \
  prj

# Ожидание закрытия окна в случае ошибки
read -n 1 -s -r -p "Нажмите любую клавишу для выхода..."