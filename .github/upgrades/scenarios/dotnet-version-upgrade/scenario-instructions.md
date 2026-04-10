# Scenario Instructions: .NET Version Upgrade

## Scenario
**From**: .NET 8.0 (net8.0)
**To**: .NET 10.0 LTS (net10.0)
**Solution**: /home/runner/work/net/net/SimpleWebApp.sln
**Project**: /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj

## Strategy
**Selected**: All-at-Once
**Rationale**: 1 project, currently on .NET 8.0, no external NuGet packages — straightforward TFM bump with possible code modernization.

### Execution Constraints
- Single atomic upgrade — all changes applied together; validate full solution build after upgrade
- Update TargetFramework to net10.0 in project file
- Update any implicit framework package references
- Fix any breaking changes found during build
- Run build validation after all changes

## User Preferences

### Flow Mode
**Mode**: Automatic — run end-to-end without pausing for approval

### Commit Strategy
Single Commit at End — one atomic upgrade, one commit

## Key Decisions Log
- 2026-03-26: Target framework selected as net10.0 (LTS) — recommended by get_dotnet_upgrade_options
- 2026-03-26: Strategy selected as All-at-Once — single project, no external NuGet packages, straightforward upgrade
