# Upgrade Options — SimpleWebApp

Assessment: 1 project (SimpleWebApp), net8.0 → net9.0, SDK-style, no NuGet packages, low complexity.

## Strategy

### Upgrade Strategy
Single-project solution with no .NET Framework, no incompatible packages, and straightforward TFM bump — All-at-Once fits perfectly.

| Value | Description |
|-------|-------------|
| **All-at-Once** (selected) | Upgrade all projects simultaneously in a single atomic pass. Fastest approach for simple solutions. |
| Top-Down | Upgrade entry-point apps first, multi-target libraries. Adds overhead without benefit here. |

## Modernization

### Nullable Reference Types
Target is net9.0, no `<Nullable>` currently set, small codebase (≤ 5,000 LOC, 1 project), low complexity.

| Value | Description |
|-------|-------------|
| **Enable Nullable Reference Types** (selected) | Adds `<Nullable>enable</Nullable>` to project file. Compile-time null safety for a modern net9.0 app. |
| Leave Disabled | Keep existing null handling. Enable separately later. |
