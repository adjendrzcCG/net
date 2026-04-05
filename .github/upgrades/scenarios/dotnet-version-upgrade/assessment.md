# Upgrade Assessment: SimpleWebApp → net9.0

## Summary

| Item | Value |
|------|-------|
| **Project** | SimpleWebApp |
| **Current Framework** | net8.0 |
| **Target Framework** | net9.0 |
| **Project Type** | ASP.NET Core MVC Web Application |
| **Has Solution File** | No (standalone project) |
| **Overall Risk** | 🟢 Low |

---

## Projects

### SimpleWebApp (`SimpleWebApp.csproj`)

- **Current TFM**: `net8.0`
- **Target TFM**: `net9.0`
- **SDK**: `Microsoft.NET.Sdk.Web`
- **Explicit NuGet Packages**: None (SDK-only)

---

## Package Analysis

No explicit NuGet package references found. All dependencies come from the `Microsoft.NET.Sdk.Web` SDK, which will be resolved automatically against net9.0 runtime packages.

✅ No package vulnerabilities detected.  
✅ No incompatible packages.

---

## Code Analysis

### Hosting Model (Old Pattern — Requires Modernization)

| File | Pattern | Action |
|------|---------|--------|
| `Program.cs` | `IHostBuilder` / `CreateHostBuilder` / `UseStartup<Startup>()` | Replace with `WebApplication.CreateBuilder` |
| `Startup.cs` | `ConfigureServices` + `Configure` methods | Merge into `Program.cs`, then delete |

**Current `Program.cs`** uses:
```csharp
Host.CreateDefaultBuilder(args)
    .ConfigureWebHostDefaults(wb => wb.UseStartup<Startup>());
```

**Current `Startup.cs`** registers:
- `services.AddControllersWithViews()`
- Middleware: `UseDeveloperExceptionPage` / `UseExceptionHandler` / `UseHsts`
- `UseHttpsRedirection`, `UseStaticFiles`, `UseRouting`, `UseAuthorization`
- `endpoints.MapControllerRoute("default", "{controller=Home}/{action=Index}/{id?}")`

### Other Files

| File | Status |
|------|--------|
| `Controllers/HomeController.cs` | No changes needed |
| `Models/ErrorViewModel.cs` | No changes needed |
| `Views/**` | No changes needed |
| `wwwroot/**` | No changes needed |
| `appsettings.json` | No changes needed |
| `Properties/launchSettings.json` | Update `launchUrl` if needed |

---

## Risks

| Risk | Severity | Notes |
|------|----------|-------|
| Hosting model migration | 🟢 Low | Straightforward consolidation; no custom DI wiring |
| Breaking API changes net8→net9 | 🟢 Low | MVC stack stable; no deprecated APIs in use |

---

## Plan of Action

1. **Update `SimpleWebApp.csproj`** — change `<TargetFramework>net8.0</TargetFramework>` → `net9.0`
2. **Rewrite `Program.cs`** — minimal hosting model using `WebApplication.CreateBuilder`
3. **Delete `Startup.cs`** — no longer needed
4. **Build & verify** — confirm clean compilation
