# Scenario Instructions — .NET Version Upgrade

## Scenario
Upgrade SimpleWebApp from .NET 8 to .NET 9 and apply modern C# patterns.

## Target Framework
**net9.0** (.NET 9 STS)

## Strategy
**Selected**: All-at-Once — Single project upgraded atomically.
**Rationale**: 1 project, currently on net8.0, straightforward upgrade with no external NuGet dependencies beyond the SDK.

### Execution Constraints
- Single atomic upgrade — all project files updated together; validate full solution build after upgrade
- Commit all changes at the end in a single commit

## Preferences

### Flow Mode
**Automatic** — Run end-to-end, only pause when blocked.

### Commit Strategy
Single Commit at End

## User Preferences

### Technical Preferences
- Target framework: net9.0
- Enable nullable reference types
- Enable implicit usings
- Modernize Program.cs to minimal hosting model (remove Startup.cs)
- Apply C# 12 primary constructors where applicable

## Source Control
- Working branch: `copilot/modernize-dotnet-to-8-again`

## Key Decisions Log
- 2025-07-15: Target net9.0 (latest stable), apply minimal hosting model, nullable reference types, implicit usings, primary constructors
