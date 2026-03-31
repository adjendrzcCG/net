
## [2026-03-31 14:36] 01-upgrade-simplewebapp

Upgraded SimpleWebApp from net8.0 to net9.0 with minimal hosting model.

Changes made:
- SimpleWebApp.csproj: TargetFramework net8.0 → net9.0; added ImplicitUsings=enable and Nullable=enable
- Program.cs: Replaced IHostBuilder/UseStartup<Startup> with WebApplication.CreateBuilder minimal hosting model; transferred all middleware and routing from Startup.cs
- Startup.cs: Deleted (logic consolidated into Program.cs)
- Models/ErrorViewModel.cs: RequestId changed to string? to fix CS8618 nullable warning

Validation: dotnet build → Build succeeded, 0 errors, 0 warnings.
Code review: No comments. CodeQL: 0 alerts.

