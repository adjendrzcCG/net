# Scenario Instructions: dotnet-version-upgrade

## Scenario Parameters
- **Project**: /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj
- **Current Framework**: net8.0
- **Target Framework**: net9.0
- **Source Branch**: copilot/dotnet-update-to-current-version-again
- **Working Branch**: copilot/dotnet-update-to-current-version-again (already on this branch)

## Preferences

### Flow Mode
Mode: **Automatic** — Run end-to-end, only pause when blocked.

### Additional Modernization
- Consolidate Program.cs + Startup.cs into minimal hosting model (remove Startup.cs)
- No separate .sln file — standalone project

## Strategy
**Selected**: All-at-Once  
**Rationale**: Single project, net8.0 → net9.0, low-risk TFM bump + hosting model consolidation

## Execution Constraints
- Single atomic upgrade — update csproj, rewrite Program.cs, delete Startup.cs, validate build
- Validate: solution builds with 0 errors and 0 warnings after changes

## Preferences
- **Flow Mode**: Automatic
- **Commit Strategy**: Single Commit at End

## Key Decisions Log
- 2025-01: Target net9.0 (STS), user explicitly specified
- 2025-01: Apply minimal hosting model consolidation alongside version upgrade
- 2025-01: All-at-Once strategy selected (single project, straightforward)
