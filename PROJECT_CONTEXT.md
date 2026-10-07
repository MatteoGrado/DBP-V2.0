# DBP-V2.0 – Projektkontext

Diese Datei ist die Kurzreferenz für die weitere Arbeit am Projekt. Sie soll den
aktuellen technischen Stand festhalten, damit nicht jedes Mal das gesamte
Repository analysiert werden muss.

## Struktur

- `compose.yml` startet die lokale Infrastruktur und den `user-service`.
- `.env` enthält die lokalen Zugangsdaten und Service-Variablen.
- `User-Service/` ist eine Spring-Boot-Anwendung.
- Startklasse: `de.grado.userservice.UserServiceApplication`.
- Konfiguration: `User-Service/src/main/resources/application.yaml`.
- Build: Maven über `User-Service/mvnw`.

## Aktueller User-Service

- Spring Boot: `4.0.8`
- Java im Maven-Projekt: `25`
- Dockerfile-Basis: Eclipse Temurin JDK `25`.
- Service-Name in Spring/Consul: `User-Service`
- Externer Port: `8081`
- Datenbank: PostgreSQL
- Datenbanktreiber: `org.postgresql:postgresql`
- Service Discovery: Consul über Spring Cloud Consul Discovery

## Verbindungen im Docker-Netzwerk

Innerhalb von `compose.yml` werden Servicenamen als Hostnamen verwendet:

| Ziel | Host | Port |
|---|---|---:|
| PostgreSQL für User-Service | `user-db` | `5432` |
| Consul für User-Service | `consul` | `8500` |

Vom Host-System aus ist PostgreSQL über `localhost:3306` und Consul über
`localhost:8500` erreichbar. Der User-Service ist über `localhost:8081`
erreichbar.

## Konfiguration

`application.yaml` liest die Werte aus Umgebungsvariablen und verwendet für
Docker passende Fallbacks:

- `DB_HOST`, `DB_PORT`
- `POSTGRES_USER_DB`
- `POSTGRES_USER_USER`
- `POSTGRES_USER_PASSWORD`
- optional `CONSUL_HOST`, `CONSUL_PORT`
- optional `SERVER_PORT`

Die Datenbank-URL lautet im Container sinngemäß:
`jdbc:postgresql://user-db:5432/user-db`.

## Compose-Hinweis

Das offizielle PostgreSQL-Image erwartet die Variablen `POSTGRES_DB`,
`POSTGRES_USER` und `POSTGRES_PASSWORD`. `.env` verwendet bewusst die
projektspezifischen Namen `POSTGRES_USER_DB`, `POSTGRES_USER_USER` und
`POSTGRES_USER_PASSWORD`; `compose.yml` mappt diese deshalb beim
`user-db`-Container auf die von PostgreSQL erwarteten Namen.

Weitere Infrastruktur (Kafka, Elasticsearch, Redis und MailHog) ist bereits in
`compose.yml` definiert. Für Kafka fehlen im aktuellen `.env` jedoch noch die
notwendigen Variablen; das betrifft die aktuelle DB-/Consul-Konfiguration nicht.

## Nützliche Befehle

```bash
# Compose-Konfiguration prüfen
docker compose config

# Services bauen und starten
docker compose up --build

# User-Service lokal bauen
cd User-Service
./mvnw test
```

## Offene Punkte

1. Fehlende Kafka-Variablen in `.env` ergänzen, bevor Kafka über Compose genutzt wird.
2. Vor produktivem Einsatz `spring.jpa.hibernate.ddl-auto: update` durch eine kontrollierte Migration (z. B. Flyway/Liquibase) ersetzen.
