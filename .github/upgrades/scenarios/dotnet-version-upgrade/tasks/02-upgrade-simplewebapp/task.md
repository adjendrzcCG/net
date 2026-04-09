# 02-upgrade-simplewebapp: Upgrade SimpleWebApp to .NET 9 with modernization

## Objective
Upgrade SimpleWebApp from net8.0 to net9.0. Apply code modernization: minimal hosting model, nullable reference types, implicit usings.

## Scope
- **Project**: SimpleWebApp/SimpleWebApp.csproj
- **Files to modify**: SimpleWebApp.csproj, Program.cs, Controllers/HomeController.cs, Models/ErrorViewModel.cs
- **Files to delete**: Startup.cs

## Research Findings

### 1. TFM Upgrade
- Change `<TargetFramework>net8.0</TargetFramework>` → `net9.0`
- Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`

### 2. Minimal Hosting Model Migration
Current `Program.cs` uses `IHostBuilder.CreateDefaultBuilder(...).ConfigureWebHostDefaults(webBuilder => webBuilder.UseStartup<Startup>())`.
New `Program.cs` uses `WebApplication.CreateBuilder(args)` + `app.Run()`.
The `Startup.ConfigureServices` and `Startup.Configure` logic is inlined into Program.cs.
`Startup.cs` is then deleted.

### 3. Nullable Reference Types
- `ErrorViewModel.RequestId` is `string` but can be null → change to `string?`
- No other nullable issues expected

### 4. Implicit Usings
Implicit usings for `Microsoft.NET.Sdk.Web` include: `Microsoft.Extensions.Logging`, `Microsoft.AspNetCore.Builder`, `Microsoft.Extensions.DependencyInjection`, `Microsoft.Extensions.Hosting`, `Microsoft.AspNetCore.Hosting`.
- `HomeController.cs`: remove `using Microsoft.Extensions.Logging;` (implicit)
- New `Program.cs`: no explicit usings needed (all are implicit)

## Done When
- Project targets net9.0
- Startup.cs deleted
- Program.cs uses minimal hosting model (top-level statements)
- Nullable enabled, ErrorViewModel.RequestId is string?
- Build: 0 errors, 0 warnings
