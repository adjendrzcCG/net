# .NET Modernization — SimpleWebApp

## Preferences
- **Flow Mode**: Automatic
- **Target Framework**: net8.0 (already set — focus is code modernization)

## Source Control
- **Source Branch**: copilot/update-dotnet-to-v8
- **Working Branch**: copilot/update-dotnet-to-v8
- **Commit Strategy**: After Each Task

## Upgrade Options
**Source**: .github/upgrades/scenarios/dotnet-version-upgrade/upgrade-options.md

### Strategy
- Upgrade Strategy: All-At-Once

### Modernization
- Nullable Reference Types: Enable

## Execution Constraints
- Single atomic upgrade — all projects updated together
- Validate full solution build (0 errors, 0 warnings) after all changes
- Commit strategy: After Each Task

## Modernization Goals
1. Migrate from Startup.cs/Program.cs (old CreateHostBuilder pattern) to minimal hosting model (top-level statements)
2. Enable `<Nullable>enable</Nullable>` in csproj
3. Enable `<ImplicitUsings>enable</ImplicitUsings>` in csproj
4. Apply file-scoped namespaces throughout all C# files
5. Update `ErrorViewModel.RequestId` to `string?` (nullable)
6. Remove Startup.cs after merging into Program.cs
