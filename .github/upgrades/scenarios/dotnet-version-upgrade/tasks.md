# Tasks

## 01-project-settings — Enable nullable reference types and implicit usings
**Status**: ✅ completed

Added `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` to SimpleWebApp.csproj.

---

## 02-minimal-hosting — Migrate to minimal hosting top-level Program.cs
**Status**: ✅ completed

Replaced old CreateHostBuilder + Startup.cs pattern with modern top-level Program.cs using WebApplication.CreateBuilder API. Deleted Startup.cs.

---

## 03-file-scoped-namespaces — Apply file-scoped namespaces
**Status**: ✅ completed

Converted block-scoped namespaces to file-scoped namespaces in HomeController.cs and ErrorViewModel.cs.

---

## 04-nullable-types — Fix nullable reference type annotations
**Status**: ✅ completed

Fixed ErrorViewModel.RequestId to `string?`. Removed redundant `using Microsoft.Extensions.Logging` (covered by implicit usings). Removed redundant `UseDeveloperExceptionPage()` else branch (auto-enabled in .NET 8 dev).

---

## 05-build-validate — Build and validate
**Status**: ✅ completed

Build result: **0 errors, 0 warnings**. CodeQL: 0 security alerts.
