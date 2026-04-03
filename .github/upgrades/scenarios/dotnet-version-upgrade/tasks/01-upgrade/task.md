# 01-upgrade: Upgrade to net9.0 and migrate to minimal hosting model

## Objective

Update SimpleWebApp from net8.0 to net9.0 and replace the legacy `CreateHostBuilder` / `Startup` class pattern with the minimal hosting model using top-level statements in Program.cs.

## Scope Inventory

**Projects affected**: SimpleWebApp (SimpleWebApp/SimpleWebApp.csproj)

**Distinct concerns**:
1. TFM update (1 line change in .csproj)
2. Hosting model migration (rewrite Program.cs, delete Startup.cs)
3. Build validation

**Files to change**:
- `SimpleWebApp/SimpleWebApp.csproj` — change `net8.0` → `net9.0`
- `SimpleWebApp/Program.cs` — replace with minimal hosting top-level statements
- `SimpleWebApp/Startup.cs` — DELETE

**External packages**: None — no NuGet package changes needed.

## Current State

**Program.cs** (current):
```csharp
Host.CreateDefaultBuilder(args).ConfigureWebHostDefaults(webBuilder => webBuilder.UseStartup<Startup>()).Build().Run();
```

**Startup.cs** (current):
- `ConfigureServices`: adds `services.AddControllersWithViews()`
- `Configure`: sets up Developer exception page or `UseExceptionHandler("/Home/Error")` + `UseHsts()`, `UseHttpsRedirection()`, `UseStaticFiles()`, `UseRouting()`, `UseAuthorization()`, endpoints with default MVC route

## Target State

**Program.cs** (new — top-level statements):
```csharp
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllersWithViews();
var app = builder.Build();
if (!app.Environment.IsDevelopment()) { app.UseExceptionHandler("/Home/Error"); app.UseHsts(); }
app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();
app.MapControllerRoute(name: "default", pattern: "{controller=Home}/{action=Index}/{id?}");
app.Run();
```

**Startup.cs**: deleted

## Done When
- `dotnet build SimpleWebApp/SimpleWebApp.csproj` succeeds with zero errors and zero warnings
- `SimpleWebApp.csproj` targets `net9.0`
- `Program.cs` uses `WebApplication.CreateBuilder` with top-level statements
- `Startup.cs` has been deleted
- `dotnet run` starts the application without errors
