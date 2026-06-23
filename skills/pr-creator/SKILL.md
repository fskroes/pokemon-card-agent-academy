---
name: pr-creator
description: Create GitHub pull requests safely from the current Git branch using `gh`. Use when the user asks Codex to open or prepare a PR, create a pull request for the current branch, assign the authenticated user, select CODEOWNERS reviewers, or generate a branch-specific PR title/body without merge noise.
---

# PR Creator

## Overview

Create a pull request only after proving the branch is ready. Stop on failed prerequisites instead of trying to repair repository state.

## Workflow

1. Confirm the repository and branch.
   - Run `git rev-parse --show-toplevel`.
   - Run `git branch --show-current`.
   - Stop if the current branch is empty or is the default branch.

2. Determine the default branch automatically.
   - Prefer `gh repo view --json defaultBranchRef --jq '.defaultBranchRef.name'`.
   - If `gh` cannot answer, inspect the remote default with `git symbolic-ref refs/remotes/origin/HEAD` or `git remote show origin`.
   - Do not hardcode `main` or `master`.

3. Fail fast if the worktree is dirty.
   - Run `git status --porcelain`.
   - If there is any output, stop and tell the user to commit or stash the changes before creating a PR.
   - Do not stage, commit, stash, or discard changes unless the user explicitly asks in a separate request.

4. Fail fast if commits are unpushed.
   - Check that the branch has an upstream with `git rev-parse --abbrev-ref --symbolic-full-name @{u}`.
   - If there is no upstream, stop and tell the user to push the branch first.
   - Compare local and upstream state with `git rev-list --left-right --count @{u}...HEAD`.
   - If the local branch is ahead, stop and tell the user to push before creating the PR.

5. Build a branch-specific summary.
   - Compute the base with `git merge-base HEAD origin/<default-branch>`.
   - Inspect changed files with `git diff --name-only <base>...HEAD`.
   - Inspect commits with `git log --no-merges --reverse --format='%s' <base>..HEAD`.
   - Exclude merge commits from the default branch; do not summarize default-branch merge noise as branch work.
   - Summarize the purpose, meaningful file areas, behavior changes, and validation evidence.

6. Determine reviewers from CODEOWNERS when possible.
   - Search for `CODEOWNERS`, `.github/CODEOWNERS`, or `docs/CODEOWNERS`.
   - Match changed files to the most specific applicable CODEOWNERS patterns.
   - Use GitHub handles or teams as reviewers when `gh pr create` accepts them.
   - If mapping is ambiguous or cannot be automated confidently, mention the likely owners in the PR body instead of guessing reviewers.

7. Create the PR.
   - Use a conventional-commit-style title, such as `feat: add card ranking endpoint` or `docs: add PR creator skill`.
   - Assign the authenticated GitHub user: `gh api user --jq .login`.
   - Create the PR with `gh pr create --base <default-branch> --head <current-branch> --title "<title>" --body "<body>" --assignee <login>`.
   - Add reviewers with `--reviewer` only when reviewer handles were confidently derived.

8. Open the PR.
   - Prefer `gh pr view --web` after creation.
   - If browser opening is not available in the execution environment, return the PR URL and state that the browser could not be opened.

## PR Body

Use a concise body with these sections:

- Summary: what the branch changes and why.
- Changes: short bullets based on branch-specific files and commits.
- Validation: commands run and results, or state when validation was not run.
- Reviewers: CODEOWNERS-derived reviewers or a note that none were detected.

## Stop Conditions

Stop without creating a PR when:

- the worktree has uncommitted changes;
- the branch has no upstream;
- the branch has local commits that are not pushed;
- the current branch is the default branch;
- `gh` is unavailable or the user is not authenticated;
- required repository information cannot be determined reliably.
