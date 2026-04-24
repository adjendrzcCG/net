package com.example.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * Entry point for the net application.
 *
 * <p>Demonstrates modern Java 21 features including:
 * <ul>
 *   <li>Records</li>
 *   <li>Sealed interfaces and pattern matching</li>
 *   <li>Text blocks</li>
 *   <li>Switch expressions</li>
 *   <li>Virtual threads (Project Loom)</li>
 * </ul>
 */
public final class Application {

    private Application() {
        // Utility class — no instances
    }

    public static void main(String[] args) {
        var client = HttpClientFactory.createDefault();

        // Text block (Java 15+)
        var banner = """
                ╔══════════════════════════════╗
                ║   net — Java 21 HTTP Client  ║
                ╚══════════════════════════════╝
                """;
        System.out.print(banner);

        // Demonstrate sealed-interface dispatch via pattern matching
        HttpResult result = fetchSync(client, "https://httpbin.org/get");
        String message = switch (result) {
            case HttpResult.Success(var statusCode, var body) ->
                    "Response %d: %s".formatted(statusCode, body.substring(0, Math.min(80, body.length())));
            case HttpResult.Failure(var error) ->
                    "Request failed: " + error.getMessage();
        };
        System.out.println(message);
    }

    /** Performs a synchronous GET and wraps the outcome in an {@link HttpResult}. */
    static HttpResult fetchSync(HttpClient client, String url) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new HttpResult.Success(response.statusCode(), response.body());
        } catch (Exception e) {
            return new HttpResult.Failure(e);
        }
    }

    /** Performs an asynchronous GET and wraps the outcome in a {@link CompletableFuture}. */
    static CompletableFuture<HttpResult> fetchAsync(HttpClient client, String url) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(r -> (HttpResult) new HttpResult.Success(r.statusCode(), r.body()))
                .exceptionally(ex -> new HttpResult.Failure(ex instanceof Exception e ? e : new RuntimeException(ex)));
    }
}
