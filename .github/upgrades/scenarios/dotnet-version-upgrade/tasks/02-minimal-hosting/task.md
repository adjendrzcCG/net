# 02-minimal-hosting: Migrate to Minimal Hosting Model

## Objective
Replace the old `CreateHostBuilder`/`Startup` pattern with ASP.NET Core minimal hosting (top-level statements). Merge all content from `Startup.cs` into `Program.cs`, then delete `Startup.cs`.

## Current State

### Program.cs (current)
```csharp
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;

namespace SimpleWebApp
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                });
    }
}
```

### Startup.cs (to be merged & deleted)
- `ConfigureServices`: registers `services.AddControllersWithViews()`
- `Configure`: sets up pipeline:
  - Dev: `UseDeveloperExceptionPage()`
  - Prod: `UseExceptionHandler("/Home/Error")` + `UseHsts()`
  - `UseHttpsRedirection()`, `UseStaticFiles()`, `UseRouting()`, `UseAuthorization()`
  - Endpoint: `MapControllerRoute` (default route)

## Target Pattern (minimal hosting top-level statements)
```csharp
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllersWithViews();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
```

Note: `UseDeveloperExceptionPage()` is **not** needed in .NET 6+ minimal hosting — it's enabled automatically in development mode. The condition flips: we call `UseExceptionHandler` only when NOT in development.

## Files to Modify
- `Program.cs` — full rewrite to top-level statements
- `Startup.cs` — delete

## Done When
- `Program.cs` uses `WebApplication.CreateBuilder` top-level statements
- `Startup.cs` is deleted
- Project builds with 0 errors (1 nullable warning acceptable — fixed in task 03)
