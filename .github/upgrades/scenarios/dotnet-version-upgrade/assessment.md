# Assessment — SimpleWebApp Modernization

## Summary

**Project**: SimpleWebApp  
**Target Framework**: net8.0 (already set)  
**Focus**: Code pattern modernization (not framework version upgrade)

## Current State

| Area | Current | Target |
|------|---------|--------|
| Hosting model | `CreateHostBuilder` + `Startup.cs` (ASP.NET Core 3.x pattern) | Minimal hosting (top-level statements) |
| Nullable reference types | Disabled (not configured) | `<Nullable>enable</Nullable>` |
| Implicit usings | Disabled | `<ImplicitUsings>enable</ImplicitUsings>` |
| Namespace style | Block-scoped | File-scoped |
| `ErrorViewModel.RequestId` | `string` (non-nullable) | `string?` (nullable) |
| `Startup.cs` | Present | Removed (merged into Program.cs) |

## Projects

- **SimpleWebApp** (`SimpleWebApp.csproj`) — single-project solution targeting net8.0

## Package Dependencies

No NuGet packages — uses only built-in ASP.NET Core framework packages from the SDK.

## Identified Changes

1. **`SimpleWebApp.csproj`** — Add `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`
2. **`Program.cs`** — Replace `CreateHostBuilder`/`Startup` pattern with minimal hosting model top-level statements; delete `Startup.cs`
3. **`Controllers/HomeController.cs`** — Convert to file-scoped namespace; remove now-redundant `using` directives (handled by implicit usings)
4. **`Models/ErrorViewModel.cs`** — Convert to file-scoped namespace; change `string RequestId` → `string? RequestId`

## Risk Assessment

**Low Risk** — purely code pattern modernization with no framework version change, no package changes, and no breaking API changes.

## Security Vulnerabilities

None detected.
