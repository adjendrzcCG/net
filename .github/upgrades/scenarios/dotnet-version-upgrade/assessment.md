# Upgrade Assessment: SimpleWebApp → net9.0

## Summary

| Item | Value |
|------|-------|
| **Project** | SimpleWebApp |
| **Current Framework** | net8.0 |
| **Target Framework** | net9.0 |
| **Project Type** | ASP.NET Core MVC Web Application |
| **Risk Level** | Low |
| **Build Status (pre-upgrade)** | ✅ Succeeds with 0 warnings |

---

## Projects

### SimpleWebApp (`SimpleWebApp/SimpleWebApp.csproj`)

**Current**: `net8.0` → **Target**: `net9.0`

No NuGet package references (uses only built-in SDK packages via `Microsoft.NET.Sdk.Web`).

---

## Code Analysis

### Hosting Model (Breaking Pattern)

The project uses the legacy .NET Core 3.x hosting pattern:

- **`Program.cs`** — Uses `IHostBuilder` / `CreateHostBuilder` / `webBuilder.UseStartup<Startup>()`
- **`Startup.cs`** — Contains `ConfigureServices` / `Configure` methods

**Action**: Migrate to the minimal hosting model introduced in .NET 6 and used in net9.0 templates:
- Consolidate into a single `Program.cs` with top-level statements
- Replace `app.UseEndpoints()` with direct `app.MapControllerRoute()`
- Replace `IApplicationBuilder.UseRouting()` explicit call (no longer required in minimal hosting)
- Delete `Startup.cs`

### Controller (`Controllers/HomeController.cs`)

- Uses `Microsoft.Extensions.Logging` — ✅ fully compatible with net9.0
- No deprecated APIs detected

### Views

- Standard Razor views — fully compatible
- Bootstrap 5 static files — no changes needed

---

## Packages

No explicit NuGet packages. Framework packages (part of `Microsoft.NET.Sdk.Web`) are automatically updated when the TFM changes to `net9.0`.

---

## Security Vulnerabilities

None detected.

---

## Risks

| Risk | Level | Mitigation |
|------|-------|------------|
| Hosting model migration | Low | Well-documented migration; project is simple |
| net9.0 breaking changes | Low | No deprecated APIs used in this project |

---

## Modernization Opportunities

1. **Minimal hosting model** — Replace `Startup.cs` + old `Program.cs` with single modern `Program.cs`
2. **Target framework bump** — net8.0 → net9.0 (STS)
3. **Implicit usings** — Already applicable; `<ImplicitUsings>enable</ImplicitUsings>` can be added
4. **Nullable reference types** — `<Nullable>enable</Nullable>` can be enabled for net9.0 compatibility
