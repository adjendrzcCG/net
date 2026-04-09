# .NET Version Upgrade Plan

## Overview

**Target**: Upgrade SimpleWebApp from net8.0 to net9.0 with code modernization
**Scope**: 1 project, ~120 LOC, no external NuGet dependencies

---

### Selected Strategy
**All-at-Once** — All projects upgraded simultaneously in a single operation.
**Rationale**: 1 project, all on modern .NET, no package dependencies, straightforward upgrade.

## Tasks

### 01-prerequisites: Verify SDK and baseline build

Confirm that the .NET 9 SDK is available and that the project builds cleanly on the current framework before any changes. This establishes a green baseline to compare against post-upgrade.

Project: `SimpleWebApp/SimpleWebApp.csproj` (net8.0, Microsoft.NET.Sdk.Web)

**Done when**: .NET 9 SDK is confirmed available; `dotnet build` on the current project produces 0 errors and 0 warnings.

---

### 02-upgrade-simplewebapp: Upgrade SimpleWebApp to .NET 9 with modernization

Upgrade the single project to net9.0 and apply all modernization improvements identified in the assessment:

1. **TFM upgrade**: Change `<TargetFramework>` from `net8.0` to `net9.0` in the csproj.
2. **Enable nullable reference types**: Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` to the project file; fix the resulting nullable warning in `ErrorViewModel.RequestId` (change to `string?`).
3. **Migrate to minimal hosting model**: Consolidate `Startup.cs` and `Program.cs` into a single modern `Program.cs` using top-level statements and `WebApplication.CreateBuilder`. Delete `Startup.cs`.
4. **Remove redundant using directives**: After enabling implicit usings, remove `using` directives that are now provided by the SDK automatically.

The project has no external NuGet packages, so no package updates are needed. The only breaking change risk is the minimal hosting model migration, which has a well-defined migration pattern.

**Done when**: Project builds targeting net9.0 with 0 errors and 0 warnings; `Startup.cs` is deleted; `Program.cs` uses the minimal hosting model; nullable reference types are enabled; all redundant using statements are removed.

---

### 03-final-validation: Final validation and commit

Perform a clean build of the solution targeting net9.0, verify all files are correct, and commit all changes.

**Done when**: `dotnet build` produces 0 errors and 0 warnings; changes are committed to the working branch.
