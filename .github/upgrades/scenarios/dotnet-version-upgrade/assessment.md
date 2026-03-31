# Assessment — SimpleWebApp .NET Upgrade

## Solution Overview

| Property | Value |
|----------|-------|
| Project | SimpleWebApp |
| Project Path | `SimpleWebApp/SimpleWebApp.csproj` |
| Current Framework | net8.0 |
| Target Framework | net9.0 |
| Project Type | ASP.NET Core MVC Web Application |
| SDK Style | Yes (Microsoft.NET.Sdk.Web) |

## Dependency Analysis

**External NuGet packages**: None (no explicit `<PackageReference>` entries)  
**Framework-provided packages**: All via implicit Microsoft.NET.Sdk.Web references  
**Project-to-project references**: None  

## Code Inventory

| File | Description | Modernization Needed |
|------|-------------|---------------------|
| `SimpleWebApp.csproj` | Project file | Upgrade TFM to net9.0; add Nullable+ImplicitUsings |
| `Program.cs` | App entry point | Uses old IHostBuilder + Startup.cs pattern → minimal hosting model |
| `Startup.cs` | Service/middleware config | Merge into Program.cs, then delete |
| `Controllers/HomeController.cs` | MVC controller | Remove unused `ILogger` dependency (never called in any action) |
| `Models/ErrorViewModel.cs` | Error view model | `RequestId` must be `string?` when nullable enabled |
| Views & Static files | Razor views, CSS, JS | No changes needed |

## Identified Issues

### Framework Upgrade
- `net8.0` → `net9.0`: straightforward TFM bump, no breaking changes for this app

### Code Modernization
1. **Nullable Reference Types** (Medium): Not enabled; `ErrorViewModel.RequestId` is `string` (non-nullable) but can be null — fix to `string?`
2. **Minimal Hosting Model** (Medium): `Program.cs` uses the pre-.NET 6 `IHostBuilder` + `Startup.cs` pattern. Modern apps use `WebApplication.CreateBuilder()` with top-level statements
3. **Implicit Usings** (Low): Not enabled; explicit `using` directives can be removed once enabled
4. **Unused Dependency** (Low): `HomeController` injects `ILogger<HomeController>` but never calls it — remove the unused dependency

## Security Vulnerabilities
None detected — no external NuGet package dependencies.

## Risks
- **Low risk**: Simple MVC app with no external packages and no complex middleware
- **Minimal breaking changes** expected between net8.0 and net9.0 for this application

## Assessment Summary
This is a straightforward upgrade. The app has zero NuGet package dependencies and simple structure. 
The main work is:
1. TFM bump to `net9.0`
2. Enabling nullable reference types + implicit usings
3. Modernizing the hosting model (Program.cs + Startup.cs → single minimal Program.cs)
4. Remove unused ILogger dependency from HomeController

**Recommended Strategy**: All-at-Once
