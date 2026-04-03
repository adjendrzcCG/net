# .NET Version Upgrade Plan — SimpleWebApp

### Selected Strategy
**All-At-Once** — Single project upgraded in one atomic operation.
**Rationale**: 1 project, SDK-style, net8.0, no external NuGet packages, straightforward TFM bump plus hosting model modernization.

## Overview

**Target**: Upgrade SimpleWebApp from net8.0 to net9.0 and migrate the hosting model to the minimal API pattern.
**Scope**: Small project (~3 C# source files) — all changes can be done in one pass.

## Tasks

### 01-upgrade: Upgrade to net9.0 and migrate to minimal hosting model

Update the project's target framework to `net9.0` and replace the legacy `CreateHostBuilder` / `Startup` class pattern with the modern minimal hosting model using `WebApplication.CreateBuilder` and top-level statements.

The project currently uses the old `IHostBuilder`-based pattern where `Program.cs` delegates all service registration and middleware configuration to a separate `Startup.cs` file. The migration merges both files into a single `Program.cs` with top-level statements, removing `Startup.cs` entirely.

Key changes:
- **SimpleWebApp.csproj**: Change `<TargetFramework>net8.0</TargetFramework>` to `<TargetFramework>net9.0</TargetFramework>`
- **Program.cs**: Replace with top-level statements using `WebApplication.CreateBuilder(args)`, `builder.Services.AddControllersWithViews()`, and `app.MapControllerRoute(...)` — merging all content from `Startup.ConfigureServices` and `Startup.Configure`
- **Startup.cs**: Delete — all content moved to `Program.cs`

No package updates are required (zero external NuGet dependencies).

**Done when**: `dotnet build` succeeds with zero errors and zero warnings on the net9.0 target; `Startup.cs` has been removed; `Program.cs` uses top-level statements with `WebApplication.CreateBuilder`; application starts correctly via `dotnet run`.
