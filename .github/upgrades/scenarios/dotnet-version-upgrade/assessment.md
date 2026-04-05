# Upgrade Assessment: SimpleWebApp net8.0 → net9.0

## Summary

| Property | Value |
|----------|-------|
| Solution | SimpleWebApp (no .sln file) |
| Project | SimpleWebApp/SimpleWebApp.csproj |
| Current Framework | net8.0 |
| Target Framework | net9.0 |
| Project Type | ASP.NET Core MVC Web Application |
| NuGet Packages | None (framework-only) |
| Security Vulnerabilities | None |

## Projects

### SimpleWebApp

- **Current TFM**: net8.0
- **Target TFM**: net9.0
- **Pattern**: Legacy `Startup.cs` + old-style `Program.cs` (CreateHostBuilder pattern) — .NET 3.1 era style
- **Nullable**: Not enabled
- **Implicit Usings**: Not enabled
- **C# Language Features**: Older style (explicit namespaces, no records, no pattern matching)

## Issues Identified

### 1. Legacy Hosting Model (Medium Risk)
`Program.cs` uses `CreateHostBuilder`/`Startup.cs` pattern from .NET 3.1. Should migrate to minimal hosting model (top-level statements, `WebApplication.CreateBuilder`).

### 2. Nullable Reference Types Not Enabled
`ErrorViewModel.RequestId` is declared as `string` (non-nullable) but is assigned potentially-null values. Enabling nullable reference types requires fixing this.

### 3. No Implicit Usings
Project does not use `<ImplicitUsings>enable</ImplicitUsings>` — many `using` directives can be removed.

### 4. Old-Style Namespaces
All files use block-style namespaces (`namespace Foo { }`) instead of file-scoped namespaces (`namespace Foo;`).

### 5. DTO Can Be a Record
`ErrorViewModel` is a simple data class that can benefit from `record` type.

## Packages

No third-party NuGet packages. Only framework-provided packages.

## Risk Assessment

| Risk | Level | Notes |
|------|-------|-------|
| TFM upgrade net8 → net9 | Low | Minor version bump, minimal breaking changes |
| Startup.cs → minimal hosting | Medium | Requires rewriting Program.cs and removing Startup.cs |
| Nullable reference types | Low | Only one property needs fixing |
| C# modernization | Low | Non-breaking improvements |

## Recommendation

Proceed with full upgrade including:
1. Target framework upgrade to net9.0
2. Enable `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`
3. Migrate to minimal hosting model
4. Apply C# 12 modernizations (file-scoped namespaces, records, pattern matching)
