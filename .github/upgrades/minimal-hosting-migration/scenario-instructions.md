# Scenario: Minimal Hosting Model Migration

## Goal
Migrate the ASP.NET Core `SimpleWebApp` (targeting .NET 8.0) from the legacy
`Startup.cs` + `CreateHostBuilder` pattern to the modern **minimal hosting model**
introduced in .NET 6 and recommended in .NET 8.

## Scope
- Project: `/home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj`
- Target: .NET 8.0 (already set — no framework version change needed)

## User Preferences

### Execution Style
- Flow Mode: **Automatic** — run end-to-end without pausing for approval

### Technical Preferences
- Use top-level statements in `Program.cs`
- Remove `Startup.cs` entirely (do not keep as empty stub)
- Apply file-scoped namespaces across all C# source files
- Enable `<Nullable>enable</Nullable>` in the csproj
- Use `WebApplication.CreateBuilder` / `app.MapControllerRoute` pattern

## Key Decisions Log
- 2024: Migration target is minimal hosting model (not a .NET version change)
- All `Startup.cs` logic (ConfigureServices + Configure) merged into `Program.cs`
