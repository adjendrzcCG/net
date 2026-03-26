# Scenario Instructions: .NET Modernization

## Scenario
Modernize SimpleWebApp from legacy ASP.NET Core hosting model to the minimal hosting model (.NET 6+), while targeting net8.0.

## Source Project
- **Project**: `/home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj`
- **Current Target Framework**: net8.0
- **Target Framework**: net8.0 (already correct; modernize patterns)
- **Branch**: `copilot/update-dotnet-to-version-8-again`

## Flow Mode
Automatic

## Key Decisions Log
- **2026-03-26**: Project is already at net8.0 but uses legacy Startup.cs hosting model; primary modernization is migration to minimal hosting API.
- **2026-03-26**: Migrate Program.cs + Startup.cs into a single Program.cs with top-level statements and WebApplication.CreateBuilder pattern.

## Strategy
**Selected**: All-At-Once  
**Rationale**: Single project on net8.0 with straightforward pattern modernization.

### Execution Constraints
- Single atomic upgrade — all changes applied together
- Validate full project build after all changes
- Commit once after all changes validated

## Preferences
**Commit Strategy**: Single Commit at End (All-at-Once default)

## User Preferences

### Technical Preferences
- Target framework: net8.0
- Use minimal hosting model (top-level statements, WebApplication.CreateBuilder)

### Execution Style
- Automatic mode: proceed without pausing
