# Exercise 00 - Getting Started

## Story

Professor Oak's Card Lab has a small recommendation platform for collectors. Before changing anything, you need to understand how to run it and how a rating becomes a suggestion.

## Goal

Run the baseline services and submit at least one card rating.

## Starting Point

- `README.md`
- `collector-api/`
- `advisor-api/`
- `collections/intellij/http-requests.http`
- `openapi/`

## Suggested Agent Prompt

```text
Explore this repo and explain how I can run the Pokemon card collector platform locally. Focus on the services, Kafka/MySQL dependencies, and the request flow from collector-api to advisor-api.
```

## Steps

1. Ask your agent to summarize the architecture.
2. Start Kafka and MySQL with Docker Compose.
3. Start `advisor-api`.
4. Start `collector-api`.
5. Submit a rating and query suggestions.

## Acceptance Criteria

- You can explain what each service does.
- You can start both services locally.
- You can submit a rating through `collector-api`.
- You can call `advisor-api` for tag-based suggestions.

## Reflection

- What context did your agent need before it could help effectively?
- Which files were most useful for understanding the system?
