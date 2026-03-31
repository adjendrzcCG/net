# Assessment: SimpleWebApp .NET Modernization

**Date**: 2026-03-31  
**Target**: net9.0  
**Strategy**: All-at-Once (single project, low risk)

---

## Project Analysis

| Property | Value |
|---|---|
| Project | SimpleWebApp.csproj |
| Current Framework | net8.0 |
| Target Framework | net9.0 |
| Project Type | ASP.NET Core MVC Web Application |
| Explicit NuGet Packages | None (framework-provided only) |
| Lines of Code | ~100 (C#), ~200 (Views/Razor) |

---

## Code Patterns Identified

### Legacy Hosting Model (High Priority)
- **Program.cs** uses the old `CreateHostBuilder` pattern with a separate `Startup.cs` class
- This is the pre-.NET 6 hosting model; the modern minimal hosting model was introduced in .NET 6
- Migration: Consolidate into a single `Program.cs` using top-level statements and `WebApplication.CreateBuilder`
- **Startup.cs** will be deleted; all configuration moves into `Program.cs`

### Target Framework
- Currently `net8.0` (LTS, supported until Nov 2026)
- Upgrading to `net9.0` (STS, released Nov 2024, supported until May 2026) — current active release
- No breaking API changes expected for this simple MVC app between net8.0 → net9.0

### HomeController
- Uses `Microsoft.Extensions.Logging.ILogger<T>` — no changes needed
- No deprecated APIs detected

---

## Package Analysis

No explicit `<PackageReference>` entries in the project file. All dependencies come from the `Microsoft.NET.Sdk.Web` implicit framework references, which will be updated automatically by the TFM bump.

---

## Risk Assessment

| Risk | Severity | Notes |
|---|---|---|
| TFM bump net8.0 → net9.0 | Low | Simple MVC app, no complex APIs |
| Minimal hosting model migration | Low | Well-understood refactoring, no behavior change |
| Startup.cs removal | Low | ConfigureServices + Configure logic is minimal |

**Overall Risk: LOW** — No known breaking changes. No third-party packages. Simple MVC scaffold.

---

## Summary

Two changes required:
1. **TFM bump**: `net8.0` → `net9.0` in `SimpleWebApp.csproj`
2. **Minimal hosting model**: Rewrite `Program.cs` with top-level statements; delete `Startup.cs`
