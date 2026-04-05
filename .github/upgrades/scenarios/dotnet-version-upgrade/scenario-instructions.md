# Scenario Instructions

## Scenario
- **Scenario**: dotnet-version-upgrade
- **Project**: SimpleWebApp
- **Source Framework**: net8.0
- **Target Framework**: net9.0
- **Flow Mode**: Automatic

## Upgrade Strategy
Single-project upgrade with full modernization:
1. Upgrade target framework to net9.0
2. Enable nullable reference types and implicit usings
3. Convert legacy Startup.cs pattern to minimal hosting model
4. Fix nullable annotations
5. Update stale links

## User Preferences
### Technical Preferences
- Target framework: net9.0

### Execution Style
- Flow Mode: Automatic (run end-to-end without pausing)

## Key Decisions Log
- 2026-03-26: Target net9.0 (latest stable .NET release, SDKs available on machine)
- 2026-03-26: Apply full modernization including minimal hosting model conversion
