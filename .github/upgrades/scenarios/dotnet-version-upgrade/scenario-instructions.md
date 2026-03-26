# Scenario Instructions: dotnet-version-upgrade

## Overview

Upgrade SimpleWebApp from net8.0 to net9.0 and apply C# modernization improvements.

## Parameters

- **Solution/Project**: /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj
- **Target Framework**: net9.0
- **Source Branch**: copilot/update-dotnet-to-version-8-please-work
- **Working Branch**: copilot/update-dotnet-to-version-8-please-work (already on working branch)

## Preferences

### Flow Mode
Mode: **Automatic** — Run end-to-end, only pause when blocked.

### Technical Preferences
- Upgrade target framework to net9.0
- Apply minimal hosting model migration (Startup.cs → Program.cs top-level statements)
- Enable nullable reference types
- Apply C# modernization: file-scoped namespaces, records for DTOs, implicit usings

## Strategy
**Selected**: All-at-Once
**Rationale**: 1 project on net8.0, simple upgrade with no breaking changes.

### Execution Constraints
- Single atomic upgrade — all files updated together
- Validate full solution build after upgrade with 0 errors and 0 warnings
- Commit Strategy: Single Commit at End

## Key Decisions Log

- 2025: Chose net9.0 as upgrade target (stable STS release, net10.0 is preview)
- 2025: Flow mode: Automatic (user requested full automation)
- 2025: All-at-Once strategy selected (single project, straightforward upgrade)
