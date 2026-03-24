// ──────────────────────────────────────────────────────────────────────────────
// Program.cs – Minimal Hosting Model (.NET 6+ / top-level statements)
//
// Modernized from the legacy IHostBuilder + Startup class pattern.
// ConfigureServices and Configure from the former Startup.cs are merged here.
// ──────────────────────────────────────────────────────────────────────────────

var builder = WebApplication.CreateBuilder(args);

// ── Service registration (replaces Startup.ConfigureServices) ────────────────
builder.Services.AddControllersWithViews();

// ── Build the application ─────────────────────────────────────────────────────
var app = builder.Build();

// ── Middleware pipeline (replaces Startup.Configure) ─────────────────────────
if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
}
else
{
    app.UseExceptionHandler("/Home/Error");
    // HSTS: default is 30 days. Adjust in production – see https://aka.ms/aspnetcore-hsts
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
