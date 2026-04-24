package com.example.net;

import java.util.Objects;
import java.util.OptionalInt;

/**
 * A validated, immutable representation of a URL endpoint.
 *
 * <p>Demonstrates Java 21 records with compact constructors, pattern matching,
 * and switch expressions.
 *
 * @param scheme the URL scheme ({@code "http"} or {@code "https"})
 * @param host   the hostname or IP address
 * @param port   the TCP port; use {@code -1} for the scheme's default
 * @param path   the URL path component (must start with {@code "/"})
 */
public record Endpoint(String scheme, String host, int port, String path) {

    /** Validates and normalises record components. */
    public Endpoint {
        Objects.requireNonNull(scheme, "scheme must not be null");
        Objects.requireNonNull(host, "host must not be null");
        Objects.requireNonNull(path, "path must not be null");

        scheme = scheme.toLowerCase();
        if (!scheme.equals("http") && !scheme.equals("https")) {
            throw new IllegalArgumentException("Unsupported scheme: " + scheme);
        }
        if (host.isBlank()) {
            throw new IllegalArgumentException("host must not be blank");
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("path must start with '/': " + path);
        }
        if (port != -1 && (port < 1 || port > 65535)) {
            throw new IllegalArgumentException("Invalid port: " + port);
        }
    }

    // ── Factory methods ─────────────────────────────────────────────────────

    /**
     * Parses a minimal URL of the form {@code scheme://host[:port]/path}.
     *
     * @param url the URL to parse
     * @return a validated {@code Endpoint}
     * @throws IllegalArgumentException if the URL cannot be parsed
     */
    public static Endpoint parse(String url) {
        Objects.requireNonNull(url, "url must not be null");
        try {
            var uri = new java.net.URI(url);
            int port = uri.getPort(); // -1 when absent
            String path = uri.getRawPath();
            if (path == null || path.isBlank()) {
                path = "/";
            }
            return new Endpoint(uri.getScheme(), uri.getHost(), port, path);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot parse URL: " + url, e);
        }
    }

    /** Creates an HTTPS endpoint on the default port (443). */
    public static Endpoint https(String host, String path) {
        return new Endpoint("https", host, -1, path);
    }

    /** Creates an HTTP endpoint on the default port (80). */
    public static Endpoint http(String host, String path) {
        return new Endpoint("http", host, -1, path);
    }

    // ── Derived properties ──────────────────────────────────────────────────

    /** Returns the effective port (scheme default when {@code port == -1}). */
    public int effectivePort() {
        if (port != -1) {
            return port;
        }
        // Switch expression (Java 14+) with exhaustive pattern matching
        return switch (scheme) {
            case "https" -> 443;
            case "http"  -> 80;
            default      -> throw new IllegalStateException("Unexpected scheme: " + scheme);
        };
    }

    /** Returns {@code true} when TLS is used. */
    public boolean isSecure() {
        return "https".equals(scheme);
    }

    /**
     * Returns the explicit port wrapped in an {@link OptionalInt}, or
     * {@link OptionalInt#empty()} if the scheme's default is used.
     */
    public OptionalInt explicitPort() {
        return port == -1 ? OptionalInt.empty() : OptionalInt.of(port);
    }

    /**
     * Builds the full URL string for this endpoint.
     *
     * @return e.g. {@code "https://example.com/api/v1"}
     */
    public String toUrl() {
        return explicitPort()
                .stream()
                .mapToObj(p -> "%s://%s:%d%s".formatted(scheme, host, p, path))
                .findFirst()
                .orElseGet(() -> "%s://%s%s".formatted(scheme, host, path));
    }
}
