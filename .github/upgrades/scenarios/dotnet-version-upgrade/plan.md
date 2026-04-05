# .NET Modernization Plan — SimpleWebApp

## Overview

**Target**: Modernize SimpleWebApp to use idiomatic .NET 8 patterns  
**Scope**: Single project, ~5 C# files, straightforward mechanical changes

## Tasks

### 01-project-file: Enable nullable reference types and implicit usings

Enable `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` in `SimpleWebApp.csproj`. These are the default settings for new .NET 6+ projects and unlock compiler-enforced null safety and reduced boilerplate.

**Done when**: The csproj file contains both properties and the project still builds (warnings expected until subsequent tasks fix them).

---

### 02-minimal-hosting: Migrate to minimal hosting model

Replace the two-file `Program.cs` + `Startup.cs` pattern with a single `Program.cs` using top-level statements and the `WebApplication.CreateBuilder` API introduced in .NET 6. The `ConfigureServices` content maps to builder service registration; the `Configure` content maps to middleware pipeline setup on the built `app`. Delete `Startup.cs` after the merge.

**Done when**: `Startup.cs` no longer exists, `Program.cs` uses `WebApplication.CreateBuilder`, and the project builds.

---

### 03-nullable-fixes: Fix nullable warnings in models and controllers

With nullable reference types enabled, `ErrorViewModel.RequestId` must be typed as `string?` because it is assigned from a nullable expression (`Activity.Current?.Id`). Apply file-scoped namespaces to both `Models/ErrorViewModel.cs` and `Controllers/HomeController.cs` while removing explicit `using` directives that are already covered by implicit usings.

**Done when**: Project builds with zero errors and zero nullable-related warnings.

---

### 04-validate-build: Final build validation

Run a full `dotnet build` and confirm the project compiles cleanly with no errors and no warnings.

**Done when**: `dotnet build` exits with code 0 and reports 0 Error(s), 0 Warning(s).
