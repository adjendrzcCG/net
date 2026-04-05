# Task: 01-upgrade-framework — Update SimpleWebApp from net8.0 to net10.0

## Objective
Update SimpleWebApp from net8.0 to net10.0 LTS.

## Scope
- **Project**: SimpleWebApp/SimpleWebApp.csproj
- **Change**: TargetFramework net8.0 → net10.0
- **Code modernization**: Migrate from classic Startup.cs pattern to minimal hosting model (WebApplication builder in Program.cs)

## Steps

### 1. Update Project File
- Change `<TargetFramework>net8.0</TargetFramework>` to `<TargetFramework>net10.0</TargetFramework>`

### 2. Modernize Program.cs
Migrate from `IHostBuilder`/`CreateWebHostBuilder` pattern to `WebApplication.CreateBuilder` (minimal hosting model).

### 3. Remove Startup.cs
Consolidate `ConfigureServices` and `Configure` into Program.cs using the minimal hosting model. Delete Startup.cs.

### 4. Build & Fix
Run `dotnet build` and resolve any compilation errors.

## Done When
- SimpleWebApp.csproj targets net10.0
- Program.cs uses minimal hosting model
- Startup.cs removed
- `dotnet build` succeeds with 0 errors and 0 warnings
