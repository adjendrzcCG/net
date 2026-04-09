# .NET Version Upgrade

## Preferences
- **Flow Mode**: Automatic
- **Target Framework**: net9.0

## Source Control
- **Source Branch**: copilot/update-dotnet-to-v8-again
- **Working Branch**: copilot/update-dotnet-to-v8-again
- **Commit Strategy**: After Each Task

## Upgrade Options
**Source**: .github/upgrades/scenarios/dotnet-version-upgrade/upgrade-options.md

### Strategy
- Upgrade Strategy: All-at-Once

### Modernization
- Nullable Reference Types: Enable Nullable Reference Types

## Strategy
**Selected**: All-at-Once
**Rationale**: Single project, already on modern .NET (net8.0), straightforward TFM bump.

### Execution Constraints
- Single atomic upgrade — all changes applied together; validate full project build after upgrade
- Fix all nullable warnings before considering the task complete
- Delete Startup.cs after merging into Program.cs

## Modernization Goals
1. Upgrade from net8.0 to net9.0
2. Merge Startup.cs into Program.cs using minimal hosting model (top-level statements / WebApplication.CreateBuilder pattern)
3. Enable nullable reference types (`<Nullable>enable</Nullable>`) and fix nullable warnings
4. Enable implicit usings (`<ImplicitUsings>enable</ImplicitUsings>`) and remove redundant using directives
