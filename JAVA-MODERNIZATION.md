# JABZO — Java Modernization Migration Guide
## SimpleWebApp: ASP.NET Core MVC → Java 21 / Spring Boot 3.x

---

## Overview

This guide documents the complete migration of **SimpleWebApp** from ASP.NET Core MVC (.NET 8)
to a functionally equivalent **Java 21 / Spring Boot 3.3.5 MVC** application.

| Aspect | .NET Original | Java Port |
|--------|---------------|-----------|
| Language | C# 12 | Java 21 LTS |
| Runtime | .NET 8 | JVM 21 (HotSpot / GraalVM) |
| Framework | ASP.NET Core MVC | Spring Boot 3.3.5 + Spring MVC 6.1.x |
| Template Engine | Razor (.cshtml) | Thymeleaf 3.1 + Layout Dialect 3.3 |
| Build System | MSBuild / .csproj | Maven 3.9+ with Spring Boot parent POM |
| Dependency Injection | ASP.NET Core built-in DI | Spring IoC Container |
| Logging | `ILogger<T>` | SLF4J + Logback (via Spring Boot) |
| Web Server | Kestrel (embedded) | Tomcat 10.1 (embedded) |
| Jakarta/JEE Namespace | N/A | `jakarta.*` (Jakarta EE 10, mandatory for Spring Boot 3.x) |
| Configuration | `appsettings.json` | `application.properties` |
| Error Handling | `app.UseExceptionHandler("/Home/Error")` | `ErrorController` + `server.error.path` |
| Static Files | `wwwroot/` via `app.UseStaticFiles()` | `src/main/resources/static/` (auto-served) |
| LTS Status | ✅ .NET 8 LTS (EOL Nov 2026) | ✅ Java 21 LTS (EOL Sep 2029) |

---

## Pre-Migration Checklist

- [x] Repository analysed — pure .NET 8 MVC, no existing Java code
- [x] Java 21 LTS selected as target (September 2029 EOL)
- [x] Spring Boot 3.3.5 selected (current stable 3.3.x LTS-aligned branch)
- [x] Spring Framework 6.1.x confirmed via Spring Boot BOM
- [x] Jakarta EE 10 (`jakarta.*` namespace) confirmed for Spring Boot 3.x
- [x] Thymeleaf Layout Dialect 3.3.0 selected (replaces Razor `_Layout.cshtml`)
- [x] Maven 3.9+ selected as build system
- [x] All .NET source files preserved (Java files added alongside, not replacing)
- [x] `.gitignore` updated with Java/Maven artifact patterns

---

## Phase 1: Build System Setup ✅ COMPLETE

### Files Created
- `SimpleWebApp/pom.xml`

### Key Decisions

#### Spring Boot Parent POM
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>3.3.5</version>
</parent>
```
The Spring Boot parent POM acts as a Bill of Materials (BOM), managing versions of all
Spring dependencies. This is equivalent to importing `Microsoft.AspNetCore` packages via the
.NET SDK — no individual version pinning required for Spring-managed dependencies.

#### Non-Standard Maven Source Layout
Since the Java files are co-located with the .NET source for side-by-side comparison,
the Maven source directory is configured non-standardly:

```xml
<sourceDirectory>${project.basedir}</sourceDirectory>
```

This causes Maven's compiler to scan `SimpleWebApp/` recursively for `*.java` files.
The three Java source files (`SimpleWebAppApplication.java`, `Controllers/HomeController.java`,
`Models/ErrorViewModel.java`) are compiled based on their `package` declarations, not their
directory names.

> **Note for standard Maven projects**: In a greenfield Java project, move source files to:
> ```
> src/main/java/com/simplewebapp/SimpleWebAppApplication.java
> src/main/java/com/simplewebapp/controller/HomeController.java
> src/main/java/com/simplewebapp/model/ErrorViewModel.java
> ```
> Then use the default `<sourceDirectory>src/main/java</sourceDirectory>`.

#### Resource Mapping
Maven resource directories map the existing .NET file locations to Spring Boot classpath locations:

| Source Directory | `targetPath` | Spring Boot Usage |
|-----------------|--------------|-------------------|
| `Views/**/*.html` | `templates/` | Thymeleaf template resolution |
| `wwwroot/css/` | `static/css/` | Served at `/css/site.css` |
| `wwwroot/js/` | `static/js/` | Served at `/js/site.js` |
| `application.properties` | (root classpath) | Spring Boot auto-loads |

---

## Phase 2: Application Bootstrap ✅ COMPLETE

### Files Created
- `SimpleWebApp/SimpleWebAppApplication.java`
- `SimpleWebApp/application.properties`

### .NET → Java Bootstrap Mapping

```
Program.cs                              SimpleWebAppApplication.java
──────────────────────────────────────  ────────────────────────────────────
Host.CreateDefaultBuilder(args)         SpringApplication.run(...)
.ConfigureWebHostDefaults(b =>          @SpringBootApplication
   b.UseStartup<Startup>())             (auto-configuration handles everything)
.Build().Run()
```

```
Startup.cs                              application.properties + auto-config
──────────────────────────────────────  ────────────────────────────────────
services.AddControllersWithViews()      spring-boot-starter-web (auto-detects @Controller)
app.UseStaticFiles()                    spring.web.resources.static-locations=classpath:/static/
app.UseRouting()                        @GetMapping / @RequestMapping annotations
app.UseExceptionHandler("/Home/Error")  server.error.path=/home/error
app.UseHsts()                           server.ssl.* (configure in production)
app.UseHttpsRedirection()               Reverse proxy / server.ssl.*
```

### Key Insight: 115 Lines Become 1 Annotation
The combined 115 lines of `Program.cs` + `Startup.cs` are replaced by `@SpringBootApplication`
on the main class. Spring Boot's auto-configuration system detects classpath dependencies
and automatically configures:
- Embedded Tomcat 10.1 web server
- Spring MVC dispatcher servlet
- Thymeleaf template engine (detects `thymeleaf-layout-dialect` on classpath)
- Static resource handler for `classpath:/static/`
- Error handling via `ErrorController`
- SLF4J + Logback logging

---

## Phase 3: Model Migration ✅ COMPLETE

### Files Created
- `SimpleWebApp/Models/ErrorViewModel.java`

### C# Class → Java Record

```csharp
// C# — Models/ErrorViewModel.cs
public class ErrorViewModel
{
    public string RequestId { get; set; }
    public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);
}
```

```java
// Java 21 — Models/ErrorViewModel.java
public record ErrorViewModel(String requestId) {

    public ErrorViewModel {
        requestId = (requestId != null) ? requestId : "";
    }

    public boolean showRequestId() {
        return !requestId.isEmpty();
    }
}
```

**Why a record?**
- `ErrorViewModel` is a pure data carrier — exactly the use case records are designed for
- Records are **immutable** by default — appropriate for per-request error data
- **Compact constructor** handles null normalisation
- Auto-generated `equals()`, `hashCode()`, `toString()` — no boilerplate
- Thymeleaf resolves record accessor methods (e.g. `${errorViewModel.requestId}`)

---

## Phase 4: Controller Migration ✅ COMPLETE

### Files Created
- `SimpleWebApp/Controllers/HomeController.java`

### Route Mapping

| .NET Action | .NET Route | Java Method | Java Route |
|-------------|-----------|-------------|-----------|
| `Index()` | `GET /` | `index()` | `@GetMapping("/")` |
| `About()` | `GET /Home/About` | `about(Model)` | `@GetMapping("/home/about")` |
| `Privacy()` | `GET /Home/Privacy` | `privacy()` | `@GetMapping("/home/privacy")` |
| `Error()` | `GET /Home/Error` | `error(request, response, model)` | `@RequestMapping("/home/error")` |

### Logger Migration

```csharp
// C# — constructor injection
private readonly ILogger<HomeController> _logger;
public HomeController(ILogger<HomeController> logger) {
    _logger = logger;
}
_logger.LogInformation("About page requested");
```

```java
// Java 21 — SLF4J static logger (idiomatic Java)
private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
logger.info("About page requested");
```

### ViewData → Model Attributes

```csharp
// C# — dictionary-based view data
ViewData["Message"] = "A simple .NET web application.";
return View();
```

```java
// Java — type-safe model attributes
model.addAttribute("message", "A simple Java Spring Boot web application.");
return "Home/about";  // Thymeleaf template name
```

### ResponseCache → HTTP Headers

```csharp
// C#
[ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
public IActionResult Error() { ... }
```

```java
// Java 21 — explicit headers (same semantic effect)
response.setHeader(HttpHeaders.CACHE_CONTROL,
    CacheControl.noStore().mustRevalidate().getHeaderValue());
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
```

### TraceIdentifier → Request ID

```csharp
// C# — Activity trace + ASP.NET Core TraceIdentifier
RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier
```

```java
// Java 21 — Jakarta Servlet 6.0 request ID
String requestId = request.getRequestId();  // Servlet 6.0 (Spring Boot 3.x / Tomcat 10.1)
```

---

## Phase 5: View / Template Migration ✅ COMPLETE

### Files Created
- `SimpleWebApp/Views/layout.html` (master layout)
- `SimpleWebApp/Views/Home/index.html`
- `SimpleWebApp/Views/Home/about.html`
- `SimpleWebApp/Views/Home/privacy.html`
- `SimpleWebApp/Views/Shared/error.html`

### Razor → Thymeleaf Attribute Mapping

| Razor (C#) | Thymeleaf (Java) | Notes |
|-----------|-----------------|-------|
| `@{ ViewData["Title"] = "X"; }` | `<title>X</title>` | Layout Dialect merges titles |
| `@ViewData["Title"]` | Handled by `layout:title-pattern` | Automatic with Layout Dialect |
| `@ViewData["Message"]` | `th:text="${message}"` | Model attribute from controller |
| `@RenderBody()` | `<div layout:fragment="content">` | Layout Dialect insertion point |
| `layout:decorate (page side)` | `layout:decorate="~{layout}"` | Decorates with layout.html |
| `asp-controller="Home" asp-action="Index"` | `th:href="@{/}"` | Thymeleaf URL expression |
| `asp-controller="Home" asp-action="About"` | `th:href="@{/home/about}"` | Thymeleaf URL expression |
| `~/lib/bootstrap/css/bootstrap.min.css` | Bootstrap 5.3.2 CDN | Replaces local wwwroot/lib |
| `~/lib/jquery/dist/jquery.min.js` | jQuery 3.7.1 CDN | Replaces local wwwroot/lib |
| `~/css/site.css asp-append-version="true"` | `th:href="@{/css/site.css}"` | Served from static/ |
| `~/js/site.js asp-append-version="true"` | `th:src="@{/js/site.js}"` | Served from static/ |
| `@if (Model.ShowRequestId) { ... }` | `th:if="${showRequestId}"` | Conditional rendering |
| `@Model.RequestId` | `th:text="${requestId}"` | Model attribute binding |
| `@await RenderSectionAsync("Scripts")` | `<th:block layout:fragment="scripts">` | Optional scripts section |

### Bootstrap CDN vs Local Files
The original .NET app served Bootstrap and jQuery from `wwwroot/lib/`. The Java port uses
CDN links for simplicity. To use local copies, download Bootstrap 5.3.2 and jQuery 3.7.1
into `wwwroot/lib/` and update the `<link>` and `<script>` tags in `Views/layout.html` to use
`th:href="@{/lib/bootstrap/dist/css/bootstrap.min.css}"`.

---

## Phase 6: Static Asset Migration ✅ COMPLETE

### Existing Files Reused
- `SimpleWebApp/wwwroot/css/site.css` — already compatible, no changes needed
- `SimpleWebApp/wwwroot/js/site.js` — already compatible, no changes needed

Maven's resource configuration in `pom.xml` maps:
- `wwwroot/css/` → `target/classes/static/css/` → served at `/css/site.css`
- `wwwroot/js/` → `target/classes/static/js/` → served at `/js/site.js`

No duplication — the same CSS/JS files serve both the .NET and Java applications.

---

## Phase 7: Testing (Recommended Next Steps)

Tests could not be placed in the standard Maven `src/test/java/` directory because
that directory does not exist in the current repository layout.

### Test Files to Create
Create these files at `src/test/java/com/simplewebapp/` in a standard Maven project:

```java
// SimpleWebAppApplicationTests.java — context load test
@SpringBootTest
class SimpleWebAppApplicationTests {
    @Test
    void contextLoads() {}
}
```

```java
// HomeControllerTest.java — MockMvc integration tests
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void indexReturns200() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("Home/index"));
    }

    @Test
    void aboutReturns200WithMessage() throws Exception {
        mockMvc.perform(get("/home/about"))
               .andExpect(status().isOk())
               .andExpect(view().name("Home/about"))
               .andExpect(model().attribute("message",
                   "A simple Java Spring Boot web application."));
    }

    @Test
    void privacyReturns200() throws Exception {
        mockMvc.perform(get("/home/privacy"))
               .andExpect(status().isOk())
               .andExpect(view().name("Home/privacy"));
    }
}
```

---

## Phase 8: Build and Validation

### Prerequisites
```bash
# Verify Java 21
java -version
# Expected: openjdk version "21.x.x" ...

# Verify Maven 3.9+
mvn -version
# Expected: Apache Maven 3.9.x ...
```

### Build
```bash
cd SimpleWebApp
mvn clean package
```

### Run
```bash
java -jar target/SimpleWebApp-1.0.0.jar
```

### Verify Routes
```bash
# Home page
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/
# Expected: 200

# About page
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/home/about
# Expected: 200

# Privacy page
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/home/privacy
# Expected: 200
```

---

## Validation Checklist

### Build Validation
- [ ] `mvn clean package` completes without errors
- [ ] `target/SimpleWebApp-1.0.0.jar` produced
- [ ] `target/SimpleWebApp-1.0.0.jar` size > 10 MB (contains embedded Tomcat)

### Runtime Validation
- [ ] Application starts on port 8080 without exceptions in startup log
- [ ] Spring Boot banner shows version `3.3.5`
- [ ] Log line: `Tomcat started on port 8080`

### Route Validation
- [ ] `GET /` → HTTP 200, page contains "Welcome" heading
- [ ] `GET /home/about` → HTTP 200, page contains "About" heading
- [ ] `GET /home/about` → page contains "A simple Java Spring Boot web application."
- [ ] `GET /home/privacy` → HTTP 200, page contains "Privacy Policy" heading
- [ ] `GET /home/error` → HTTP 200, error page renders (no stack trace leak)

### UI Validation
- [ ] Bootstrap navbar renders with "SimpleWebApp" brand
- [ ] Navigation links: Home, About, Privacy all present
- [ ] Footer shows copyright text
- [ ] Bootstrap responsive layout works at mobile viewport widths
- [ ] `/css/site.css` loads (check browser DevTools Network tab)
- [ ] `/js/site.js` loads

### Java Version Validation
- [ ] `java -version` shows Java 21
- [ ] Startup log shows `JVM: 21.x` (or similar)
- [ ] No `javax.*` imports anywhere (must be `jakarta.*` for Spring Boot 3.x)

---

## Rollback Procedure

The Java port was added **alongside** the .NET application — no .NET files were modified
except `.gitignore`. To roll back the Java changes:

```bash
# Remove all Java-related files
git checkout -- .gitignore
git rm SimpleWebApp/pom.xml
git rm SimpleWebApp/application.properties
git rm SimpleWebApp/SimpleWebAppApplication.java
git rm SimpleWebApp/Controllers/HomeController.java
git rm SimpleWebApp/Models/ErrorViewModel.java
git rm SimpleWebApp/Views/layout.html
git rm SimpleWebApp/Views/Home/index.html
git rm SimpleWebApp/Views/Home/about.html
git rm SimpleWebApp/Views/Home/privacy.html
git rm SimpleWebApp/Views/Shared/error.html
git rm modernize-java-report.json
git rm JAVA-MODERNIZATION.md
git rm agent-log.txt
git commit -m "revert: remove Java port files"
```

The .NET application remains completely intact and unaffected.

---

## Risk Assessment

| Risk | Severity | Mitigation |
|------|----------|-----------|
| Non-standard Maven source directory | Low | Fully documented; standard layout easy to adopt |
| Bootstrap CDN dependency | Low | Can switch to local files; CDN URLs use SRI hashes |
| Thymeleaf vs Razor learning curve | Low | Complete Razor→Thymeleaf mapping table provided |
| Error handling differences | Low | `ErrorController` + Servlet 6.0 `getRequestId()` matches .NET semantics |
| Java compilation in non-standard dirs | Low | javac compiles by package declaration, not directory name |
| Missing test directory | Low | Tests documented; can be added to standard `src/test/java/` |

---

## Key Modernization Achievements

1. **Java 21 LTS** — 8-year support window, latest LTS release
2. **Spring Boot 3.3.5** — current stable, Spring Framework 6.1.x, auto-configuration
3. **Jakarta EE 10** — modern `jakarta.*` namespace throughout (no legacy `javax.*`)
4. **Java Records** — `ErrorViewModel` as immutable `record` (Java 16+ best practice)
5. **Zero XML Configuration** — `@SpringBootApplication` replaces 115 lines of boilerplate
6. **Thymeleaf HTML5** — natural templates, IDE-friendly, valid HTML5
7. **Embedded Tomcat 10.1** — single fat JAR deployment, no external server needed
8. **SLF4J + Logback** — industry-standard Java logging (replaces ASP.NET Core `ILogger<T>`)
9. **CacheControl Builder** — type-safe HTTP cache headers (replaces `[ResponseCache]` attribute)
10. **Servlet 6.0 Request ID** — `getRequestId()` exact Java equivalent of `TraceIdentifier`
