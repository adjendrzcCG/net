# Scenario Instructions: .NET Version Upgrade

## Scenario
- **From**: net8.0
- **To**: net10.0
- **Project**: /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj
- **Branch**: copilot/upgrade-app-to-newest-version

## Strategy
**Selected**: All-At-Once  
**Rationale**: Single project, no external packages, low complexity.

### Execution Constraints
- Single atomic upgrade — all changes applied together
- Validate full build (0 errors, 0 warnings) after upgrade
- Single commit at end

## Preferences
- **Flow Mode**: Automatic
- **Commit Strategy**: Single Commit at End

## User Preferences

### Technical Preferences
- Target framework: net10.0
- Migrate to minimal hosting model (Program.cs + Startup.cs → single Program.cs)

### Execution Style
- Automatic mode — no pauses for user confirmation

## Key Decisions Log
- 2026-03-26: Target framework confirmed as net10.0
- 2026-03-26: Minimal hosting model migration included (modernize Program.cs, remove Startup.cs)
