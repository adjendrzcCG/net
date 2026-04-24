package com.example.net;

/**
 * A discriminated union representing the outcome of an HTTP request.
 *
 * <p>Uses a sealed interface (Java 17+) so that switch expressions can exhaustively
 * pattern-match every case without a default branch.
 *
 * <p>Each variant is a {@code record} (Java 16+) for compact, immutable data carriers.
 */
public sealed interface HttpResult
        permits HttpResult.Success, HttpResult.Failure {

    /**
     * A successful HTTP response.
     *
     * @param statusCode the HTTP status code
     * @param body       the response body as a string
     */
    record Success(int statusCode, String body) implements HttpResult {
        /**
         * Compact canonical constructor with validation.
         *
         * @throws IllegalArgumentException if {@code statusCode} is not in [100, 599]
         * @throws NullPointerException     if {@code body} is {@code null}
         */
        public Success {
            if (statusCode < 100 || statusCode > 599) {
                throw new IllegalArgumentException(
                        "Invalid HTTP status code: " + statusCode);
            }
            java.util.Objects.requireNonNull(body, "body must not be null");
        }

        /** Returns {@code true} when the status code is in the 2xx range. */
        public boolean isOk() {
            return statusCode >= 200 && statusCode < 300;
        }
    }

    /**
     * A failed HTTP request (network error, timeout, etc.).
     *
     * @param error the underlying exception
     */
    record Failure(Exception error) implements HttpResult {
        /**
         * Compact canonical constructor with null-guard.
         *
         * @throws NullPointerException if {@code error} is {@code null}
         */
        public Failure {
            java.util.Objects.requireNonNull(error, "error must not be null");
        }
    }
}
