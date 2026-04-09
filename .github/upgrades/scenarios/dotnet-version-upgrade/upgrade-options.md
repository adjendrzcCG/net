# Upgrade Options — SimpleWebApp

Assessment: 1 project (net8.0 → net9.0), SDK-style, no external packages, no vulnerabilities.

## Strategy

### Upgrade Strategy
Single project, modern .NET, straightforward TFM bump with code modernization — ideal for All-at-Once.

| Value | Description |
|-------|-------------|
| **All-at-Once** (selected) | Upgrade all projects simultaneously in a single atomic operation |
| Bottom-Up | Upgrade leaf dependencies first, working up the dependency graph |
| Top-Down | Upgrade applications first, then libraries |

## Modernization

### Nullable Reference Types
Target is net9.0, C# project, nullable reference types not enabled — enabling improves code correctness and IDE null-safety analysis.

| Value | Description |
|-------|-------------|
| **Enable** (selected) | Add `<Nullable>enable</Nullable>` to project; fix resulting warnings |
| Skip | Leave nullable reference types disabled |
