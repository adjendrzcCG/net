# Upgrade Plan: SimpleWebApp .NET 8.0 → .NET 10.0

## Overview

| Item | Value |
|------|-------|
| Scenario | dotnet-version-upgrade |
| Strategy | All-at-Once |
| Source Framework | net8.0 |
| Target Framework | net10.0 (LTS) |
| Projects | 1 (SimpleWebApp) |

### Selected Strategy
**All-At-Once** — All projects upgraded simultaneously in a single operation.  
**Rationale**: 1 project on .NET 8.0, no external NuGet packages, straightforward upgrade with no breaking API changes expected.

---

## Tasks

### 01-upgrade-framework
**Update SimpleWebApp from net8.0 to net10.0**

- Update `TargetFramework` in SimpleWebApp.csproj to `net10.0`
- Restore packages and build solution
- Fix any compilation errors introduced by the upgrade
- Modernize Program.cs/Startup.cs to minimal hosting model (Program.cs top-level with WebApplication builder)
- Validate build succeeds with 0 errors and 0 warnings

---

## Success Criteria

- [ ] SimpleWebApp.csproj targets net10.0
- [ ] Solution builds without errors
- [ ] No build warnings
- [ ] Application code updated to use modern .NET 10 patterns where appropriate
