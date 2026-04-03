# Progress Details — 01-upgrade

## Changes Made

### SimpleWebApp/SimpleWebApp.csproj
- Changed `<TargetFramework>` from `net8.0` to `net9.0`
- Added `<Nullable>enable</Nullable>` to enable nullable reference types (required for warning-free build with modern .NET)
- Added `<ImplicitUsings>enable</ImplicitUsings>` to provide global usings for ASP.NET Core types (required by the minimal hosting model's top-level statements)

### SimpleWebApp/Program.cs
Completely replaced the legacy `CreateHostBuilder` / `Startup` pattern with the minimal hosting model using top-level statements:
- **Before**: `Host.CreateDefaultBuilder` → `ConfigureWebHostDefaults` → `UseStartup<Startup>()` with a separate `Startup` class
- **After**: `WebApplication.CreateBuilder(args)` → `builder.Services.AddControllersWithViews()` → `builder.Build()` → middleware pipeline → `app.Run()`
- All middleware configuration from `Startup.Configure()` merged into `Program.cs`
- All service registration from `Startup.ConfigureServices()` merged into `Program.cs`

### SimpleWebApp/Startup.cs
**Deleted** — all content merged into `Program.cs`.

### SimpleWebApp/Models/ErrorViewModel.cs
- Changed `string RequestId` to `string? RequestId` to fix CS8618 nullable reference warning introduced by enabling `<Nullable>enable</Nullable>`

## Validation Results

- `dotnet build SimpleWebApp/SimpleWebApp.csproj` → **0 errors, 0 warnings** ✅
- `dotnet run` → Application starts on `http://localhost:5198`, Hosting environment: Development ✅
- Output assembly: `bin/Debug/net9.0/SimpleWebApp.dll` ✅

## Summary

Upgraded from net8.0 to net9.0 in a single atomic pass. No external NuGet packages needed changes. The hosting model migration was straightforward — all `ConfigureServices` and `Configure` content mapped 1:1 to the minimal hosting model equivalents.
