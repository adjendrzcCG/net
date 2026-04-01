# .NET 8 Modernization — SimpleWebApp

## Preferences
- **Flow Mode**: Automatic
- **Target Framework**: net8.0 (already on target; focus is on code pattern modernization)

## Source Control
- **Source Branch**: copilot/update-dotnet-to-version-8-f60dc346-19ef-4b69-9908-2c76159e4291
- **Working Branch**: copilot/update-dotnet-to-version-8-f60dc346-19ef-4b69-9908-2c76159e4291 (same branch — no TFM change)
- **Commit Strategy**: After Each Task

## Strategy
**Selected**: All-At-Once
**Rationale**: Single project, no external package dependencies, all changes low-risk and well-scoped.

### Execution Constraints
- Single atomic pass — all changes applied together across all source files
- Build must succeed with 0 errors and 0 warnings before task is complete
- Startup.cs must be deleted after migration, not just emptied
- File-scoped namespace changes are pure formatting — validate build after each rename

## Modernization Goals
1. Migrate from Startup.cs + CreateHostBuilder to minimal hosting model (WebApplication.CreateBuilder)
2. Enable nullable reference types (<Nullable>enable</Nullable>)
3. Enable implicit usings (<ImplicitUsings>enable</ImplicitUsings>)
4. Apply file-scoped namespace declarations
5. Remove the separate Startup.cs file
6. Apply other .NET 8 idiomatic best practices
