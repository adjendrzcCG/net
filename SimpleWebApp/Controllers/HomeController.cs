using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using SimpleWebApp.Models;

// Microsoft.Extensions.Logging is supplied automatically by the
// Microsoft.NET.Sdk.Web implicit using directives.
// Microsoft.AspNetCore.Mvc is NOT implicit and must be listed explicitly.

namespace SimpleWebApp.Controllers;

public class HomeController : Controller
{
    private readonly ILogger<HomeController> _logger;

    public HomeController(ILogger<HomeController> logger)
    {
        _logger = logger;
    }

    public IActionResult Index()
    {
        return View();
    }

    public IActionResult About()
    {
        ViewData["Message"] = "A simple .NET web application.";
        return View();
    }

    public IActionResult Privacy()
    {
        return View();
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error()
    {
        return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
    }
}
