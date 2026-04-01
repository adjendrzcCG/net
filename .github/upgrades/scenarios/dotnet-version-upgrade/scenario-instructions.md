# Scenario Instructions: SimpleWebApp .NET 8 Modernization

## Scenario
- **ID**: dotnet-version-upgrade
- **Goal**: Modernize SimpleWebApp to .NET 8 minimal hosting model with nullable reference types and implicit usings
- **Target Framework**: net8.0

## Source Control
- **Branch**: copilot/update-dotnet-to-version-8-6f4945ec-b444-4afb-9e72-51f977db782b
- **Commit Strategy**: Single commit at end

## Strategy
**Selected**: All-at-Once  
**Rationale**: Single project with no external dependencies — atomic upgrade is safest.

### Execution Constraints
- Single atomic upgrade — all projects updated together
- Validate full solution build after upgrade
- No test projects present

## Upgrade Options
- **Strategy**: All-at-Once
- **Nullable**: Enable
- **Implicit Usings**: Enable
- **Minimal Hosting**: Enable

## Flow Mode
Automatic

## User Preferences
### Technical Preferences
- Target: net8.0 minimal hosting model
- Enable: Nullable reference types
- Enable: Implicit usings
- Delete: Startup.cs after migration
