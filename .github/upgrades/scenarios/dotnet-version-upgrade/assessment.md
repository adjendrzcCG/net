# Upgrade Assessment: SimpleWebApp → .NET 8 Modernization

**Date**: 2025-07-14  
**Target Framework**: net8.0 (already set in .csproj)  
**Scope**: SimpleWebApp — single ASP.NET Core MVC project

---

## Executive Summary

SimpleWebApp already targets `net8.0` at the project file level, but still uses
the legacy hosting patterns from ASP.NET Core 3.x/5.x. Three modernization gaps
were identified:

| Gap | Impact | Effort |
|-----|--------|--------|
| Legacy `CreateHostBuilder` + `Startup.cs` pattern | Architecture | Low |
| Missing `<Nullable>enable</Nullable>` | Safety / correctness | Low |
| Missing `<ImplicitUsings>enable</ImplicitUsings>` | Readability | Low |

No package vulnerabilities or incompatible packages detected. No test projects found.

---

## Project Analysis

### SimpleWebApp.csproj

| Property | Value |
|----------|-------|
| Target Framework | net8.0 |
| Project Format | SDK-style (`Microsoft.NET.Sdk.Web`) |
| Package References | None (framework-provided only) |
| Nullable | Not enabled |
| Implicit Usings | Not enabled |

### Code Files

| File | Issue |
|------|-------|
| `Program.cs` | Uses old `CreateHostBuilder` pattern with class/namespace boilerplate |
| `Startup.cs` | Separate `ConfigureServices`/`Configure` class — obsolete in .NET 6+ |
| `Controllers/HomeController.cs` | Explicit `using` statements that implicit usings will cover |
| `Models/ErrorViewModel.cs` | `RequestId` property is `string` (non-nullable) without `?` annotation |

---

## Risks

- **Low risk**: Minimal hosting model is a pure refactor — same middleware and services,
  different syntax. No behavioral change.
- **Low risk**: Enabling nullable is additive; warnings may surface on existing code
  that needs `?` annotations (`ErrorViewModel.RequestId`).
- **None**: No external packages to update, no database migrations, no test projects.

---

## Modernization Plan

1. **Migrate to minimal hosting model** — Replace `Program.cs`/`Startup.cs` with
   a single top-level-statements `Program.cs`
2. **Enable modern project settings** — Add `<Nullable>enable</Nullable>` and
   `<ImplicitUsings>enable</ImplicitUsings>` to the `.csproj`
3. **Fix nullable annotations** — Update `ErrorViewModel.RequestId` to `string?`
   and strip now-redundant explicit `using` directives from controllers
