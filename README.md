docker run --env-file .env -d -p 8080:8080 -v ./logs:/app/logs --name musicguessr-app musicguessr:latest

docker build -t musicguessr:latest .

docker-compose up