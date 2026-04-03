# 01-upgrade-simplewebapp: Upgrade SimpleWebApp to net9.0 with minimal hosting model

## Objective

Upgrade `SimpleWebApp` from `net8.0` to `net9.0`, migrate to the minimal hosting model, and enable nullable reference types.

## Scope Inventory

**Projects affected**: `SimpleWebApp/SimpleWebApp.csproj`

**Files to modify**:
- `SimpleWebApp.csproj` — change TFM to net9.0, add `<ImplicitUsings>enable</ImplicitUsings>` and `<Nullable>enable</Nullable>`
- `Program.cs` — full replacement: top-level statements with `WebApplication.CreateBuilder`
- `Controllers/HomeController.cs` — may need `?` on ILogger field or nullable annotations
- `Models/ErrorViewModel.cs` — `RequestId` is `string?` if nullable enabled (currently `string`)

**Files to delete**:
- `Startup.cs` — replaced by new Program.cs

**No NuGet packages to update** — project uses only SDK-provided packages.

## Research Findings

**Current `Startup.cs` `ConfigureServices`**:
```csharp
services.AddControllersWithViews();
```

**Current `Startup.cs` `Configure`**:
```csharp
if (env.IsDevelopment()) app.UseDeveloperExceptionPage();
else { app.UseExceptionHandler("/Home/Error"); app.UseHsts(); }
app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();
app.UseEndpoints(e => e.MapControllerRoute("default", "{controller=Home}/{action=Index}/{id?}"));
```

**Minimal hosting model equivalent**:
```csharp
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllersWithViews();
var app = builder.Build();
if (!app.Environment.IsDevelopment()) { app.UseExceptionHandler("/Home/Error"); app.UseHsts(); }
app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseAuthorization();
app.MapControllerRoute(name: "default", pattern: "{controller=Home}/{action=Index}/{id?}");
app.Run();
```

Note: `app.UseRouting()` and `app.UseEndpoints()` are not needed in the minimal hosting model — routing is implicit.

**Nullable annotations needed**:
- `ErrorViewModel.RequestId` → `string?` (used with null check in view)
- `HomeController._logger` → already `ILogger<HomeController>` (non-nullable field set by constructor DI = fine)

## Done When

- `SimpleWebApp.csproj` targets `net9.0` with `<Nullable>enable</Nullable>` and `<ImplicitUsings>enable</ImplicitUsings>`
- `Program.cs` uses top-level statements + `WebApplication.CreateBuilder`
- `Startup.cs` is deleted
- `dotnet build` reports 0 errors and 0 warnings
