package com.simplewebapp.controller;

import com.simplewebapp.model.ErrorViewModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController — Spring MVC {@code @Controller} equivalent of the
 * ASP.NET Core {@code HomeController.cs}.
 *
 * <h2>.NET → Java Migration Mapping</h2>
 * <pre>
 *   ASP.NET Core                           Spring Boot 3.x
 *   ─────────────────────────────────────  ──────────────────────────────────────
 *   [Controller] class HomeController      @Controller HomeController
 *   ILogger&lt;HomeController&gt; (DI)          Logger (SLF4J static field)
 *   public IActionResult Index()           @GetMapping("/")
 *   public IActionResult About()           @GetMapping("/home/about")
 *   ViewData["Message"] = "..."            model.addAttribute("message", "...")
 *   public IActionResult Privacy()         @GetMapping("/home/privacy")
 *   [ResponseCache(NoStore=true)] Error()  @RequestMapping("/home/error") + headers
 *   return View()                          return "Home/index" (Thymeleaf template)
 *   return View(model)                     model.addAttribute(...) + return view name
 * </pre>
 *
 * <h2>Error Handling</h2>
 * <p>This controller implements Spring Boot's {@link ErrorController} interface,
 * making it the designated error handler mapped to {@code /home/error}
 * (configured via {@code server.error.path} in {@code application.properties}).
 * This replicates the {@code app.UseExceptionHandler("/Home/Error")} pipeline
 * from {@code Startup.cs}.
 *
 * <h2>Logging</h2>
 * <p>Uses SLF4J static logger — the idiomatic Java approach replacing
 * ASP.NET Core's constructor-injected {@code ILogger&lt;HomeController&gt;}.
 *
 * <h2>Jakarta EE 10</h2>
 * <p>All servlet imports use {@code jakarta.*} (not legacy {@code javax.*}),
 * as required by Spring Boot 3.x / Jakarta EE 10.
 *
 * @see ErrorViewModel
 * @see <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html">@Controller</a>
 */
@Controller
public class HomeController implements ErrorController {

    /**
     * SLF4J logger — equivalent to ASP.NET Core's
     * {@code private readonly ILogger<HomeController> _logger}.
     *
     * <p>In .NET, the logger was injected via constructor DI:
     * {@code public HomeController(ILogger<HomeController> logger)}.
     * In idiomatic Java with SLF4J, a static final logger is preferred
     * because loggers are inherently stateless and thread-safe.
     */
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    // ─────────────────────────────────────────────────────────────────────────
    // Route Handlers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Index action — displays the home/welcome page.
     *
     * <p>.NET equivalent:
     * <pre>
     *   public IActionResult Index() =&gt; View();
     * </pre>
     *
     * <p>Route: {@code GET /}
     * Template: {@code classpath:/templates/Home/index.html}
     *
     * @return Thymeleaf template name {@code "Home/index"}
     */
    @GetMapping("/")
    public String index() {
        logger.debug("Index page requested");
        return "Home/index";
    }

    /**
     * About action — displays application information.
     *
     * <p>.NET equivalent:
     * <pre>
     *   public IActionResult About() {
     *       ViewData["Message"] = "A simple .NET web application.";
     *       return View();
     *   }
     * </pre>
     *
     * <p>Spring MVC uses {@link Model#addAttribute(String, Object)} as the
     * equivalent of ASP.NET Core's {@code ViewData} dictionary. The Thymeleaf
     * template accesses values via {@code th:text="${message}"}.
     *
     * <p>Route: {@code GET /home/about}
     * Template: {@code classpath:/templates/Home/about.html}
     *
     * @param model Spring MVC model (equivalent to ASP.NET Core's ViewData)
     * @return Thymeleaf template name {@code "Home/about"}
     */
    @GetMapping("/home/about")
    public String about(Model model) {
        logger.info("About page requested");
        model.addAttribute("message", "A simple Java Spring Boot web application.");
        return "Home/about";
    }

    /**
     * Privacy action — displays the privacy policy.
     *
     * <p>.NET equivalent:
     * <pre>
     *   public IActionResult Privacy() =&gt; View();
     * </pre>
     *
     * <p>Route: {@code GET /home/privacy}
     * Template: {@code classpath:/templates/Home/privacy.html}
     *
     * @return Thymeleaf template name {@code "Home/privacy"}
     */
    @GetMapping("/home/privacy")
    public String privacy() {
        logger.debug("Privacy page requested");
        return "Home/privacy";
    }

    /**
     * Error action — handles application errors and displays the error page.
     *
     * <p>.NET equivalent:
     * <pre>
     *   [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
     *   public IActionResult Error() {
     *       return View(new ErrorViewModel {
     *           RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier
     *       });
     *   }
     * </pre>
     *
     * <p>This method implements {@link ErrorController}, registering it as the
     * canonical error handler for Spring Boot (mapped to {@code /home/error}
     * via {@code server.error.path} in {@code application.properties}).
     *
     * <h3>No-Cache Headers</h3>
     * <p>The {@code [ResponseCache(Duration=0, Location=None, NoStore=true)]}
     * attribute is replicated by setting explicit {@code Cache-Control},
     * {@code Pragma}, and {@code Expires} HTTP response headers.
     *
     * <h3>Request ID</h3>
     * <p>The request ID mirrors {@code Activity.Current?.Id ?? HttpContext.TraceIdentifier}.
     * In Jakarta Servlet 6.0 (Spring Boot 3.x / Tomcat 10.1), the request ID
     * is available via {@link HttpServletRequest#getRequestId()} — a direct
     * equivalent of ASP.NET Core's {@code TraceIdentifier}.
     *
     * <p>Route: {@code ANY /home/error} (handles both GET and POST error redirects)
     * Template: {@code classpath:/templates/Shared/error.html}
     *
     * @param request  the HTTP servlet request (Jakarta Servlet 6.0)
     * @param response the HTTP servlet response (for setting no-cache headers)
     * @param model    Spring MVC model for passing data to the template
     * @return Thymeleaf template name {@code "Shared/error"}
     */
    @RequestMapping("/home/error")
    public String error(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {

        // ── No-cache headers ──────────────────────────────────────────────────
        // Equivalent to [ResponseCache(Duration=0, Location=None, NoStore=true)]
        response.setHeader(HttpHeaders.CACHE_CONTROL,
                CacheControl.noStore().mustRevalidate().getHeaderValue());
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // ── Request ID ────────────────────────────────────────────────────────
        // Mirrors: Activity.Current?.Id ?? HttpContext.TraceIdentifier
        //
        // First try the error request URI attribute (set when forwarded from error handler).
        // Fall back to Servlet 6.0's getRequestId() — the Jakarta EE 10 equivalent
        // of ASP.NET Core's HttpContext.TraceIdentifier.
        Object errorUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String requestId = (errorUri != null)
                ? errorUri.toString()
                : request.getRequestId();  // Servlet 6.0 (Jakarta EE 10)

        // ── Build ViewModel ───────────────────────────────────────────────────
        // Uses Java 16+ record — equivalent of `new ErrorViewModel { RequestId = ... }`
        ErrorViewModel errorViewModel = new ErrorViewModel(requestId);

        model.addAttribute("errorViewModel", errorViewModel);
        model.addAttribute("requestId", errorViewModel.requestId());
        model.addAttribute("showRequestId", errorViewModel.showRequestId());

        logger.warn("Error page rendered — requestId={}", requestId);

        return "Shared/error";
    }
}
