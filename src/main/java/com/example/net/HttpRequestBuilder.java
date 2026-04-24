package com.example.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A fluent, immutable HTTP request builder that produces {@link HttpResult}
 * instances via a pre-configured {@link HttpClient}.
 *
 * <p>All state is carried in a {@code record}, making instances thread-safe
 * and easy to copy-and-modify with {@code with*} helpers.
 */
public record HttpRequestBuilder(
        HttpClient client,
        String baseUrl,
        Map<String, String> defaultHeaders,
        Duration timeout
) {

    /** Creates a builder with default headers and a 15-second timeout. */
    public HttpRequestBuilder {
        Objects.requireNonNull(client, "client must not be null");
        Objects.requireNonNull(baseUrl, "baseUrl must not be null");
        defaultHeaders = Map.copyOf(Objects.requireNonNullElseGet(defaultHeaders, Map::of));
        timeout = Objects.requireNonNullElse(timeout, Duration.ofSeconds(15));
    }

    // ── Factory helpers ─────────────────────────────────────────────────────

    /** Creates a builder backed by the default {@link HttpClientFactory} client. */
    public static HttpRequestBuilder of(String baseUrl) {
        return new HttpRequestBuilder(HttpClientFactory.createDefault(), baseUrl, Map.of(), null);
    }

    // ── Fluent copies ───────────────────────────────────────────────────────

    /** Returns a new builder with the given additional header. */
    public HttpRequestBuilder withHeader(String name, String value) {
        var merged = new java.util.HashMap<>(defaultHeaders);
        merged.put(name, value);
        return new HttpRequestBuilder(client, baseUrl, Map.copyOf(merged), timeout);
    }

    /** Returns a new builder with the given timeout. */
    public HttpRequestBuilder withTimeout(Duration newTimeout) {
        return new HttpRequestBuilder(client, baseUrl, defaultHeaders, newTimeout);
    }

    // ── HTTP verbs ──────────────────────────────────────────────────────────

    /**
     * Performs a synchronous GET request.
     *
     * @param path the path to append to {@link #baseUrl()}
     * @return an {@link HttpResult} representing success or failure
     */
    public HttpResult get(String path) {
        return send(buildRequest(path, HttpRequest.BodyPublishers.noBody(), "GET"));
    }

    /**
     * Performs a synchronous POST request with a string body.
     *
     * @param path the path to append to {@link #baseUrl()}
     * @param body the request body
     * @return an {@link HttpResult}
     */
    public HttpResult post(String path, String body) {
        return send(buildRequest(path, HttpRequest.BodyPublishers.ofString(body), "POST"));
    }

    /**
     * Performs an asynchronous GET request.
     *
     * @param path the path to append to {@link #baseUrl()}
     * @return a {@link CompletableFuture} that resolves to an {@link HttpResult}
     */
    public CompletableFuture<HttpResult> getAsync(String path) {
        return sendAsync(buildRequest(path, HttpRequest.BodyPublishers.noBody(), "GET"));
    }

    // ── Batch helper ────────────────────────────────────────────────────────

    /**
     * Performs multiple GET requests concurrently and returns results in the same order.
     *
     * @param paths a list of paths to request in parallel
     * @return a list of {@link HttpResult}s in the same order as {@code paths}
     */
    public List<HttpResult> getAll(List<String> paths) {
        var futures = paths.stream()
                .map(p -> getAsync(p))
                .toList();
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    // ── Internal helpers ────────────────────────────────────────────────────

    private HttpRequest buildRequest(
            String path,
            HttpRequest.BodyPublisher bodyPublisher,
            String method
    ) {
        var builder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(timeout)
                .method(method, bodyPublisher);
        defaultHeaders.forEach(builder::header);
        return builder.build();
    }

    private HttpResult send(HttpRequest request) {
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new HttpResult.Success(response.statusCode(), response.body());
        } catch (Exception e) {
            return new HttpResult.Failure(e);
        }
    }

    private CompletableFuture<HttpResult> sendAsync(HttpRequest request) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .<HttpResult>thenApply(r -> new HttpResult.Success(r.statusCode(), r.body()))
                .exceptionally(ex -> new HttpResult.Failure(
                        ex instanceof Exception e ? e : new RuntimeException(ex)));
    }
}
