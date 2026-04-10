
## [2026-03-26 08:18] 01-upgrade-framework

## Task 01-upgrade-framework: Complete

**What changed:**
- `SimpleWebApp.csproj`: Updated `TargetFramework` from `net8.0` to `net10.0`. Added `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>` (recommended defaults for .NET 10).
- `Program.cs`: Replaced legacy `IHostBuilder`/`CreateWebHostDefaults`/`Startup` pattern with the modern minimal hosting model using `WebApplication.CreateBuilder`. All middleware configuration from Startup.cs is now in Program.cs.
- `Startup.cs`: Deleted — configuration consolidated into Program.cs.
- `Models/ErrorViewModel.cs`: Fixed nullable warning — `RequestId` property changed from `string` to `string?`.

**Validation:** `dotnet build` → **Build succeeded with 0 errors, 0 warnings** targeting net10.0.

**Security:** CodeQL scan found 0 alerts. No CVE vulnerabilities.


