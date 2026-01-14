# Итоговый проект: Разработка и развертывание ИИ-приложения в Kubernetes

## Описание проекта

Данный проект представляет собой контейнерное Java-приложение с интегрированной ИИ-моделью для анализа тональности текста, развернутое в локальном Kubernetes-кластере с использованием Minikube. Проект включает полный цикл: от разработки приложения до настройки мониторинга и балансировки нагрузки.

## Основные компоненты

### 1. Приложение для анализа тональности

- Язык: Java (Spring Boot)
- Функционал: REST API для анализа тональности текста
- ИИ-модель: Интегрированная модель для классификации тональности (положительная/отрицательная/нейтральная)
- Эндпоинты:
``POST /api/analyze`` - анализ тональности текста
``GET /api/health`` - проверка работоспособности
``GET /api/metrics`` - метрики приложения

### 2. Инфраструктура Kubernetes

- Локальный кластер: Minikube
- Контейнеризация: Docker
- Оркестрация: Kubernetes манифесты
- Мониторинг: Prometheus + Grafana
- Балансировка нагрузки: Kubernetes Service (LoadBalancer/NodePort)

## Требования

### Системные требования

- Операционная система: Windows 10/11, macOS 10.14+, Linux (Ubuntu 18.04+)
- Память: минимум 4 GB RAM
- Процессор: 2+ ядра
- Свободное место на диске: 20 GB

### Установленное ПО

- Minikube (версия 1.30.0+)
- Docker (версия 20.10.0+)
- kubectl (версия 1.28.0+)
- Java JDK (версия 11+)
- Maven (версия 3.6+)


## Архитектура проекта
```
sentiment-analysis-app/
├── src/                          # Исходный код Java-приложения
│   ├── main/java/com/sentiment/  # Основные классы приложения
│   └── main/resources/           # Конфигурационные файлы
├── docker/
│   └── Dockerfile               # Docker-образ приложения
├── kubernetes/
│   ├── deployment.yaml          # Deployment манифест
│   ├── service.yaml             # Service манифест
│   ├── configmap.yaml           # Конфигурации
│   └── prometheus/              # Мониторинг
│       ├── prometheus.yaml      # Настройка Prometheus
│       └── grafana/             # Дашборды Grafana
├── docs/
│   └── report.md               # Аналитический отчет
└── scripts/
    └── deploy.sh               # Скрипты развертывания
```
## Инструкция по развертыванию

### Шаг 1: Установка Minikube
```
# Для Linux
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# Для macOS
brew install minikube

# Для Windows (используйте PowerShell как администратор)
choco install minikube
```
### Шаг 2: Запуск Minikube кластера
#### Запуск кластера с дополнительными ресурсами
```
minikube start --memory=4096 --cpus=2 --disk-size=20g
```
#### Включение дополнений
```
minikube addons enable metrics-server
minikube addons enable dashboard
```
### Шаг 3: Проверка установки
#### Проверка статуса Minikube
```
minikube status
```
#### Проверка версии kubectl
```
kubectl version --client
```
#### Доступ к Kubernetes Dashboard
```
minikube dashboard
```
### Шаг 4: Сборка приложения
#### Сборка Java-приложения
```
mvn clean package
```
#### Сборка Docker-образа
```eval $(minikube docker-env)
docker build -t sentiment-analysis:latest -f docker/Dockerfile .
```
### Шаг 5: Развертывание в Kubernetes
#### Применение манифестов
```kubectl apply -f kubernetes/configmap.yaml
kubectl apply -f kubernetes/deployment.yaml
kubectl apply -f kubernetes/service.yaml
```
#### Проверка развертывания
```
kubectl get all
kubectl get pods
kubectl get services
```
### Шаг 6: Настройка мониторинга
#### Установка Prometheus
```
kubectl apply -f kubernetes/prometheus/prometheus.yaml
```
#### Установка Grafana
```
kubectl apply -f kubernetes/prometheus/grafana/
```
#### Доступ к приложению
```
minikube service sentiment-analysis-service
```
## Использование API

### Пример запроса анализа тональности
```
curl -X POST http://<SERVICE_IP>:<PORT>/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"text": "Это отличный продукт, я очень доволен покупкой!"}'
  ```
### Пример ответа
```
{
  "text": "Это отличный продукт, я очень доволен покупкой!",
  "sentiment": "POSITIVE",
  "confidence": 0.92,
  "timestamp": "2024-01-15T10:30:00Z"
}
```
## Мониторинг и метрики

### Доступные метрики

#### Метрики приложения:

- ```sentiment_analysis_requests_total```
- ```sentiment_analysis_processing_time_seconds```
- ```sentiment_distribution```

#### Метрики системы:

- Использование CPU и памяти
- Количество реплик
- Доступность эндпоинтов
