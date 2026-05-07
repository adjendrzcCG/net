package com.simplewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SimpleWebApp — Spring Boot 3.x Application Entry Point.
 *
 * <p>This class is the Java/Spring Boot equivalent of the combined
 * {@code Program.cs} and {@code Startup.cs} files in the ASP.NET Core version.
 *
 * <h2>.NET → Java Migration Summary</h2>
 * <pre>
 *   Program.cs                         SimpleWebAppApplication.java
 *   ─────────────────────────────────  ────────────────────────────────────────
 *   Host.CreateDefaultBuilder(args)    SpringApplication.run(...)
 *   .ConfigureWebHostDefaults(...)     @SpringBootApplication (auto-config)
 *   .Build().Run()
 *
 *   Startup.cs
 *   ─────────────────────────────────  ────────────────────────────────────────
 *   services.AddControllersWithViews() @SpringBootApplication (auto-detects MVC)
 *   app.UseStaticFiles()               spring.web.resources.* in application.properties
 *   app.UseRouting()                   @RequestMapping / @GetMapping annotations
 *   app.UseAuthorization()             Spring Security (not added — matches .NET baseline)
 *   app.UseExceptionHandler(...)       server.error.path in application.properties
 *   endpoints.MapControllerRoute(...)  @GetMapping on individual controller methods
 * </pre>
 *
 * <h2>Modern Java 21 Features Used in This Project</h2>
 * <ul>
 *   <li>{@code record} — {@link com.simplewebapp.model.ErrorViewModel} data carrier</li>
 *   <li>Jakarta EE 10 — {@code jakarta.*} servlet API (not legacy {@code javax.*})</li>
 *   <li>SLF4J static logger — replaces ASP.NET Core {@code ILogger<T>} injection</li>
 *   <li>Spring Boot 3.x auto-configuration — zero-XML bootstrap</li>
 * </ul>
 *
 * <h2>Running the Application</h2>
 * <pre>
 *   # Build
 *   cd SimpleWebApp
 *   mvn clean package
 *
 *   # Run
 *   java -jar target/SimpleWebApp-1.0.0.jar
 *
 *   # Access
 *   http://localhost:8080/
 *   http://localhost:8080/home/about
 *   http://localhost:8080/home/privacy
 * </pre>
 *
 * @see <a href="https://spring.io/projects/spring-boot">Spring Boot Reference</a>
 * @since Java 21
 */
@SpringBootApplication
public class SimpleWebAppApplication {

    /**
     * Application entry point.
     *
     * <p>Equivalent to {@code Program.Main(string[] args)} in .NET.
     * Spring Boot's {@link SpringApplication#run} bootstraps the embedded
     * Tomcat 10.1 server, loads all auto-configuration, registers beans,
     * and starts accepting HTTP requests on port 8080.
     *
     * @param args command-line arguments (e.g. {@code --server.port=9090})
     */
    public static void main(String[] args) {
        SpringApplication.run(SimpleWebAppApplication.class, args);
    }
}
