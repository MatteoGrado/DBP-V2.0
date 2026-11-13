# DBP-V2.0 — Digital Banking Platform

**A Microservices-Based Banking Management Platform built with Java and Spring Boot.**

DBP-V2.0 is a backend-focused software engineering project designed to explore microservices architecture, distributed systems, event-driven communication, and secure API development.

The platform simulates core banking operations through independently maintainable services, integrating modern technologies and established backend development practices.

**Project Status:** 🚧 Active Development  
**Project Type:** Educational & Portfolio Project  
**Primary Focus:** Java Backend Development · Spring Boot · Microservices

## 🛠️ Technology Stack

| Category | Technologies |
|---|---|
| Backend | Java 25, Spring Boot, Spring Data JPA, Hibernate |
| Architecture | Microservices, REST APIs, Event-Driven Architecture |
| Communication | Spring WebClient, Apache Kafka |
| Security | Spring Security, Keycloak, OAuth 2.0, JWT |
| Database | PostgreSQL |
| Caching | Redis |
| Service Discovery | HashiCorp Consul |
| Infrastructure | Docker, Docker Compose |
| Testing | JUnit 5, Mockito, Testcontainers |
| API Documentation | OpenAPI, Swagger UI |
| Build Tools | Maven |

> **Note:** This project is under active development. Some technologies and features are being integrated incrementally.

## 🏗️ System Architecture

DBP-V2.0 follows a microservice-oriented architecture, separating business responsibilities into independently maintainable backend services.

The architecture emphasizes:

- **Separation of Concerns:** Each microservice handles a clearly defined business domain.
- **Independent Services:** Services can be developed, tested, and maintained separately.
- **Event-Driven Communication:** Apache Kafka enables asynchronous communication between services.
- **Synchronous Communication:** REST APIs and Spring WebClient support direct service-to-service interactions.
- **Centralized Security:** Keycloak provides identity and access management.
- **Service Discovery:** Consul enables dynamic service registration and discovery.
- **Distributed Data Management:** Each microservice maintains its own PostgreSQL database.

### Microservices Overview

| Microservice | Responsibility | Port |
|---|---|---|
| User Service | User management and user-related operations | 8081 |
| Customer Service | Customer information and customer-related operations | 8082 |
| BackOffice Service | Internal banking administration and service integration | 8083 |

### Infrastructure Components

| Component | Purpose |
|---|---|
| PostgreSQL | Persistent relational data storage |
| Apache Kafka | Asynchronous event-driven communication |
| Redis | Caching and performance optimization |
| Keycloak | Authentication, authorization, and identity management |
| HashiCorp Consul | Service registration and discovery |
| Docker Compose | Local container orchestration |

## 📁 Project Structure

```text
DBP-V2.0/
│
├── User-Service/
│   └── ...
│
├── Customer-Service/
│   └── ...
│
├── BackOffice-Service/
│   └── ...
│
├── .github/
│
├── compose.yml
├── PROJECT_CONTEXT.md
└── README.md
```

Each microservice is organized as an independent Spring Boot application with its own business logic, configuration, and persistence layer.

## 🚀 Getting Started

### Prerequisites

Ensure the following tools are installed:

- Java 25
- Docker and Docker Compose
- Git
- Maven or the included Maven Wrapper

### 1. Clone the Repository

```bash
git clone https://github.com/MatteoGrado/DBP-V2.0.git
cd DBP-V2.0
```

### 2. Configure Environment Variables

Create a `.env` file in the project root.

Configure the required environment variables referenced in `compose.yml`, including:

- PostgreSQL database credentials
- Keycloak configuration
- Kafka configuration
- Service URLs and application-specific settings

> **Security Notice:** Never commit passwords, client secrets, access tokens, or other sensitive credentials to version control.

### 3. Start the Application

Validate the Docker Compose configuration:

```bash
docker compose config
```

Build and start the services:

```bash
docker compose up --build -d
```

Check the running containers:

```bash
docker compose ps
```

View application logs:

```bash
docker compose logs -f
```

Stop the application:

```bash
docker compose down
```

### 4. Local Service Endpoints

| Service | Local URL |
|---|---|
| User Service | http://localhost:8081 |
| Customer Service | http://localhost:8082 |
| BackOffice Service | http://localhost:8083 |
| Consul UI | http://localhost:8500 |

Successful startup requires valid environment configuration and available infrastructure dependencies.

## 📖 API Documentation

REST APIs are documented using OpenAPI and Swagger UI, providing interactive documentation for available endpoints.

Swagger UI allows developers to:

- Explore available REST endpoints
- Inspect request and response models
- Review HTTP methods and status codes
- Understand endpoint authentication requirements
- Execute API requests during development

For services with Springdoc Swagger UI enabled, documentation is typically available at:

```text
http://localhost:<service-port>/swagger-ui/index.html
```

Actual endpoint availability depends on the current service configuration.

## 🧪 Testing Strategy

DBP-V2.0 uses automated testing to validate application behavior, business logic, and infrastructure integration.

| Framework | Purpose |
|---|---|
| JUnit 5 | Unit and integration testing |
| Mockito | Mocking service dependencies |
| Testcontainers | Integration testing with containerized infrastructure |

### Run Tests

Navigate to a microservice directory:

```bash
cd User-Service
```

Execute the test suite:

```bash
./mvnw test
```

### Testing Objectives

- Validate service-layer business logic
- Verify expected application behavior
- Test database persistence and repository operations
- Verify interactions between application components
- Improve reliability and maintainability

Integration test coverage is being expanded as development progresses.

## 🔐 Security Architecture

DBP-V2.0 incorporates Spring Security and Keycloak to explore centralized authentication and authorization in a distributed environment.

### Security Technologies

- **Spring Security:** Application-level security and request authorization
- **Keycloak:** Centralized identity and access management
- **OAuth 2.0:** Authorization framework
- **JWT:** Token-based authentication
- **Role-Based Access Control (RBAC):** Restricting access based on assigned roles

The security architecture is designed to separate identity management from individual business services.

> **Disclaimer:** DBP-V2.0 is an educational project and is not intended for production banking operations or the processing of real financial transactions.

## 🗺️ Development Roadmap

The following improvements are planned as the platform evolves:

- [ ] Expand banking account and transaction management
- [ ] Improve Kafka-based asynchronous event processing
- [ ] Extend integration test coverage with Testcontainers
- [ ] Introduce version-controlled database migrations
- [ ] Implement monitoring and observability
- [ ] Strengthen production-oriented security configuration
- [ ] Expand CI/CD and deployment automation
- [ ] Improve resilience and fault tolerance between services

The roadmap will evolve as new architectural and technical requirements are introduced.

## 🎯 Project Objectives

This project serves as a practical environment for strengthening skills in:

- Java and Spring Boot backend development
- Microservices and distributed system architecture
- RESTful API design and service integration
- Event-driven architecture with Apache Kafka
- Authentication and authorization using OAuth 2.0 and JWT
- Relational database design and persistence
- Containerized application development
- Automated testing and software quality
- Modern DevOps and deployment practices

## 👨‍💻 Author

**Matteo Grado**  
Java Backend Developer | Spring Boot | Microservices

Focused on developing maintainable, scalable backend applications and continuously expanding expertise in distributed systems, cloud infrastructure, and modern software engineering.

**LinkedIn:** https://www.linkedin.com/in/matteo-grado-a09481216
**GitHub:** https://github.com/MatteoGrado  
**Repository:** https://github.com/MatteoGrado/DBP-V2.0

---

**DBP-V2.0 — Digital Banking Platform**  
*Built for learning, experimentation, and continuous improvement.*
