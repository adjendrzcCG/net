# Scenario Instructions: SimpleWebApp .NET Modernization

## Target
- **Project**: `/home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj`
- **Source Framework**: `net8.0`
- **Target Framework**: `net9.0`

## Strategy
**Selected**: All-at-Once  
**Rationale**: Single project, low complexity, no breaking dependencies.

### Execution Constraints
- Single atomic upgrade — all changes applied together
- Validate full solution build (0 errors, 0 warnings) after upgrade
- Delete `Startup.cs` after integrating its logic into `Program.cs`
- Commit Strategy: Single Commit at End

## Flow Mode
**Automatic** — run end-to-end without pausing for approval.

## User Preferences

### Technical Preferences
- Upgrade to net9.0 (current stable active release)
- Apply minimal hosting model (top-level statements in Program.cs)
- Remove Startup.cs pattern

### Execution Style
- Automatic mode: proceed without pausing
