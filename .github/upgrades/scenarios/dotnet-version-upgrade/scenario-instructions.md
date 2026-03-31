# .NET Modernization — SimpleWebApp

## Strategy
Direct modernization — single project, apply all changes in topological order

## Preferences
- **Flow Mode**: Automatic
- **Commit Strategy**: After Each Task
- **Pace**: Standard
- **Target Framework**: net8.0

## Decisions
- Merge Startup.cs into Program.cs using WebApplication.CreateBuilder minimal hosting model
- Enable Nullable and ImplicitUsings in csproj
- Apply file-scoped namespaces to all C# files
- Fix RequestId to nullable string

## Custom Instructions
<!-- Task-specific overrides: "For {taskId}: {instruction}" -->
