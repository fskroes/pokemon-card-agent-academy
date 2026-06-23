# Exercise 05 - Refactoring

## Story

The suggestion logic is starting to carry too many responsibilities. The lab wants cleaner code without changing behavior.

## Goal

Refactor suggestion scoring into clearer units while preserving behavior.

## Starting Point

- `advisor-api/src/main/java/com/cardlab/advisor/service/SuggestionService.java`
- Tests added in Exercise 03

## Suggested Agent Prompt

```text
Refactor SuggestionService to make filtering, scoring, and response mapping easier to understand. Preserve external behavior and rely on the existing tests. Show me the plan before editing.
```

## Steps

1. Ask for a refactoring plan.
2. Check that the plan preserves behavior.
3. Let the agent implement small changes.
4. Run tests.
5. Review the diff for unnecessary churn.

## Acceptance Criteria

- Public API behavior remains unchanged.
- Tests still pass.
- The ranking logic is easier to read.
- The diff avoids unrelated rewrites.

## Reflection

- Did the agent keep the refactor focused?
- Which tests protected you from accidental behavior changes?
