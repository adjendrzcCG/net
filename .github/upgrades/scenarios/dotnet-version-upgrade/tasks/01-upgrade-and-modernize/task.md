# 01-upgrade-and-modernize: Upgrade SimpleWebApp to net9.0 with modernization improvements

## Objective
Upgrade SimpleWebApp from net8.0 to net9.0 and apply .NET 6+ modernization best practices.

## Scope Inventory

**Project affected**: SimpleWebApp (SimpleWebApp/SimpleWebApp.csproj)

**Files to modify**:
- `SimpleWebApp/SimpleWebApp.csproj` — TFM upgrade, add Nullable and ImplicitUsings
- `SimpleWebApp/Program.cs` — Replace with minimal hosting model (top-level statements)
- `SimpleWebApp/Startup.cs` — Delete after merging into Program.cs
- `SimpleWebApp/Models/ErrorViewModel.cs` — Change `string RequestId` → `string? RequestId` for nullable
- `SimpleWebApp/Controllers/HomeController.cs` — Remove `using Microsoft.Extensions.Logging;` (covered by web SDK implicit usings)

**Distinct concerns**:
1. TFM bump: net8.0 → net9.0
2. Project features: add `<Nullable>enable</Nullable>` + `<ImplicitUsings>enable</ImplicitUsings>`
3. Minimal hosting model: merge Startup.cs into Program.cs, delete Startup.cs
4. Nullable fix: `ErrorViewModel.RequestId` → `string?`
5. Implicit using cleanup: remove now-redundant `using Microsoft.Extensions.Logging;` from HomeController.cs

**Assessment findings**:
- No NuGet packages in the project (no PackageReference entries) — TFM bump only
- `Microsoft.NET.Sdk.Web` implicit usings cover: `Microsoft.Extensions.Logging`, `Microsoft.AspNetCore.Builder`, `Microsoft.Extensions.DependencyInjection`, `Microsoft.Extensions.Hosting`, `Microsoft.AspNetCore.Hosting`, etc.
- `Microsoft.AspNetCore.Mvc` and `System.Diagnostics` are NOT covered by implicit usings — must keep those usings
- No stubs found (fresh codebase)

## Done When
- Project builds with `dotnet build` targeting net9.0 with zero errors and zero warnings
- `Startup.cs` no longer exists
- `Program.cs` uses `WebApplication.CreateBuilder` (top-level statements)
- `.csproj` has `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`
- `ErrorViewModel.RequestId` is `string?`
