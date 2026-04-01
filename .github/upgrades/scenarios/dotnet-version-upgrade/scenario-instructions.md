# Scenario Instructions

## Scenario
**Name**: .NET 8 Code Modernization  
**Project**: SimpleWebApp  
**Working directory**: /home/runner/work/net/net  
**Project file**: /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj  

## Source Control
**Branch**: copilot/update-dotnet-to-version-8-075dd8b6-b785-4213-a71c-43ea8aee48e0  
**Commit strategy**: After Each Task  

## Upgrade Options
- **Strategy**: All-At-Once
- **Target TFM**: net8.0 (already set — modernizing code patterns)
- **Flow mode**: Automatic

## Strategy
**Selected**: All-At-Once  
**Rationale**: Single project, already on net8.0, clear bounded scope.  

### Execution Constraints
- All changes applied in a single pass — no tier ordering needed
- Build validation after each task to catch regressions early
- Commit after each task
- Delete Startup.cs once its content is fully migrated to Program.cs

## User Preferences
### Flow Mode
Automatic — proceed without pausing for approval at stage boundaries.
