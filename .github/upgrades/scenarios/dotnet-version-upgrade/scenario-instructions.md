# .NET Version Upgrade — SimpleWebApp

## Preferences
- **Flow Mode**: Automatic
- **Target Framework**: net9.0

## Source Control
- **Source Branch**: copilot/update-dotnet-version-one-more-time
- **Working Branch**: copilot/update-dotnet-version-one-more-time (existing branch, no new branch needed)
- **Commit Strategy**: After Each Task

## Upgrade Options
**Source**: .github/upgrades/scenarios/dotnet-version-upgrade/upgrade-options.md

### Strategy
- Upgrade Strategy: All-at-Once

## Strategy
**Selected**: All-at-Once
**Rationale**: 1 project, SDK-style, net8.0 → net9.0, no external packages.

### Execution Constraints
- Single atomic upgrade — all changes in one pass
- Validate full project build (zero errors, zero warnings) after upgrade
- Single commit at end of upgrade task

## Decisions
- Modernize Program.cs to use minimal hosting model (top-level statements, WebApplication.CreateBuilder)
- Remove Startup.cs entirely, merging all configuration into Program.cs
