# Upgrade Plan: SimpleWebApp → net9.0

## Strategy: Single-Project In-Place Upgrade with Modernization

### Assessment Summary
- **Project**: SimpleWebApp (ASP.NET Core MVC)
- **Current Framework**: net8.0
- **Target Framework**: net9.0
- **Packages**: No external NuGet packages (uses only framework-provided packages)
- **Breaking Changes Risk**: Low
- **Modernization Opportunities**: High (legacy Startup.cs pattern, no nullable types)

### Tasks

| ID | Task | Risk |
|----|------|------|
| 01-upgrade-tfm | Upgrade TargetFramework and enable modern C# features | Low |
| 02-minimal-hosting | Convert to minimal hosting model (replace Startup.cs) | Medium |
| 03-nullable-fixes | Fix nullable reference type annotations | Low |
| 04-modernize-code | Apply remaining modernization (links, C# features) | Low |
| 05-validate | Build and validate | Low |
