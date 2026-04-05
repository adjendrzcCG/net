# Scenario Instructions

## Scenario
**Goal**: Upgrade SimpleWebApp from net8.0 to net9.0  
**Target Framework**: net9.0  
**Project**: /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj

## Strategy
**Selected**: All-at-Once  
**Rationale**: Single project, no NuGet dependencies, straightforward TFM bump.

### Execution Constraints
- Single atomic upgrade — update project file, restore, build, fix all errors in one pass
- Validate: solution builds with 0 errors and 0 warnings
- Commit strategy: Single commit at end

## Preferences
**Flow Mode**: Automatic  
**Commit Strategy**: Single Commit at End
