# SimpleWebApp .NET 9 Upgrade Plan

## Overview

**Target**: Upgrade SimpleWebApp from net8.0 to net9.0 and modernize the codebase.  
**Scope**: Single project, no external NuGet dependencies, straightforward upgrade.

### Selected Strategy
**All-at-Once** — All changes applied simultaneously in a single atomic operation.  
**Rationale**: 1 project on net8.0, no external packages, no breaking changes expected.

## Tasks

### 01-upgrade-project-file: Update project file to net9.0

Update `SimpleWebApp/SimpleWebApp.csproj` to target net9.0 and enable modern compiler features.
Change the `TargetFramework` from `net8.0` to `net9.0`, add `<Nullable>enable</Nullable>`, and add `<ImplicitUsings>enable</ImplicitUsings>`.

**Done when**: Project file targets net9.0 with nullable and implicit usings enabled; `dotnet restore` completes successfully.

---

### 02-modernize-hosting-model: Migrate to minimal hosting model

The app currently uses the pre-.NET 6 `IHostBuilder` + `Startup.cs` pattern. Rewrite `Program.cs` to use the modern minimal hosting model (`WebApplication.CreateBuilder()`, top-level statements). Merge all service registrations and middleware from `Startup.cs` into `Program.cs`, then delete `Startup.cs`.

**Done when**: `Startup.cs` is deleted, `Program.cs` uses `WebApplication.CreateBuilder()` with top-level statements, and the app's middleware pipeline is functionally equivalent.

---

### 03-fix-nullable-types: Fix nullable reference type issues

With nullable reference types enabled, `ErrorViewModel.RequestId` is declared as `string` but can be null. Change `string RequestId` to `string? RequestId`. Remove explicit `using` directives covered by implicit usings. Update namespace declarations to file-scoped style.

**Done when**: No nullable warnings remain; all types have correct nullability annotations.

---

### 04-apply-modern-csharp: Remove unused ILogger dependency from HomeController

`HomeController` injects `ILogger<HomeController>` but never calls any logging method in any action.
Remove the unused `ILogger` constructor parameter and the private `_logger` field.

**Done when**: `HomeController` has no unused injected dependencies; build produces 0 warnings.

---

### 05-build-and-validate: Build and validate

Run `dotnet build --configuration Release` and confirm 0 errors and 0 warnings.

**Done when**: `dotnet build` exits with code 0, no errors, no warnings.
