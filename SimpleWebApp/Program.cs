// ----------------------------------------------------------------------------
// Modern .NET 8 minimal hosting model (top-level statements).
// Startup.cs has been intentionally retired — all configuration now lives here.
// ----------------------------------------------------------------------------

var builder = WebApplication.CreateBuilder(args);

// ── Service registration ────────────────────────────────────────────────────
builder.Services.AddControllersWithViews();

var app = builder.Build();

// ── Middleware pipeline ─────────────────────────────────────────────────────
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // HSTS default is 30 days; adjust in production via MaxAge. See https://aka.ms/aspnetcore-hsts
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

// ── Endpoint mapping ────────────────────────────────────────────────────────
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
