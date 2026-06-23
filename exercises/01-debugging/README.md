# Exercise 01 - Debugging

## Story

Collectors noticed that the "last review" shown for a card can be wrong after several quick ratings for the same card. The lab suspects an event-ordering issue.

## Goal

Use your agent to investigate and fix the rating ingestion bug.

## Starting Point

- `collector-api/src/main/java/com/cardlab/collector/service/RatingPublisherService.java`
- `advisor-api/src/main/java/com/cardlab/advisor/service/RatingIngestionService.java`
- `collections/intellij/http-requests.http`

## Suggested Agent Prompt

```text
Investigate why the last review for the same card can become incorrect when ratings arrive quickly or out of order. Read the publisher and ingestion code, explain the likely cause, propose a fix, then implement it with focused tests if useful.
```

## Steps

1. Ask your agent to trace the rating event flow.
2. Reproduce or reason through the bug.
3. Ask for a small fix plan.
4. Implement the fix.
5. Run the relevant checks.

## Acceptance Criteria

- Events for the same card are handled consistently.
- The stored last review is not overwritten by an older event.
- The intentional flaw in the baseline is removed.
- The agent explains both the root cause and the fix.

## Reflection

- Did the agent find the real issue first, or did it need steering?
- What made the bug easier or harder to reproduce?
