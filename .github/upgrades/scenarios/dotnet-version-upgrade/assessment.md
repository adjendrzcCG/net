# Upgrade Assessment: SimpleWebApp → .NET 9.0

Generated: manual analysis (assessment tool unavailable for single-project solution)

## Solution Overview

| Property | Value |
|---|---|
| Solution | SimpleWebApp.slnx |
| Projects | 1 |
| Target | net9.0 |
| Source | net8.0 |

## Projects

### SimpleWebApp

| Property | Value |
|---|---|
| File | SimpleWebApp/SimpleWebApp.csproj |
| SDK | Microsoft.NET.Sdk.Web |
| Current TFM | net8.0 |
| Target TFM | net9.0 |
| NuGet Packages | None (framework-only) |
| Build Status | ✅ Builds clean (0 warnings, 0 errors) |

## Issues & Modernization Opportunities

### 1. Outdated Hosting Pattern (High Priority)
**Files**: `Program.cs`, `Startup.cs`  
The application uses the pre-.NET 6 hosting pattern (`CreateHostBuilder` + `Startup` class). In .NET 6+, the recommended approach is the minimal hosting model where everything is configured in `Program.cs` using top-level statements.

- `Program.cs` uses `IHostBuilder` / `CreateHostBuilder` / `webBuilder.UseStartup<Startup>()`
- `Startup.cs` contains `ConfigureServices` and `Configure` methods
- **Action**: Merge into a single minimal `Program.cs`, delete `Startup.cs`

### 2. Nullable Reference Types Not Enabled (Medium Priority)
**Files**: `SimpleWebApp.csproj`, `Models/ErrorViewModel.cs`  
The project does not have `<Nullable>enable</Nullable>`. With nullable enabled:
- `ErrorViewModel.RequestId` should be `string?` (it can be null)
- **Action**: Add `<Nullable>enable</Nullable>` to project, update `RequestId` to `string?`

### 3. Implicit Usings Not Enabled (Low Priority)
**Files**: `SimpleWebApp.csproj`, all `.cs` files  
The project does not use `<ImplicitUsings>enable</ImplicitUsings>`, requiring explicit `using` statements for common namespaces.
- **Action**: Enable implicit usings, remove now-redundant `using` directives

### 4. Target Framework Upgrade
**File**: `SimpleWebApp.csproj`  
Upgrade `<TargetFramework>` from `net8.0` to `net9.0`.

## Security Vulnerabilities

None detected. The project has no explicit NuGet package dependencies.

## Risk Assessment

| Risk | Level | Mitigation |
|---|---|---|
| Hosting model migration | Low | Well-defined migration pattern; single small project |
| net9.0 breaking changes | Low | No packages or APIs with known breaking changes |
| Nullable reference type warnings | Low | Only one model property needs updating |

## Summary

- **Effort**: Low — small project with no external packages
- **Breaking changes**: None expected
- **Modernization impact**: High — significant code quality improvement
