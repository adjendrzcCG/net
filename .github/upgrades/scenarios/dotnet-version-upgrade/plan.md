# .NET Upgrade Plan: net8.0 → net9.0

## Overview

**Project**: SimpleWebApp  
**Upgrade**: net8.0 → net9.0  
**Strategy**: All-at-Once (single project)

### Selected Strategy
**All-At-Once** — All projects upgraded simultaneously in a single operation.  
**Rationale**: 1 project on net8.0, no NuGet dependencies, straightforward TFM bump.

---

## Tasks

### Task 01: Upgrade SimpleWebApp to net9.0

**Scope**: Update TargetFramework in SimpleWebApp.csproj, restore, build, fix any issues.

**Steps**:
1. Update `<TargetFramework>` from `net8.0` to `net9.0`
2. Restore dependencies
3. Build and fix any compilation errors
4. Verify: builds with 0 errors and 0 warnings
