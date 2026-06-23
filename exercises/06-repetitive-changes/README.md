# Exercise 06 - Repetitive Changes

## Story

Collectors want to search for sealed products and graded cards. That requires adding new tags consistently across code, OpenAPI, seed data, and examples.

## Goal

Use an agent to make a repetitive cross-file domain change safely.

## Starting Point

- `collector-api/src/main/java/com/cardlab/collector/domain/CardTag.java`
- `advisor-api/src/main/java/com/cardlab/advisor/domain/CardTag.java`
- `openapi/`
- `collections/`

## Suggested Agent Prompt

```text
Add two new CardTag values, SEALED and GRADED, everywhere they belong. Update OpenAPI and request examples if useful. Keep the change mechanical and run checks afterward.
```

## Steps

1. Ask the agent to list every place that needs the new tags.
2. Let the agent make the change.
3. Inspect the diff for missed contracts or examples.
4. Run the build.

## Acceptance Criteria

- Both services contain the new enum values.
- OpenAPI documents the new values.
- At least one request example uses a new value.
- The build passes.

## Reflection

- Did the agent find all duplicate definitions?
- Was this a good task for automation?
