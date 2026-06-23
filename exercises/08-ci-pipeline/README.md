# Exercise 08 - CI Pipeline

## Story

The lab wants every pull request to prove that the project still builds and tests pass.

## Goal

Add a GitHub Actions workflow for the Maven build.

## Starting Point

- `pom.xml`
- `.github/workflows/`
- Existing Maven commands

## Suggested Agent Prompt

```text
Add a GitHub Actions CI workflow for this Maven multi-module repo. It should run on pull requests and pushes to main, set up Java 21, cache Maven dependencies, and run the test/build command.
```

## Steps

1. Ask the agent to inspect the Maven structure.
2. Let it add the workflow.
3. Review the workflow for version and command correctness.
4. Run the same Maven command locally.

## Acceptance Criteria

- A workflow exists under `.github/workflows/`.
- It uses Java 21.
- It runs the Maven verification command.
- The local command passes.

## Reflection

- Did the agent choose a minimal workflow?
- What would you add for a production project?
