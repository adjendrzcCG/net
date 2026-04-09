# .NET Version Upgrade

## Preferences
- **Flow Mode**: Automatic
- **Target Framework**: net9.0 (.NET 9.0 STS)
- **Upgrade Strategy**: All projects in solution

## Source Control
- **Source Branch**: copilot/update-dotnet-to-version-8-37363da4-e5db-4143-b491-64230cb01a17
- **Working Branch**: copilot/update-dotnet-to-version-8-37363da4-e5db-4143-b491-64230cb01a17
- **Commit Strategy**: After Each Task

## Upgrade Options
**Source**: .github/upgrades/scenarios/dotnet-version-upgrade/upgrade-options.md

### Strategy
- Upgrade Strategy: All-at-Once

### Modernization
- Nullable Reference Types: Enable

## Strategy
**Selected**: All-at-Once
**Rationale**: 1 project, all on modern .NET (net8.0→net9.0), no external packages, low complexity.

### Execution Constraints
- Single atomic upgrade — all projects updated together
- Validate full solution build after upgrade (0 errors, 0 warnings)
- Apply code modernization alongside TFM upgrade (minimal hosting model, nullable types, implicit usings)
