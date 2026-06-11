# 🚀 Запуск нагрузочного тестирования

## 📋 Требования

- Docker и Docker Compose
- Gradle
- Gatling

## 🔧 Инструкция по запуску

### Запустите необходимые сервисы с помощью Docker Compose:

```bash
docker compose up -d
```

### Запуститье Gatling:

```bash
cd task
./gradlew gatlingRun
```

### [Наблюдайте метрики в Grafana](http://localhost:3000)  
(admin / admin)
