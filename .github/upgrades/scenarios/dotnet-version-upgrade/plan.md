# Modernization Plan: SimpleWebApp → .NET 8 Modern Patterns

## Current State
- Target Framework: `net8.0` ✅ (already up-to-date)
- Hosting: Old-style `CreateHostBuilder` pattern with separate `Startup.cs`
- C# Language Features: Pre-C#10 (block namespaces, no nullable annotations, no implicit usings)
- No NuGet packages beyond the SDK (pure SDK-style project)

## Modernization Goals
Apply all relevant C# 10/11/12 and .NET 8 modern patterns:

1. **Enable modern project settings** — nullable reference types, implicit usings
2. **Migrate to minimal hosting** — merge Startup.cs into top-level Program.cs
3. **File-scoped namespaces** — apply to all C# source files
4. **Nullable reference type annotations** — fix non-nullable warnings in models
5. **Build & validate** — confirm clean build with no warnings

## Task List

| ID | Task | Status |
|----|------|--------|
| 01-project-settings | Enable nullable RTs and implicit usings in .csproj | Pending |
| 02-minimal-hosting | Migrate to minimal hosting top-level Program.cs | Pending |
| 03-file-scoped-namespaces | Apply file-scoped namespaces to all C# files | Pending |
| 04-nullable-types | Fix nullable annotations in models and controllers | Pending |
| 05-build-validate | Build and validate the solution | Pending |
