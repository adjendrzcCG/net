# Upgrade Options — SimpleWebApp

Assessment: 1 project (SimpleWebApp), currently net8.0, upgrading to net9.0; SDK-style, small codebase.

## Strategy

### Upgrade Strategy
Single project with no dependencies — All-at-Once is optimal; no tier ordering needed.

| Value | Description |
|-------|-------------|
| **All-at-Once** (selected) | Upgrade the single project in one atomic pass: TFM bump, package updates, modernization changes all together. |
| Top-Down | Incremental: apps first, multi-target libraries. Overhead not justified for a single project. |

## Modernization

### Nullable Reference Types
User explicitly requested enabling nullable reference types; codebase is small (≤ 5,000 LOC) and has known null issues (ErrorViewModel.RequestId).

| Value | Description |
|-------|-------------|
| **Enable Nullable Reference Types** (selected) | Adds `<Nullable>enable</Nullable>` and fixes all resulting warnings in the codebase. |
| Leave Disabled | Keeps existing null handling; enable separately after migration. |
