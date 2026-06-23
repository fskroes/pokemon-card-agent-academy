# Exercise 09 - Plan-First Feature

## Story

Collectors want suggestions that can prioritize wishlist-worthy cards. This is larger than a one-line change, so the lab wants a plan before implementation.

## Goal

Practice plan-first feature work with an agent.

## Starting Point

- `advisor-api/`
- `collector-api/`
- `openapi/`
- Tests from earlier exercises

## Suggested Agent Prompt

```text
Plan a wishlist-aware suggestion feature for this repo. First inspect the code, then propose API, persistence, ranking, tests, and migration implications. Do not edit until I approve the plan.
```

## Steps

1. Ask the agent for a plan only.
2. Review scope and tradeoffs.
3. Approve a small first version.
4. Let the agent implement in steps.
5. Run tests and review the final behavior.

## Acceptance Criteria

- The agent produces a concrete plan before editing.
- The feature scope is intentionally small.
- API and tests are updated together.
- Checks pass after implementation.

## Reflection

- Did planning reduce rework?
- Where did you need to constrain the agent?
