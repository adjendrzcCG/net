
## [2026-04-03 07:16] 01-upgrade-simplewebapp

Upgraded SimpleWebApp from net8.0 to net9.0. Changed TargetFramework, enabled Nullable and ImplicitUsings in csproj. Replaced legacy Program.cs (IHostBuilder/CreateHostBuilder/UseStartup pattern) with minimal hosting model using top-level statements and WebApplication.CreateBuilder. Deleted Startup.cs. Updated ErrorViewModel.RequestId to string? for nullable compatibility. Removed redundant Microsoft.Extensions.Logging using from HomeController (now covered by implicit usings). Build: 0 errors, 0 warnings.

