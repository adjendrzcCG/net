# Upgrade Assessment: SimpleWebApp

## Summary

| Property | Value |
|----------|-------|
| Project | SimpleWebApp/SimpleWebApp.csproj |
| Current TFM | net8.0 |
| Target TFM | net9.0 |
| SDK Style | Yes |
| External NuGet packages | None |
| Security vulnerabilities | None |

## Projects

### SimpleWebApp (SimpleWebApp/SimpleWebApp.csproj)

- **Type**: ASP.NET Core MVC Web Application
- **Current TFM**: net8.0
- **Target TFM**: net9.0
- **Pattern**: Legacy `CreateHostBuilder` + `Startup` class pattern
- **Files**: 3 C# source files (Program.cs, Startup.cs, Controllers/HomeController.cs)

## Identified Work Items

### 1. TFM Upgrade (Required)
Update `<TargetFramework>net8.0</TargetFramework>` → `<TargetFramework>net9.0</TargetFramework>` in SimpleWebApp.csproj.

### 2. Minimal Hosting Model Migration (Required)
The project uses the legacy `CreateHostBuilder` / `Startup` pattern:
- **Program.cs** — Uses `Host.CreateDefaultBuilder`, `ConfigureWebHostDefaults`, `webBuilder.UseStartup<Startup>()`
- **Startup.cs** — Has `ConfigureServices` (adds ControllersWithViews) and `Configure` (sets up middleware pipeline)

Migration plan:
- Replace Program.cs content with `WebApplication.CreateBuilder` top-level statements
- Merge `ConfigureServices` → `builder.Services.AddControllersWithViews()`
- Merge `Configure` → Middleware calls on the `app` object
- Delete Startup.cs

### 3. Package Updates
No external NuGet packages referenced — no package updates required.

## Risks

| Risk | Severity | Notes |
|------|----------|-------|
| Hosting model migration | Low | Standard pattern, well-documented migration path |
| net9.0 breaking changes | Low | No deprecated APIs in use |

## Conclusion

Straightforward upgrade with no external dependencies. Two tasks: upgrade TFM + migrate hosting model.
