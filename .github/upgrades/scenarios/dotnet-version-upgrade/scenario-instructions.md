# Scenario Instructions

## Scenario
Modernize SimpleWebApp from old ASP.NET Core patterns to modern .NET 8 patterns.
The project already targets net8.0 but uses older C# and hosting patterns.

## Flow Mode
Automatic — run end-to-end without pausing for user input.

## Target Framework
net8.0 (already set — no framework change needed)

## Modernization Scope
- Enable `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` in .csproj
- Migrate from old Startup.cs + Program.cs pattern to minimal hosting (top-level statements)
- Apply file-scoped namespaces to all C# files
- Fix nullable reference type warnings in models and controllers
- Remove redundant using directives after implicit usings are enabled

## User Preferences
### Execution Style
- Automatic mode: run all tasks without pausing
