# .NET Upgrade Plan: net8.0 → net10.0

## Overview

**Target**: net10.0  
**Scope**: 1 project (SimpleWebApp — ASP.NET Core MVC)

### Selected Strategy
**All-At-Once** — Single project upgraded in one atomic operation.  
**Rationale**: 1 project on net8.0, no external packages, low complexity. Clear path to net10.0.

## Tasks

### 01-tfm-upgrade: Update TargetFramework to net10.0

Update the `TargetFramework` property in `SimpleWebApp.csproj` from `net8.0` to `net10.0`.

**Done when**: SimpleWebApp.csproj targets net10.0 and `dotnet restore` succeeds.

---

### 02-hosting-model: Modernize Hosting Model

Migrate from the legacy `CreateHostBuilder`/`Startup.cs` pattern to the minimal hosting model (`WebApplication.CreateBuilder`). Consolidate `Program.cs` and `Startup.cs` into a single `Program.cs`. Remove the `Startup.cs` file.

**Done when**: Program.cs uses `WebApplication.CreateBuilder`, Startup.cs is removed, and the app builds without errors or warnings.

---

### 03-build-validate: Build and Validate

Restore packages and build the solution. Fix any compiler errors or warnings until the build is clean (0 errors, 0 warnings).

**Done when**: `dotnet build` completes with 0 errors and 0 warnings targeting net10.0.
