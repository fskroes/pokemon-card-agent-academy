# Pokemon Card Agent Academy

This repository is a guided training project for learning coding-agent workflows with Java, Spring Boot, Kafka, MySQL, OpenAPI, and Maven.

The domain is a small Pokemon card collector platform. The exercises are intentionally stored in folders instead of separate Git branches, so you can keep one agent session focused on the same checkout while you work through the academy step by step.

## How The Academy Works

Work through the folders in `exercises/` in order. Each exercise builds on the same baseline application and uses the same structure:

- `Story`: why this task exists in the card collector domain.
- `Goal`: what the exercise should accomplish.
- `Starting point`: where to ask your agent to look first.
- `Suggested agent prompt`: a practical first prompt.
- `Steps`: a guided workflow.
- `Acceptance criteria`: how to know you are done.
- `Reflection`: what to learn from the exercise.

There is no exercise branch switching in this repo.

## Exercises

- [00 - Getting Started](exercises/00-getting-started/README.md)
- [01 - Debugging](exercises/01-debugging/README.md)
- [02 - Context Discovery](exercises/02-context-discovery/README.md)
- [03 - Tests](exercises/03-tests/README.md)
- [04 - Code Review](exercises/04-code-review/README.md)
- [05 - Refactoring](exercises/05-refactoring/README.md)
- [06 - Repetitive Changes](exercises/06-repetitive-changes/README.md)
- [07 - Skills And Playbooks](exercises/07-skills-playbooks/README.md)
- [08 - CI Pipeline](exercises/08-ci-pipeline/README.md)
- [09 - Plan-First Feature](exercises/09-plan-first-feature/README.md)
- [99 - Create Your Own Training Repo](exercises/99-create-your-own-training-repo/README.md)

## Card Collector Platform

The training app contains two cooperating services:

- `collector-api` accepts card ratings from users. Each rating contains a card identifier, a 1-5 score, up to 3 card tags, and free-text collector notes.
- `advisor-api` consumes rating events, stores them in MySQL, and returns card suggestions based on requested tags.

Functional use case:

- A collector rates cards they own or want.
- Another collector asks for suggestions using one to three tags.
- The platform returns cards that match at least one requested tag.
- Results are ranked by:
  1. average rating
  2. number of overlapping tags
  3. card name

The baseline models a realistic "ingest events -> materialize read model -> query ranked suggestions" workflow, but keeps the business domain approachable for agent practice.

## Tech Stack

- Java 21 + Spring Boot 3
- Maven multi-module setup
- Apache Kafka for async event flow
- MySQL + Spring Data JPA for persistence
- OpenAPI contracts in `openapi/`
- Docker Compose for local infrastructure

## Run Locally

1. Start Kafka and MySQL:

   ```bash
   docker compose up -d
   ```

2. Start the advisor service:

   ```bash
   mvn -pl advisor-api spring-boot:run
   ```

3. Start the collector service:

   ```bash
   mvn -pl collector-api spring-boot:run
   ```

4. Send requests using one of the request collections:

   - IntelliJ HTTP client: `collections/intellij/http-requests.http`
   - Postman collection: `collections/postman/pokemon-card-agent-academy.postman_collection.json`

## Baseline API

Submit a card rating:

```http
POST http://localhost:8081/api/v1/ratings
Content-Type: application/json

{
  "cardId": "CHARIZARD_BASE_SET",
  "userId": "user-01",
  "score": 5,
  "tags": ["FIRE", "HOLO", "VINTAGE"],
  "experience": "Centerpiece card for a vintage fire collection."
}
```

Get suggestions:

```http
GET http://localhost:8082/api/v1/suggestions?tags=FIRE&tags=HOLO&tags=VINTAGE
Accept: application/json
```
