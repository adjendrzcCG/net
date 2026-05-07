package com.simplewebapp.model;

/**
 * ErrorViewModel — Java 16+ {@code record} equivalent of the C# ErrorViewModel class.
 *
 * <h2>.NET → Java Migration</h2>
 * <pre>
 *   // C# original (Models/ErrorViewModel.cs)
 *   public class ErrorViewModel {
 *       public string RequestId { get; set; }
 *       public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);
 *   }
 *
 *   // Java 21 equivalent (this file)
 *   public record ErrorViewModel(String requestId) {
 *       public boolean showRequestId() { return !requestId.isEmpty(); }
 *   }
 * </pre>
 *
 * <h2>Why a Java {@code record}?</h2>
 * <p>The C# class is a pure data carrier — it holds a request ID and exposes
 * a computed boolean property. A Java {@code record} (introduced in Java 16,
 * preview in Java 14/15) is the idiomatic modern replacement:
 * <ul>
 *   <li><strong>Immutable</strong> — fields are {@code final}, matching the
 *       ephemeral per-request nature of error information.</li>
 *   <li><strong>Zero boilerplate</strong> — constructor, {@code equals()},
 *       {@code hashCode()}, and {@code toString()} are auto-generated.</li>
 *   <li><strong>Compact constructor</strong> — allows validation/normalisation
 *       at construction time (null → empty string below).</li>
 * </ul>
 *
 * <h2>Accessor Method Naming</h2>
 * <p>Records generate accessor methods named after the component (e.g.
 * {@link #requestId()}), not with a {@code get} prefix. Thymeleaf 3.x
 * is aware of record accessor naming and resolves {@code ${errorViewModel.requestId}}
 * correctly via reflection.
 *
 * @param requestId the unique identifier of the failed HTTP request;
 *                  normalised to empty string if {@code null}
 */
public record ErrorViewModel(String requestId) {

    /**
     * Compact constructor — normalises {@code null} request IDs to an empty
     * string, ensuring consistent template behaviour without null checks in views.
     *
     * <p>This mirrors the implicit null-safety of C#'s
     * {@code string.IsNullOrEmpty(RequestId)} check in {@code ShowRequestId}.
     */
    public ErrorViewModel {
        requestId = (requestId != null) ? requestId : "";
    }

    /**
     * Computed accessor — determines whether a request ID is available to display.
     *
     * <p>Direct equivalent of the C# computed property:
     * {@code public bool ShowRequestId => !string.IsNullOrEmpty(RequestId);}
     *
     * <p>Used in the Thymeleaf error template:
     * {@code <div th:if="${errorViewModel.showRequestId()}">}
     *
     * @return {@code true} if {@link #requestId()} is non-empty
     */
    public boolean showRequestId() {
        return !requestId.isEmpty();
    }
}
