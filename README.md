# SimpleWebApp

A web application available in two equivalent implementations:

## 1. ASP.NET Core MVC (.NET 8) — `SimpleWebApp/`

Original implementation using C#, ASP.NET Core MVC, and Razor views.

**Build & Run:**
```bash
cd SimpleWebApp
dotnet run
# Access: https://localhost:5001
```

**Stack:** .NET 8 LTS · ASP.NET Core MVC · Razor · Kestrel · MSBuild

---

## 2. Spring Boot MVC (Java 21) — `SimpleWebApp/` (Java files)

Java 21 / Spring Boot 3.3.5 equivalent created by the **JABZO Java Modernization** task.
Functionally identical to the .NET version — same routes, same page content, same layout.

**Build & Run:**
```bash
cd SimpleWebApp
mvn clean package
java -jar target/SimpleWebApp-1.0.0.jar
# Access: http://localhost:8080
```

**Routes:**

| URL | Page |
|-----|------|
| `GET /` | Home / Welcome |
| `GET /home/about` | About |
| `GET /home/privacy` | Privacy Policy |
| `ANY /home/error` | Error page |

**Stack:** Java 21 LTS · Spring Boot 3.3.5 · Spring MVC 6.1.x · Thymeleaf 3.1 · Tomcat 10.1 · Maven 3.9+ · Jakarta EE 10

**Java files added:**

| Java File | .NET Equivalent | Notes |
|-----------|----------------|-------|
| `SimpleWebApp/pom.xml` | `SimpleWebApp.csproj` | Maven build — Spring Boot 3.3.5 parent |
| `SimpleWebApp/application.properties` | `appsettings.json` | Spring Boot configuration |
| `SimpleWebApp/SimpleWebAppApplication.java` | `Program.cs` + `Startup.cs` | `@SpringBootApplication` entry point |
| `SimpleWebApp/Controllers/HomeController.java` | `Controllers/HomeController.cs` | Spring `@Controller` with SLF4J logging |
| `SimpleWebApp/Models/ErrorViewModel.java` | `Models/ErrorViewModel.cs` | Java 21 `record` (immutable data carrier) |
| `SimpleWebApp/Views/layout.html` | `Views/Shared/_Layout.cshtml` | Thymeleaf Layout Dialect master template |
| `SimpleWebApp/Views/Home/index.html` | `Views/Home/Index.cshtml` | Thymeleaf home page |
| `SimpleWebApp/Views/Home/about.html` | `Views/Home/About.cshtml` | Thymeleaf about page |
| `SimpleWebApp/Views/Home/privacy.html` | `Views/Home/Privacy.cshtml` | Thymeleaf privacy page |
| `SimpleWebApp/Views/Shared/error.html` | `Views/Shared/Error.cshtml` | Thymeleaf error page |

---

## Java Modernization Analysis

- **Report:** [`modernize-java-report.json`](modernize-java-report.json) — Full JSON analysis
- **Guide:** [`JAVA-MODERNIZATION.md`](JAVA-MODERNIZATION.md) — Step-by-step migration guide
- **Agent Log:** [`agent-log.txt`](agent-log.txt)