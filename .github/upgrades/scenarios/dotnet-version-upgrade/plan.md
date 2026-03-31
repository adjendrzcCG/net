# dotnet-version-upgrade Plan

## Overview

**Target**: SimpleWebApp — upgrade from net8.0 to net9.0, apply minimal hosting model  
**Scope**: Single project, ~5 source files, no external NuGet dependencies beyond SDK

### Selected Strategy
**All-At-Once** — All projects upgraded simultaneously in a single operation.  
**Rationale**: 1 project, currently on net8.0, straightforward TFM bump + hosting model consolidation; no breaking changes expected.

---

## Tasks

### 01-upgrade-simplewebapp: Upgrade SimpleWebApp to net9.0 with minimal hosting model

Upgrade the standalone `SimpleWebApp` project to target `net9.0` and modernize the hosting model by consolidating the old `IHostBuilder`/`Startup` pattern into a single `Program.cs` using `WebApplication.CreateBuilder`.

Specifically:
- Update `<TargetFramework>` in `SimpleWebApp.csproj` from `net8.0` to `net9.0`
- Rewrite `Program.cs` using the minimal hosting model (`WebApplication.CreateBuilder`, `app.UseXxx`, `app.MapControllerRoute`, `app.Run`)
- Transfer all service registrations and middleware from `Startup.cs` into `Program.cs`
- Delete `Startup.cs` — it is no longer needed
- Build the project to confirm zero errors

The routing and middleware pipeline must remain equivalent: `AddControllersWithViews`, developer exception page (dev) / exception handler + HSTS (prod), HTTPS redirection, static files, routing, authorization, and the default MVC route `{controller=Home}/{action=Index}/{id?}`.

**Done when**: Project builds successfully targeting `net9.0`, `Startup.cs` is deleted, `Program.cs` uses `WebApplication.CreateBuilder`, and all original middleware/routing is preserved.

---
