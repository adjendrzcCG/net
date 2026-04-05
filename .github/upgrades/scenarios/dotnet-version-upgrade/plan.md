# dotnet-version-upgrade Plan

## Overview

**Target**: Upgrade SimpleWebApp from net8.0 to net9.0 with full C# modernization
**Scope**: Single ASP.NET Core MVC web application, small codebase (~100 LOC)

### Selected Strategy
**All-At-Once** — All projects upgraded simultaneously in a single operation.
**Rationale**: 1 project on net8.0, straightforward upgrade with no breaking changes.

## Tasks

### 01-project-config: Update project file

Update `SimpleWebApp.csproj` to target net9.0 and enable modern C# features. This includes:
- Set `TargetFramework` to `net9.0`
- Enable `<Nullable>enable</Nullable>` for null-safety
- Enable `<ImplicitUsings>enable</ImplicitUsings>` to reduce boilerplate using directives
- Set C# language version to `latest`

**Done when**: The `.csproj` targets net9.0, nullable and implicit usings are enabled, and the project restores successfully.

---

### 02-minimal-hosting: Migrate to minimal hosting model

Replace the legacy .NET 3.1-style `Program.cs` (using `CreateHostBuilder`) and `Startup.cs` class with the modern minimal hosting model introduced in .NET 6.

The new `Program.cs` should use top-level statements and `WebApplication.CreateBuilder`, consolidating all service registration and middleware pipeline configuration in a single file. The `Startup.cs` file should be deleted.

**Done when**: `Startup.cs` is deleted, `Program.cs` uses `WebApplication.CreateBuilder` with top-level statements, and all services and middleware from the old `Startup.cs` are preserved.

---

### 03-csharp-modernization: Apply C# 12 modernization

Apply modern C# language features across all source files:
- Convert block-style namespaces to file-scoped namespaces (`namespace Foo;`)
- Remove redundant `using` directives now covered by implicit usings
- Convert `ErrorViewModel` to a `record` type
- Fix nullable reference type annotations (`string RequestId` → `string? RequestId`)
- Apply `primary constructors` where applicable
- Apply other idiomatic .NET 9 / C# 13 patterns

Affects: `HomeController.cs`, `ErrorViewModel.cs`, `Program.cs`

**Done when**: All files use file-scoped namespaces, `ErrorViewModel` is a record with nullable `RequestId`, no nullable warnings are present.

---

### 04-validate: Build and test validation

Build the solution and verify it compiles without errors or warnings.

**Done when**: `dotnet build` completes with 0 errors and 0 warnings.
