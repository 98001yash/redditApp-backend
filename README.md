# RedditApp – Scalable Microservices Backend

A production-grade Reddit-like backend built using Spring Boot and Spring Cloud, designed with microservices architecture, event-driven communication, and full observability.

This project focuses on production readiness, reliability, and DevOps automation.

---

## Overview

RedditApp is a distributed backend platform inspired by Reddit.  
It demonstrates real-world system design, cloud-native practices, and automated deployment pipelines.

Key goals:

- Scalable microservices architecture
- Secure centralized access
- Asynchronous event processing
- End-to-end observability
- CI/CD-driven delivery

---
## Architecture

Client
↓
API Gateway (JWT, Routing)
↓
Service Discovery (Eureka)
↓
Microservices Layer
↓
MySQL | Kafka | Monitoring Stack



All services are containerized and orchestrated using Docker Compose.

---

## Microservices

| Service              | Responsibility                          |
|----------------------|------------------------------------------|
| api-gateway          | Routing, authentication, filters         |
| auth-service         | Login, JWT, token validation              |
| user-service         | User management                           |
| post-service         | Post creation and retrieval               |
| comment-service      | Comment handling                          |
| feed-service         | Personalized feed generation              |
| vote-service          | Upvotes and downvotes                     |
| karma-service        | Reputation system                         |
| notification-service| Asynchronous notifications                |

Each service is independently deployable.

---

## Technology Stack

### Backend
- Java 17
- Spring Boot
- Spring Cloud (Gateway, Eureka)
- Resilience4j
- JWT Security

### Data & Messaging
- MySQL
- Apache Kafka

### Observability
- Spring Boot Actuator
- Prometheus
- Grafana
- Zipkin

### DevOps
- Docker
- Docker Compose
- GitHub Actions
- Docker Hub

---

## Core Features

- Distributed microservices architecture
- Centralized API Gateway
- JWT-based authentication
- Service discovery
- Event-driven processing
- Metrics and dashboards
- Distributed tracing
- CI/CD automation
- Fault tolerance
- Cloud-ready design

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.9+
- Docker
- Docker Compose

---

### Run with Docker

```bash
git clone https://github.com/your-username/reddit-app.git
cd reddit-app
docker compose up -d
  Service Endpoints
Tool	URL
API Gateway	http://localhost:8080

Eureka	http://localhost:8761

Grafana	http://localhost:3000

Prometheus	http://localhost:9090

Zipkin	http://localhost:9411



CI/CD Pipeline

Each microservice is integrated with GitHub Actions.

Pipeline stages:

Build and test with Maven

Docker image creation

Push to Docker Hub

Versioned deployment

Ensures consistent and reliable delivery.

Monitoring and Tracing
Monitoring

Prometheus collects metrics

Grafana visualizes dashboards

Actuator exposes health endpoints

Tracing

Zipkin tracks inter-service communication

Enables root cause analysis

Provides full system visibility.

Security

JWT authentication

Gateway-level validation

Secure environment variables

Externalized configuration

No hardcoded secrets

Engineering Challenges Solved

Multi-module Maven conflicts

Docker networking issues

Kafka broker configuration

Service startup ordering

CI pipeline failures

Build cache optimization

This project involved extensive real-world debugging.

Performance and Resilience

Circuit breakers (Resilience4j)

Timeout handling

Graceful degradation

Health monitoring

Designed for high availability.

Roadmap

Kubernetes deployment

Centralized config server

ELK logging stack

OAuth2 integration

Autoscaling

Canary releases

Author

Your Name
Backend Engineer – Java, Spring, Microservices

GitHub: https://github.com/your-username

LinkedIn: https://linkedin.com/in/your-profile
