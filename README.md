# Jewellery Shop Management System

[![CI](https://github.com/Gulshan614/jewellery-shop-management-system/actions/workflows/ci.yml/badge.svg)](https://github.com/Gulshan614/jewellery-shop-management-system/actions/workflows/ci.yml)
![Java 17](https://img.shields.io/badge/Java-17-orange)
![Spring Boot 3.3.5](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)
![License: MIT](https://img.shields.io/badge/License-MIT-blue)

A backend system that automates jewellery shop operations: products, gold rates, inventory, customers, orders, billing and payments. It uses **event-driven processing with Apache Kafka** so that order, stock and notification workflows stay decoupled and reliable.

> **Status:** under active development. Step 1 (project skeleton) is complete. See the [Roadmap](#roadmap).

## Objectives

- Automate jewellery sales, inventory, customer management, billing and order processing.
- Use Kafka for asynchronous communication between services.
- Improve stock accuracy, billing efficiency and data consistency.

## Modules

| # | Module | Description |
|---|--------|-------------|
| 1 | Product Management | Add, update, delete and search jewellery products |
| 2 | Gold Rate Management | Maintain gold and silver rates by purity and weight |
| 3 | Inventory Management | Track stock and update availability after sales |
| 4 | Customer Management | Store customer details and purchase history |
| 5 | Order Management | Create, confirm, cancel and track orders |
| 6 | Billing Management | Gold price, making charges, discounts and taxes |
| 7 | Payment Management | Payment method, amount and status |
| 8 | Notification Management | Order and low-stock notifications via Kafka events |
| 9 | Sales Reports | Daily sales reports and inventory summaries |
| 10 | Authentication & Authorization | Secure access for administrators and sales staff (JWT) |

## Tech Stack

| Area | Technology |
|------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.3.5 (Web, Validation, Security) |
| Persistence | Spring Data JPA, Hibernate, MySQL 8 |
| Messaging | Apache Kafka (Spring Kafka) |
| Security | Spring Security, JWT |
| Build | Maven |
| Testing | JUnit, Mockito, Spring Boot Test, Postman |
| Deployment | Docker, Docker Compose |
| CI | GitHub Actions |

## How Kafka is used

- Publish an order event when a customer places an order.
- Consume order events to trigger inventory updates.
- Generate a notification when stock runs low.
- Process sales events for reporting and analytics.
- Reliable processing with retries and duplicate-event protection (idempotent consumers).

## Project Structure

```
.
├── .github/workflows/ci.yml   # Build and test on every push / PR
├── docker-compose.yml         # MySQL 8 and Kafka for local development
├── pom.xml
└── src
    ├── main
    │   ├── java/com/jewellery/...
    │   │   ├── config         # Security, Kafka configuration
    │   │   ├── controller     # REST controllers
    │   │   ├── dto            # Request / response objects
    │   │   ├── entity         # JPA entities
    │   │   ├── event          # Kafka event classes, producers, consumers
    │   │   ├── exception      # Custom exceptions and global handler
    │   │   ├── repository     # Spring Data JPA repositories
    │   │   └── service        # Service interfaces
    │   │       └── impl       # Service implementations
    │   └── resources          # application.properties
    └── test                   # Unit and integration tests
```

## Getting Started

### Prerequisites

- JDK 17
- Maven 3.9+
- Docker and Docker Compose

### Run locally

```bash
# 1. Clone
git clone https://github.com/Gulshan614/jewellery-shop-management-system.git
cd jewellery-shop-management-system

# 2. Start MySQL (and Kafka, needed from Step 5)
docker compose up -d

# 3. Start the application
mvn spring-boot:run
```

Check that it works:

```bash
curl http://localhost:8080/api/health
# {"status":"UP", ...}
```

### Run tests

```bash
mvn test
```

### Configuration

Defaults work with the provided `docker-compose.yml`. Override them with environment variables (see `.env.example`):

| Variable | Purpose | Default (local dev) |
|----------|---------|---------------------|
| `DB_URL` | JDBC URL | `jdbc:mysql://localhost:3306/jewellery_db` |
| `DB_USERNAME` | Database user | `root` |
| `DB_PASSWORD` | Database password | `root` |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address | `localhost:9092` |

> **Development only:** the Compose file uses a weak root password, and `spring.jpa.hibernate.ddl-auto=update` is enabled. Change both before any real deployment.

## API

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/health` | Application health check | Public |

Module endpoints will be documented here as they are built.

> `SecurityConfig` currently permits all requests. It is replaced by JWT security in Step 3.

## Roadmap

- [x] **Step 1:** Project skeleton (Spring Boot, MySQL, health endpoint, security placeholder)
- [ ] **Step 2:** CRUD modules (products, gold rates, inventory, customers)
- [ ] **Step 3:** JWT authentication and role-based authorization
- [ ] **Step 4:** Orders, billing and payments
- [ ] **Step 5:** Kafka events (order, stock, notification)
- [ ] **Step 6:** Idempotent consumers, retries, duplicate-event protection
- [ ] **Step 7:** Tests, Docker packaging, GitHub CI/CD

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for the branch and pull request workflow.

## License

Released under the [MIT License](LICENSE).
