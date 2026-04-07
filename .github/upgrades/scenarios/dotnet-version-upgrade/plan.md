# Upgrade Plan — SimpleWebApp Modernization

## Selected Strategy
**All-At-Once** — All changes applied simultaneously in a single operation.  
**Rationale**: 1 project, already net8.0, no framework version change needed. Pure code pattern modernization.

## Projects in Scope

| Project | Current TFM | Target TFM | Type |
|---------|-------------|------------|------|
| SimpleWebApp | net8.0 | net8.0 | ASP.NET Core MVC Web App |

---

## Tasks

### 01-modernize-project-file: Enable modern csproj properties

Update `SimpleWebApp.csproj` to enable `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`. These two properties unlock modern C# features (nullable reference types and global using directives) and are baseline requirements for the code modernization tasks that follow.

**Done when**: `SimpleWebApp.csproj` contains both `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`; project builds with these settings enabled.

---

### 02-minimal-hosting: Migrate to minimal hosting model

Replace the old `CreateHostBuilder`/`Startup.cs` pattern with the ASP.NET Core minimal hosting model using top-level statements in `Program.cs`. Merge all service registrations and middleware pipeline configuration from `Startup.cs` into `Program.cs`. Delete `Startup.cs` once all content is merged.

Affected files:
- `Program.cs` — rewrite to top-level statements using `WebApplication.CreateBuilder` / `app.Run()`
- `Startup.cs` — delete after merge

**Done when**: `Program.cs` uses `WebApplication.CreateBuilder`, `builder.Build()`, and `app.Run()` top-level statements; `Startup.cs` is deleted; project builds and runs correctly.

---

### 03-file-scoped-namespaces: Apply file-scoped namespaces and fix nullable warnings

Convert all C# source files from block-scoped to file-scoped namespace declarations. Update `ErrorViewModel.RequestId` from `string` to `string?` to satisfy the newly enabled nullable reference types. Remove redundant `using` directives that are now covered by implicit usings.

Affected files:
- `Controllers/HomeController.cs`
- `Models/ErrorViewModel.cs`

**Done when**: All C# files use file-scoped namespaces (`namespace X;`); `ErrorViewModel.RequestId` is `string?`; solution builds with 0 errors and 0 warnings.

---

### 04-final-validation: Full build and validation

Build the complete solution and confirm there are no errors or warnings. Verify all modernization changes are consistent and correct.

**Done when**: `dotnet build` exits with 0 errors and 0 warnings.
