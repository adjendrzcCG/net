var builder = WebApplication.CreateBuilder(args);

// ── Service registration ────────────────────────────────────────────────────
builder.Services.AddControllersWithViews();

var app = builder.Build();

// ── HTTP request pipeline ───────────────────────────────────────────────────
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. Adjust for production as needed.
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
