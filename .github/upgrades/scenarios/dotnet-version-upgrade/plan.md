# .NET Version Upgrade Plan

## Overview

**Target**: Upgrade SimpleWebApp from net8.0 to net9.0 with minimal hosting model modernization
**Scope**: Single project, SDK-style, no external NuGet packages — straightforward TFM bump with hosting model migration

### Selected Strategy
**All-At-Once** — All projects upgraded simultaneously in a single operation.
**Rationale**: 1 project, net8.0 (modern), no incompatible packages, low complexity.

---

## Tasks

### 01-upgrade-simplewebapp: Upgrade SimpleWebApp to net9.0 with minimal hosting model

Upgrade the single project `SimpleWebApp/SimpleWebApp.csproj` from `net8.0` to `net9.0`. This involves three coordinated changes:

1. **TFM bump** — Change `<TargetFramework>net8.0</TargetFramework>` to `net9.0` in the project file. Enable implicit usings (`<ImplicitUsings>enable</ImplicitUsings>`) and nullable reference types (`<Nullable>enable</Nullable>`), which are standard for net9.0 projects.

2. **Minimal hosting model migration** — The project currently uses the legacy `Startup.cs` + `IHostBuilder`/`CreateHostBuilder` pattern introduced in .NET Core 3.x. Migrate to the minimal hosting model (top-level statements in `Program.cs`) that is the standard since .NET 6. Consolidate all service registration and middleware pipeline configuration from `Startup.cs` into a single `Program.cs`, then delete `Startup.cs`.

3. **Nullable annotation fixes** — After enabling nullable reference types, resolve any resulting compiler warnings in source files (controllers, models, views codebehind) to achieve a clean build with 0 warnings.

Key files affected: `SimpleWebApp.csproj`, `Program.cs` (full replacement), `Startup.cs` (delete), `Controllers/HomeController.cs` (minor nullable annotations if needed), `Models/ErrorViewModel.cs` (nullable annotations).

**Done when**: Project builds targeting net9.0 with 0 errors and 0 warnings; `Startup.cs` is deleted; `Program.cs` uses top-level statements with `WebApplication.CreateBuilder`.

---

### 02-final-validation: Final validation

Verify the complete upgrade is clean: build the project targeting net9.0 with no errors or warnings, confirm `Startup.cs` no longer exists, and confirm `Program.cs` uses the minimal hosting pattern. Commit all changes with a descriptive message and push to `copilot/kon-update-java-version`.

**Done when**: `dotnet build` reports 0 errors and 0 warnings; changes committed and pushed to remote.
