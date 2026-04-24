package com.example.net;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * Factory for creating pre-configured {@link HttpClient} instances.
 *
 * <p>Uses virtual threads (Java 21, Project Loom) for the executor so that
 * concurrent async requests are cheap and non-blocking.
 */
public final class HttpClientFactory {

    private HttpClientFactory() {
        // Factory class — no instances
    }

    /**
     * Creates an {@link HttpClient} with sensible defaults:
     * <ul>
     *   <li>HTTP/2 with transparent HTTP/1.1 fallback</li>
     *   <li>30-second connection timeout</li>
     *   <li>Follow redirects automatically</li>
     *   <li>Virtual-thread executor (Java 21)</li>
     * </ul>
     *
     * @return a new {@code HttpClient}
     */
    public static HttpClient createDefault() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(30))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .build();
    }

    /**
     * Creates an {@link HttpClient} with the given connection timeout.
     *
     * @param connectTimeout connection timeout; must not be {@code null}
     * @return a new {@code HttpClient}
     * @throws NullPointerException if {@code connectTimeout} is {@code null}
     */
    public static HttpClient createWithTimeout(Duration connectTimeout) {
        java.util.Objects.requireNonNull(connectTimeout, "connectTimeout must not be null");
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(connectTimeout)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .build();
    }
}
