# Exercise 02 - Context Discovery

## Story

The lab wants to add more features later, but first you need a shared map of the codebase. This exercise is about using an agent to build that map before editing.

## Goal

Have your agent produce a clear technical overview of the application.

## Starting Point

- `collector-api/`
- `advisor-api/`
- `openapi/`
- `docker-compose.yml`

## Suggested Agent Prompt

```text
Read the codebase and create a concise system map. Include modules, API endpoints, Kafka topics, persisted entities, domain enums, and the ranking algorithm. Do not change files.
```

## Steps

1. Ask the agent to inspect the repo.
2. Ask follow-up questions about unclear parts.
3. Ask where a new feature would belong.
4. Ask which parts look risky or under-tested.

## Acceptance Criteria

- You have a useful summary of services, APIs, events, persistence, and ranking.
- You know where to make changes for future exercises.
- No code changes are made for this exercise.

## Reflection

- Which prompts produced the best context?
- What did the agent miss on the first pass?
