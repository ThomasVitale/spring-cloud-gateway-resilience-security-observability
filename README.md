# Spring Cloud Gateway - Resilience, Security, and Observability

Do you want to use a microservices architecture? Are you looking for a solution to manage access to single services
from clients? How can you ensure resilience and security for your entire system? Spring Cloud Gateway is a project
based on Reactor, Spring WebFlux, and Spring Boot which provides an effective way to route traffic to your APIs and
address cross-cutting concerns.

In this session, I'll show you how to configure an API gateway to route traffic to your microservices architecture and
implement solutions to improve the resilience of your system with patterns like circuit breakers, retries, fallbacks,
and rate limiters using Spring Cloud Circuit Breaker and Resilience4J. Since the gateway is the entry point of your
system, it’s also an excellent candidate to implement security concerns like user authentication. I'll show you how
to do that with Spring Security, OAuth2, and OpenID Connect, relying on Spring Redis Reactive to manage sessions.
Finally, I'll show you how to improve the observability of your system using Spring Boot Actuator, Micrometer,
and OpenTelemetry.

## Stack

* Java 24
* Spring Boot 3.4
* Podman/Docker

## Usage

First, run Keycloak from the project root folder, using either Podman or Docker.

```shell
podman compose up -d
```

Then, you can reach of the Spring Boot applications using Gradle:

```shell
./gradlew bootRun
```

Alternatively, you can use the [Arconia CLI](https://arconia.io/docs/arconia-cli/latest/index.html) to start each application in development mode:

```shell
arconia dev
```

The Edge Service application is exposed on port 9000 while Book Service on port 9001. The applications require authentication through
OAuth2/OpenID Connect. You can log in as Isabelle (isabelle/password) or Bjorn (bjorn/password).

## Observability Stack

Both Spring Boot applications are observable, as any cloud native application should. Using [Arconia OpenTelemetry](https://arconia.io/docs/arconia/latest/opentelemetry/), both applications are configured to export logs, metrics, and traces automatically to an OpenTelemetry backend.

[Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) provide zero-code dev services for a superior developer experience. When you run the Spring Boot applications, a Grafana observability platform based on OpenTelemetry is automatically run via Testcontainers and the applications configured to export telemetry to it, giving you the possibility to visualize and explore your application’s telemetry data during development and testing.

The application logs will show you the URL where you can access the Grafana observability platform.

```log
...o.t.grafana.LgtmStackContainer: Access to the Grafana dashboard: http://localhost:#####
```

By default, logs, metrics, and traces are exported via OTLP using the HTTP/Protobuf format.

In Grafana, you can query logs and traces from the "Explore" page, selecting the "Loki" and "Tempo" data sources, respectively.
You can also explore metrics in "Drilldown > Metrics".
