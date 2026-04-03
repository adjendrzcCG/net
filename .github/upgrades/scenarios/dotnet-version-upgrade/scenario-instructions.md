# .NET Version Upgrade

## Preferences
- **Flow Mode**: Automatic
- **Target Framework**: net9.0

## Source Control
- **Source Branch**: copilot/kon-update-java-version
- **Working Branch**: copilot/kon-update-java-version
- **Commit Strategy**: Single Commit at End

## Upgrade Options
**Source**: .github/upgrades/scenarios/dotnet-version-upgrade/upgrade-options.md

### Strategy
- Upgrade Strategy: All-at-Once

### Modernization
- Nullable Reference Types: Enable Nullable Reference Types

## Strategy
**Selected**: All-at-Once
**Rationale**: Single project, all on modern .NET (net8.0), no complexity signals.

### Execution Constraints
- Single atomic upgrade — update TFM, packages, and code in one pass
- Validate full project build after upgrade with 0 errors and 0 warnings
- Enable nullable reference types and fix any resulting warnings
