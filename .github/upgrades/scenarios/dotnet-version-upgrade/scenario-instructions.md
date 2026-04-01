# Scenario Instructions

## Scenario

**Type**: dotnet-version-upgrade (modernization improvements)  
**Project**: SimpleWebApp  
**Path**: /home/runner/work/net/net/SimpleWebApp  
**Target Framework**: net8.0 (already set; modernizing code patterns)  
**Flow Mode**: Automatic

## Upgrade Parameters

- Target framework: net8.0 (no framework version change needed)
- Source branch: main
- Modernization goals:
  - Minimal hosting model (WebApplication.CreateBuilder)
  - Top-level statements in Program.cs
  - Merge Startup.cs into Program.cs
  - Nullable reference types enabled
  - Implicit usings enabled
  - File-scoped namespaces

## User Preferences

### Execution Style
- Flow Mode: Automatic — run end-to-end without pausing for approval

### Technical Preferences
- All C# language modernizations requested by user

## Key Decisions Log

- 2024: User requested full .NET 8 modernization on project that already targets net8.0 but uses legacy patterns
