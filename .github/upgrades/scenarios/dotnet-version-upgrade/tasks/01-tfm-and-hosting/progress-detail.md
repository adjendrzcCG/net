# Task 01-tfm-and-hosting — Progress Detail

## Changes Made

### SimpleWebApp/SimpleWebApp.csproj
- `TargetFramework`: `net8.0` → `net9.0`
- Added `<Nullable>enable</Nullable>` — enables nullable reference type analysis
- Added `<ImplicitUsings>enable</ImplicitUsings>` — enables ASP.NET Core global implicit usings (required for minimal hosting model `WebApplication` access without explicit `using` directives)

### SimpleWebApp/Program.cs
**Before**: Legacy `CreateHostBuilder` pattern — namespace + class + `Main` method + `IHostBuilder` factory method referencing `Startup<T>` class.

**After**: Minimal hosting model with top-level statements:
- `WebApplication.CreateBuilder(args)` 
- `builder.Services.AddControllersWithViews()` (from Startup.ConfigureServices)
- `WebApplication.Build()` + full middleware pipeline (from Startup.Configure)
- `app.MapControllerRoute(...)` replacing `UseEndpoints` lambda
- `app.Run()`

### SimpleWebApp/Startup.cs
**Deleted** — all logic consolidated into Program.cs.

### SimpleWebApp/Models/ErrorViewModel.cs
- `RequestId` property type changed `string` → `string?` to resolve CS8618 nullable warning introduced by enabling `<Nullable>enable</Nullable>`. This is correct because the property is assigned via `?.Id ?? ...` (can be null).

## Build Result
```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

## Security Scan (CodeQL)
0 alerts found.
