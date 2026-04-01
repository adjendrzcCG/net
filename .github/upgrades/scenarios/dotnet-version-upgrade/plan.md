# SimpleWebApp .NET 8 Modernization Plan

## Overview

**Target**: net8.0 (already targeting; focus is on code and project pattern modernization)
**Scope**: Single MVC project, ~100 LOC, no external NuGet dependencies

### Selected Strategy
**All-At-Once** — All modernization changes applied to the single project in one coordinated pass.
**Rationale**: 1 project, no external package dependencies, all changes are contained and low-risk.

## Tasks

### 01-hosting-migration: Migrate to minimal hosting model

The project currently uses the legacy `Startup.cs` + `CreateHostBuilder` pattern from ASP.NET Core 3.x/5.x. Since .NET 6+, the recommended pattern is the minimal hosting model in `Program.cs` using `WebApplication.CreateBuilder()`. This consolidates service registration and middleware pipeline configuration into a single, top-level-statement-based `Program.cs` and removes the need for a separate `Startup.cs` file entirely.

The existing `Startup.ConfigureServices` registers `AddControllersWithViews()`, and `Startup.Configure` wires up the middleware pipeline (developer exception page, HSTS, HTTPS redirection, static files, routing, authorization, and MVC default route). These must all be migrated into `Program.cs`. After migration, `Startup.cs` is deleted.

**Done when**: `Program.cs` uses `WebApplication.CreateBuilder()` and top-level statements; `Startup.cs` is deleted; project builds with 0 errors; the MVC default route is still configured.

---

### 02-project-modernization: Enable nullable reference types and implicit usings

The `.csproj` file does not set `<Nullable>` or `<ImplicitUsings>`, leaving both features disabled. Adding `<Nullable>enable</Nullable>` enables compile-time null-safety analysis, and `<ImplicitUsings>enable</ImplicitUsings>` removes the need to manually declare many common framework `using` directives (such as `System`, `System.Collections.Generic`, `Microsoft.Extensions.Logging`, etc.).

After enabling these features, the `ErrorViewModel.RequestId` property must be annotated as `string?` (since it is nullable by design — `ShowRequestId` already guards against empty/null). Redundant `using` directives in all source files must also be removed after implicit usings are enabled.

**Done when**: `.csproj` contains `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`; `ErrorViewModel.RequestId` is `string?`; all now-redundant `using` directives are removed; project builds with 0 errors and 0 warnings.

---

### 03-code-patterns: Apply file-scoped namespaces

All `.cs` source files use the legacy block-scoped `namespace Foo { }` syntax. Since C# 10 / .NET 6, file-scoped namespaces (`namespace Foo;`) are the idiomatic style and reduce unnecessary indentation across every source file. Apply file-scoped namespace syntax to all source files: `Program.cs` (if applicable), `Controllers/HomeController.cs`, and `Models/ErrorViewModel.cs`.

**Done when**: All `.cs` source files use file-scoped `namespace Foo;` declarations; project builds with 0 errors and 0 warnings.

---

### 04-final-validation: Build and verify

Perform a full `dotnet build` of the project. Confirm 0 build errors and 0 warnings. Verify that the MVC app configuration is complete (routes, middleware pipeline, static files, error handling for development vs. production).

**Done when**: `dotnet build` reports 0 errors and 0 warnings; `Program.cs` contains the full minimal hosting setup; `Startup.cs` no longer exists in the project.
