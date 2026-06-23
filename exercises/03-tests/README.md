# Exercise 03 - Tests

## Story

Card suggestions drive the whole platform. The lab wants confidence that ranking stays stable as new features are added.

## Goal

Add focused tests for suggestion ranking and rating ingestion behavior.

## Starting Point

- `advisor-api/src/main/java/com/cardlab/advisor/service/SuggestionService.java`
- `advisor-api/src/main/java/com/cardlab/advisor/service/RatingIngestionService.java`
- `advisor-api/src/main/java/com/cardlab/advisor/persistence/`

## Suggested Agent Prompt

```text
Add focused tests for the advisor-api ranking behavior. The ranking should prioritize average rating, then number of matching tags, then card name. Inspect the current code first, propose test cases, and implement the smallest useful test suite.
```

## Steps

1. Ask the agent to identify the behavior worth testing.
2. Review the proposed test cases.
3. Let the agent add tests.
4. Run the tests.
5. Fix production code only if the tests expose a real bug.

## Acceptance Criteria

- Ranking behavior is covered by automated tests.
- Tests are readable and focused.
- The Maven test phase passes.
- Any behavior changes are explained.

## Reflection

- Did the tests document the intended behavior?
- Did the agent over-test implementation details?
