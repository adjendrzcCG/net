# Assessment: Minimal Hosting Model Migration

## Project Under Analysis
- **Solution/Project**: `SimpleWebApp/SimpleWebApp.csproj`
- **Target Framework**: `net8.0` (already on target — no version change needed)
- **Current pattern**: `Host.CreateDefaultBuilder` + `Startup` class

---

## Current Architecture

### `Program.cs` (legacy pattern)
```csharp
// Uses CreateHostBuilder + IHostBuilder
public class Program
{
    public static void Main(string[] args)
    {
        CreateHostBuilder(args).Build().Run();
    }
    public static IHostBuilder CreateHostBuilder(string[] args) =>
        Host.CreateDefaultBuilder(args)
            .ConfigureWebHostDefaults(webBuilder => { webBuilder.UseStartup<Startup>(); });
}
```

### `Startup.cs`
- `ConfigureServices`: registers `AddControllersWithViews()`
- `Configure`: wires up DeveloperExceptionPage / ExceptionHandler, HTTPS redirect,
  static files, routing, authorization, and the default MVC controller route

---

## Required Changes

| File | Change Required |
|------|----------------|
| `Program.cs` | Rewrite: top-level statements, `WebApplication.CreateBuilder`, inline middleware pipeline |
| `Startup.cs` | **Delete** — all logic merged into new `Program.cs` |
| `Controllers/HomeController.cs` | Apply file-scoped namespace; logging field is already non-nullable (initialized in ctor) |
| `Models/ErrorViewModel.cs` | Apply file-scoped namespace; add `?` nullable annotation on `RequestId` string property |
| `SimpleWebApp.csproj` | Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` |

---

## Risk Assessment

| Risk | Severity | Notes |
|------|----------|-------|
| Middleware ordering change | Low | Preserved exactly as in `Startup.Configure` |
| Service registration change | Low | `AddControllersWithViews()` is a direct 1:1 carry-over |
| Namespace/using changes | Low | `ImplicitUsings` removes need for several manual `using` directives |
| Nullable warnings on `ErrorViewModel.RequestId` | Low | Annotate as `string?` to match actual usage |

---

## Summary

All changes are straightforward, well-understood migrations with zero functional risk.
No new packages required. No breaking API changes. Build should succeed cleanly after migration.
