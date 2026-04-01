# Upgrade Plan: SimpleWebApp .NET 8 Modernization

## Summary

Modernize SimpleWebApp (already on net8.0) to use current .NET 8 idiomatic patterns:
minimal hosting model, top-level statements, nullable reference types, implicit usings, and file-scoped namespaces.

## Tasks

| ID | Description | Status |
|---|---|---|
| 01-project-properties | Enable Nullable and ImplicitUsings in csproj | ⬜ Pending |
| 02-minimal-hosting | Migrate to minimal hosting model; merge Startup.cs into Program.cs | ⬜ Pending |
| 03-modernize-controller | Apply file-scoped namespace and implicit usings to HomeController.cs | ⬜ Pending |
| 04-modernize-errorviewmodel | Apply file-scoped namespace and fix nullable RequestId | ⬜ Pending |
| 05-build-validation | Build solution and verify no errors or warnings | ⬜ Pending |

## Execution Order

Tasks run sequentially. Project properties (01) must be done before language modernizations (03, 04)
since enabling nullable will affect what annotations are needed.
