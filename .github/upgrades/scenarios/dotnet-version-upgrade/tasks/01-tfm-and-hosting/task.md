# Task: 01-tfm-and-hosting — Upgrade TFM + Minimal Hosting Model Migration

## Objective

Modernize the SimpleWebApp project:
1. Upgrade `TargetFramework` from `net8.0` to `net9.0`
2. Migrate from the legacy `CreateHostBuilder`/`Startup.cs` pattern to the modern minimal hosting model with top-level statements in `Program.cs`
3. Delete `Startup.cs`

## Scope

### Files to Modify
- `SimpleWebApp/SimpleWebApp.csproj` — change `net8.0` → `net9.0`
- `SimpleWebApp/Program.cs` — rewrite with minimal hosting model

### Files to Delete
- `SimpleWebApp/Startup.cs`

## Steps

1. Update `SimpleWebApp.csproj`: set `<TargetFramework>net9.0</TargetFramework>`
2. Rewrite `Program.cs` using:
   - Top-level statements (no `namespace`/`class Program`/`Main` method)
   - `WebApplication.CreateBuilder(args)` instead of `Host.CreateDefaultBuilder`
   - `builder.Services.AddControllersWithViews()` from Startup.ConfigureServices
   - Middleware pipeline from Startup.Configure (dev exception page, HSTS, HTTPS redirect, static files, routing, auth, MVC endpoint)
3. Delete `Startup.cs`
4. Build and verify: `dotnet build SimpleWebApp/SimpleWebApp.csproj` — 0 errors, 0 warnings

## Validation

```
dotnet build /home/runner/work/net/net/SimpleWebApp/SimpleWebApp.csproj
```

Expected: Build succeeded, 0 Error(s), 0 Warning(s)
