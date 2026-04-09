# .NET Version Upgrade Plan

## Overview

**Target**: Upgrade SimpleWebApp from net8.0 to net9.0 and apply .NET modernization best practices
**Scope**: 1 project (SimpleWebApp), small codebase (~100 LOC C#, MVC web app)

### Selected Strategy
**All-at-Once** — Single project upgraded simultaneously in one operation.
**Rationale**: 1 project, already on modern .NET (net8.0), straightforward TFM bump with modernization.

## Tasks

### 01-upgrade-and-modernize: Upgrade SimpleWebApp to net9.0 with modernization improvements

Upgrade the project from net8.0 to net9.0, enable modern C# project features, and apply the minimal hosting model. The project currently uses the legacy `Startup.cs` / `IHostBuilder` pattern (introduced in ASP.NET Core 2.x) and lacks nullable reference types and implicit usings — all of which are standard in .NET 6+ templates.

Changes to apply:
- Update `<TargetFramework>` from `net8.0` to `net9.0` in SimpleWebApp.csproj
- Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` to the project file
- Merge `Startup.cs` into `Program.cs` using the `WebApplication.CreateBuilder` minimal hosting pattern (top-level statements), then delete `Startup.cs`
- Fix all nullable warnings introduced by enabling nullable reference types (e.g., `RequestId` in `ErrorViewModel` → `string?`)
- Remove redundant `using` directives made unnecessary by implicit usings

**Done when**: Project builds with `dotnet build` targeting net9.0 with zero errors and zero warnings; `Startup.cs` no longer exists; `Program.cs` uses `WebApplication.CreateBuilder`; `.csproj` has `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`.

---

### 02-final-validation: Final validation

Verify the upgraded application builds cleanly and all modernization changes are correct. Confirm the minimal hosting model is properly configured and no regressions were introduced.

**Done when**: `dotnet build` succeeds with zero errors and zero warnings on net9.0; `dotnet run` starts the application successfully.
