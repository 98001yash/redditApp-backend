⭐ RedditApp – Scalable Microservices Backend

A production-grade Reddit-like backend system built with Spring Boot, Spring Cloud, Kafka, and Docker, featuring full observability, CI/CD automation, and cloud-ready deployment.

<p align="center"> <img src="https://img.shields.io/badge/Java-17-orange?style=flat-square" /> <img src="https://img.shields.io/badge/SpringBoot-3.x-brightgreen?style=flat-square" /> <img src="https://img.shields.io/badge/Docker-Ready-blue?style=flat-square" /> <img src="https://img.shields.io/badge/CI/CD-GitHub_Actions-black?style=flat-square" /> <img src="https://img.shields.io/badge/Monitoring-Prometheus%20%7C%20Grafana-red?style=flat-square" /> </p>
📌 Overview

RedditApp is a fully distributed backend platform inspired by Reddit, designed to demonstrate real-world microservices engineering.

It implements:

Independent, scalable services

Centralized API Gateway

Secure authentication

Event-driven communication

Full observability stack

Automated DevOps pipelines

This project focuses on production-readiness, not just functionality.

🏗️ System Architecture
 Client
   │
   ▼
API Gateway (JWT + Routing)
   │
   ▼
Service Discovery (Eureka)
   │
   ▼
Microservices Layer
   │
   ├── MySQL
   ├── Kafka
   ├── Prometheus
   ├── Grafana
   └── Zipkin


All services are containerized and orchestrated using Docker Compose.

🧩 Microservices
Service	Responsibility
api-gateway	Routing, Authentication, Filters
auth-service	Login, JWT, Token Validation
user-service	User Management
post-service	Post Creation & Retrieval
comment-service	Comments Handling
feed-service	Personalized Feed
vote-service	Upvotes / Downvotes
karma-service	Reputation System
notification-service	Async Notifications

Each service is independently deployable.

⚙️ Technology Stack
Backend

Java 17

Spring Boot

Spring Cloud (Gateway, Eureka)

Resilience4j

JWT Security

Data & Messaging

MySQL

Apache Kafka

Observability

Spring Boot Actuator

Prometheus

Grafana

Zipkin

DevOps

Docker

Docker Compose

GitHub Actions

Docker Hub

✨ Core Features

✔️ Distributed Microservices Architecture
✔️ Centralized API Gateway
✔️ JWT-Based Authentication
✔️ Service Discovery
✔️ Event-Driven Processing
✔️ Metrics & Dashboards
✔️ Distributed Tracing
✔️ CI/CD Automation
✔️ Fault Tolerance
✔️ Cloud-Ready Design

🚀 Getting Started
Prerequisites

Java 17+

Maven 3.9+

Docker

Docker Compose

🔹 Run Using Docker
git clone https://github.com/your-username/reddit-app.git
cd reddit-app
docker compose up -d

🔹 Access Services
Tool	URL
API Gateway	http://localhost:8080

Eureka	http://localhost:8761

Grafana	http://localhost:3000

Prometheus	http://localhost:9090

Zipkin	http://localhost:9411
🔄 CI/CD Pipeline

Each microservice is integrated with GitHub Actions.

Pipeline stages:

1️⃣ Build & Test (Maven)
2️⃣ Docker Image Creation
3️⃣ Push to Docker Hub
4️⃣ Versioned Deployment

Ensures consistent and reliable delivery.

📊 Monitoring & Tracing
Monitoring

Prometheus collects metrics

Grafana visualizes dashboards

Actuator exposes health endpoints

Tracing

Zipkin tracks inter-service calls

Enables root cause analysis

Provides full system visibility.

🔐 Security Design

JWT Authentication

Gateway-Level Validation

Secure Environment Variables

Externalized Configs

No Hardcoded Secrets

🛠️ Engineering Challenges Solved

✔️ Multi-module Maven conflicts
✔️ Docker networking issues
✔️ Kafka broker setup
✔️ Service startup ordering
✔️ CI pipeline failures
✔️ Build cache optimization

This project involved real-world debugging.

📈 Performance & Resilience

Circuit Breakers (Resilience4j)

Timeout Handling

Graceful Degradation

Health Monitoring

Designed for high availability.

🧠 What I Learned

Designing distributed systems

DevOps automation

Observability tooling

Fault tolerance patterns

Production debugging

Infrastructure management

🚧 Roadmap

Kubernetes Deployment

Config Server

ELK Logging

OAuth2 Integration

Autoscaling

Canary Releases

👨‍💻 Author

Your Name
Backend Engineer | Java | Microservices | DevOps

🔗 GitHub: https://github.com/your-username

🔗 LinkedIn: https://linkedin.com/in/your-profile

⭐ Support

If you find this project useful, consider giving it a ⭐ star.

It motivates continuous improvement.
