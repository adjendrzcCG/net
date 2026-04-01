# Upgrade Plan: SimpleWebApp .NET 8 Modernization

**Strategy**: All-At-Once  
**Rationale**: Single project, already on net8.0, no .NET Framework concerns, straightforward code-pattern modernization.

---

### Selected Strategy
**All-At-Once** — All modernization changes applied simultaneously in a single operation.  
**Rationale**: 1 project, already on net8.0, all SDK-style, no incompatible packages, clear and bounded scope.

---

## Project Scope

| Project | Current TFM | Changes Needed |
|---|---|---|
| SimpleWebApp | net8.0 | Minimal hosting model, nullable, implicit usings, file-scoped namespaces |

---

## Tasks

### 01-prerequisites: Verify environment and capture baseline

Verify the .NET 8 SDK is available and that the project builds on the current baseline before any changes. This establishes a known-good starting point.

**Done when**: `dotnet build` exits with 0 errors on the unmodified project.

---

### 02-modernize-hosting: Migrate to minimal hosting model

The project currently uses the `CreateHostBuilder` + `Startup` class pattern. Migrate `Program.cs` to the .NET 6+ minimal hosting model using `WebApplication.CreateBuilder` with top-level statements. Consolidate all service registration and middleware pipeline configuration from `Startup.cs` into the new `Program.cs`. Delete `Startup.cs` once its content has been fully migrated.

Services to migrate: `services.AddControllersWithViews()`  
Middleware to migrate: `UseDeveloperExceptionPage`, `UseExceptionHandler`, `UseHsts`, `UseHttpsRedirection`, `UseStaticFiles`, `UseRouting`, `UseAuthorization`, `UseEndpoints` (→ `MapControllerRoute`).

**Done when**: `Program.cs` uses `WebApplication.CreateBuilder`, `Startup.cs` is deleted, and the project builds with 0 errors.

---

### 03-enable-nullable: Enable nullable reference types

Add `<Nullable>enable</Nullable>` to `SimpleWebApp.csproj`. Fix the `ErrorViewModel.RequestId` property which is assigned from `Activity.Current?.Id ?? HttpContext.TraceIdentifier` — it must be declared as `string?` (or given a non-null default initializer) to satisfy the nullable analysis. Verify no other nullable warnings are introduced.

**Done when**: `<Nullable>enable</Nullable>` is set in csproj, `ErrorViewModel.RequestId` is `string?`, build has 0 errors and 0 nullable warnings.

---

### 04-implicit-usings: Enable implicit usings and remove redundant directives

Add `<ImplicitUsings>enable</ImplicitUsings>` to `SimpleWebApp.csproj`. Remove all `using` directives from `.cs` files that are now covered by the SDK's implicit usings for `Microsoft.NET.Sdk.Web` (covers `System`, `System.Diagnostics`, `Microsoft.AspNetCore.Mvc`, `Microsoft.Extensions.Logging`, etc.). Keep any `using` directives that are still needed.

**Done when**: `<ImplicitUsings>enable</ImplicitUsings>` is set in csproj, redundant using directives removed, build has 0 errors.

---

### 05-file-scoped-namespaces: Convert to file-scoped namespaces

Convert all `.cs` files from block-scoped namespaces (`namespace Foo { ... }`) to file-scoped namespaces (`namespace Foo;`). This is a C# 10+ feature available in all net6.0+ projects. Remove one level of indentation from each file's type declarations.

Affected files: `Program.cs` (if using a namespace), `Controllers/HomeController.cs`, `Models/ErrorViewModel.cs`.

**Done when**: All `.cs` files use file-scoped namespace syntax, build has 0 errors.

---

### 06-final-validation: Full build validation and cleanup

Run a full build of the solution. Confirm 0 errors and 0 warnings. Verify no `Startup.cs` file remains. Verify `Program.cs` follows minimal hosting idioms.

**Done when**: `dotnet build` exits with 0 errors and 0 warnings.
