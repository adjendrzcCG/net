
## [2026-04-03 08:07] 01-upgrade

Upgraded SimpleWebApp from net8.0 to net9.0 and migrated from the legacy Startup.cs pattern to the minimal hosting model. Updated the target framework in the .csproj, replaced Program.cs with top-level statements using WebApplication.CreateBuilder, deleted Startup.cs (merging all service registration and middleware configuration inline), added ImplicitUsings and Nullable to the project, and fixed a CS8618 nullable warning in ErrorViewModel. Build: 0 errors, 0 warnings. App starts successfully on net9.0.

