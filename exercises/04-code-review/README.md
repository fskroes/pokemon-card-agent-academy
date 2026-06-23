# Exercise 04 - Code Review

## Story

A teammate proposes wishlist support for card suggestions. Before merging, the lab wants an agent-assisted code review.

## Goal

Practice using an agent as a reviewer rather than an implementer.

## Starting Point

- Current working tree changes from your own small wishlist experiment, or a small change you ask the agent to draft without committing.
- `advisor-api/`
- `openapi/advisor-api.yaml`

## Suggested Agent Prompt

```text
Review the current changes as if this were a pull request. Focus on bugs, regressions, missing tests, API contract issues, and maintainability. Put findings first with file and line references.
```

## Steps

1. Create or inspect a small change related to wishlist suggestions.
2. Ask the agent for a code review.
3. Validate whether the findings are real.
4. Fix only the findings you agree with.
5. Run checks again.

## Acceptance Criteria

- The review focuses on concrete risks.
- Findings include file and line references.
- You distinguish real issues from subjective suggestions.
- Any accepted fixes are implemented and checked.

## Reflection

- Was the agent too strict, too lenient, or useful?
- Which findings would you have missed manually?
