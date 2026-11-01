DBP-V2.0 — Digital Banking Platform

A microservice-based banking management platform built with Java and Spring Boot, designed to explore scalable backend architectures, distributed systems, secure authentication, and modern software engineering practices.

Project Status: Active Development — Educational and Portfolio Project

Tech Stack

Backend

Java 25, Spring Boot, Spring Data JPA, Hibernate

Architecture

Microservices, REST APIs, Event-Driven Architecture

Communication

Spring WebClient, Apache Kafka

Security

Spring Security, Keycloak, OAuth 2.0, JWT

Database

PostgreSQL

Caching

Redis

Service Discovery

HashiCorp Consul

Infrastructure

Docker, Docker Compose

Testing

JUnit 5, Mockito, Testcontainers

Documentation

OpenAPI, Swagger UI

Some technologies are integrated incrementally as development progresses.

Architecture

DBP-V2.0 follows a microservice-oriented architecture, separating banking operations into independently maintainable services.

Microservice

Responsibility

Port

User Service

User management and user-related operations

8081

Customer Service

Customer information and customer-related operations

8082

BackOffice Service

Internal banking administration and service integration

8083

Each service maintains its own PostgreSQL database.

Infrastructure components:

Consul: Service registration and discovery

Kafka: Asynchronous event-driven communication

Redis: Caching and performance optimization

Keycloak: Centralized identity and access management

Docker Compose: Local infrastructure orchestration

Project Structure

DBP-V2.0/
├── User-Service/
├── Customer-Service/
├── BackOffice-Service/
├── .github/
├── compose.yml
├── PROJECT_CONTEXT.md
└── README.md

Getting Started

Prerequisites

Java 25

Docker and Docker Compose

Git

Maven (or the included Maven Wrapper)

Clone the Repository

git clone https://github.com/MatteoGrado/DBP-V2.0.git
cd DBP-V2.0

Configure Environment Variables

Create a .env file in the project root and configure the database credentials, Keycloak settings, Kafka configuration, and required service URLs referenced by compose.yml.

Do not commit credentials or secrets to version control.

Start the Application

docker compose config
docker compose up --build -d

The application services are configured to use the following local addresses:

Service

URL

User Service

http://localhost:8081

Customer Service

http://localhost:8082

BackOffice Service

http://localhost:8083

Consul UI

http://localhost:8500

Successful startup depends on valid environment configuration.

API Documentation

REST endpoints are documented using OpenAPI and Swagger.

API documentation can be accessed through the Swagger UI of each configured service.

The available endpoints and authentication requirements depend on the current implementation.

Testing

The project uses automated testing to validate business logic and service integration.

JUnit 5: Unit and integration testing

Mockito: Mocking dependencies

Testcontainers: Integration testing with containerized infrastructure

Example:

cd User-Service
./mvnw test

Security

The architecture incorporates Spring Security and Keycloak for centralized authentication and authorization.

Security concepts include OAuth 2.0, JWT-based authentication, and role-based access control.

This project is intended for development and educational purposes, not production banking operations.

Roadmap

Planned improvements include:

Expand banking and transaction management functionality

Improve asynchronous event processing

Extend integration test coverage

Introduce controlled database migrations

Improve monitoring and observability

Strengthen production-oriented security configuration

Expand deployment automation

Author

Developed by Matteo Grado.

Focus: Java Backend Development, Spring Boot, Microservices, and Distributed Systems.