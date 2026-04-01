# Upgrade Plan: SimpleWebApp .NET 8 Modernization

**Strategy**: All-at-Once (single project, atomic upgrade)  
**Target**: net8.0 minimal hosting model + modern project defaults

---

## Strategy Declaration

```
Strategy: All-at-Once
Rationale: Single project with no dependencies — atomic upgrade is safest and simplest.
Commit: Single commit at end after all tasks validated.
```

---

## Tasks

### 01-modernize-hosting: Migrate to minimal hosting model

Replace the legacy `Program.cs`/`Startup.cs` pattern with a single top-level-statements
`Program.cs`. The `Startup` class with its `ConfigureServices`/`Configure` methods is
replaced by the `WebApplication.CreateBuilder` / `app.Build()` / `app.Run()` pattern
introduced in .NET 6.

The existing middleware pipeline (`UseHttpsRedirection`, `UseStaticFiles`, `UseRouting`,
`UseAuthorization`, `UseEndpoints`) and service registrations (`AddControllersWithViews`)
must be preserved exactly.

**Done when**: `Startup.cs` is deleted; `Program.cs` uses top-level statements and
`WebApplication.CreateBuilder`; project builds and runs without errors.

---

### 02-project-settings: Enable nullable and implicit usings in .csproj

Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` to
the `<PropertyGroup>` in `SimpleWebApp.csproj`. These are the recommended defaults
for all new .NET 6+ projects and align the project with modern SDK conventions.

**Done when**: `.csproj` contains both properties; project builds without errors or warnings.

---

### 03-nullable-types: Fix nullable annotations and clean up usings

With nullable enabled, `ErrorViewModel.RequestId` must be annotated as `string?`
(it is legitimately nullable — the `ShowRequestId` property checks for null/empty).

With implicit usings enabled, explicit `using` directives for `System.Diagnostics`,
`Microsoft.AspNetCore.Mvc`, `Microsoft.Extensions.Logging`, `Microsoft.AspNetCore.Builder`,
`Microsoft.AspNetCore.Hosting`, `Microsoft.Extensions.Configuration`,
`Microsoft.Extensions.DependencyInjection`, and `Microsoft.Extensions.Hosting`
in controller and model files can be removed.

**Done when**: Project builds with zero warnings; nullable annotations are correct;
no redundant explicit `using` directives remain.
