# Upgrade Plan: SimpleWebApp .NET Modernization

### Selected Strategy
**All-at-Once** — Single project upgraded atomically.  
**Rationale**: 1 project, net8.0 baseline, low-risk TFM bump + minimal hosting model migration.

---

## Tasks

| ID | Description | Status |
|---|---|---|
| 01-tfm-and-hosting | Upgrade TFM to net9.0, migrate to minimal hosting model | ⬜ Pending |

---

## Task Detail

### `01-tfm-and-hosting` — Upgrade TFM + Minimal Hosting Model Migration

**Scope**:
- `SimpleWebApp.csproj`: change `TargetFramework` from `net8.0` to `net9.0`
- `Program.cs`: rewrite using top-level statements and `WebApplication.CreateBuilder`
- `Startup.cs`: delete (logic consolidated into `Program.cs`)
- `Controllers/HomeController.cs`: remove `using Microsoft.Extensions.Logging` if it becomes unused (keep if still needed)
- Validate: `dotnet build` with 0 errors and 0 warnings

**Expected Result**: Single `Program.cs` containing all service registration and middleware pipeline configuration; no `Startup.cs`.
