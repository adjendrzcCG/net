package com.example.net;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link Endpoint}.
 */
class EndpointTest {

    // ── Factory methods ─────────────────────────────────────────────────────

    @Test
    void httpsFactoryBuildsSecureEndpoint() {
        var ep = Endpoint.https("example.com", "/api");
        assertThat(ep.scheme()).isEqualTo("https");
        assertThat(ep.host()).isEqualTo("example.com");
        assertThat(ep.port()).isEqualTo(-1);
        assertThat(ep.path()).isEqualTo("/api");
        assertThat(ep.isSecure()).isTrue();
    }

    @Test
    void httpFactoryBuildsInsecureEndpoint() {
        var ep = Endpoint.http("localhost", "/");
        assertThat(ep.isSecure()).isFalse();
        assertThat(ep.effectivePort()).isEqualTo(80);
    }

    @Test
    void parseHandlesExplicitPort() {
        var ep = Endpoint.parse("https://api.example.com:8443/v1");
        assertThat(ep.scheme()).isEqualTo("https");
        assertThat(ep.host()).isEqualTo("api.example.com");
        assertThat(ep.port()).isEqualTo(8443);
        assertThat(ep.path()).isEqualTo("/v1");
    }

    @Test
    void parseHandlesDefaultPort() {
        var ep = Endpoint.parse("http://example.com/");
        assertThat(ep.port()).isEqualTo(-1);
        assertThat(ep.effectivePort()).isEqualTo(80);
    }

    @Test
    void parseFallsBackToRootPath() {
        var ep = Endpoint.parse("https://example.com");
        assertThat(ep.path()).isEqualTo("/");
    }

    // ── Validation ───────────────────────────────────────────────────────────

    @Test
    void rejectsUnsupportedScheme() {
        assertThatThrownBy(() -> new Endpoint("ftp", "host", -1, "/"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ftp");
    }

    @Test
    void rejectsBlankHost() {
        assertThatThrownBy(() -> new Endpoint("https", "  ", -1, "/"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsPathWithoutLeadingSlash() {
        assertThatThrownBy(() -> new Endpoint("https", "host", -1, "noslash"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("noslash");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 65536, -2})
    void rejectsInvalidPort(int port) {
        assertThatThrownBy(() -> new Endpoint("https", "host", port, "/"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ── Derived properties ───────────────────────────────────────────────────

    @ParameterizedTest
    @CsvSource({
            "https, -1, 443",
            "http,  -1,  80",
            "https, 8443, 8443",
            "http,  8080, 8080"
    })
    void effectivePortReflectsSchemeOrExplicitPort(String scheme, int port, int expected) {
        var ep = new Endpoint(scheme.trim(), "host", port, "/");
        assertThat(ep.effectivePort()).isEqualTo(expected);
    }

    @Test
    void toUrlWithoutExplicitPort() {
        var ep = Endpoint.https("example.com", "/path");
        assertThat(ep.toUrl()).isEqualTo("https://example.com/path");
    }

    @Test
    void toUrlWithExplicitPort() {
        var ep = new Endpoint("https", "example.com", 8443, "/path");
        assertThat(ep.toUrl()).isEqualTo("https://example.com:8443/path");
    }

    @Test
    void explicitPortIsEmptyForDefaultPort() {
        assertThat(Endpoint.https("example.com", "/").explicitPort()).isEmpty();
    }

    @Test
    void explicitPortIsPresentForCustomPort() {
        var ep = new Endpoint("https", "example.com", 9000, "/");
        assertThat(ep.explicitPort()).hasValue(9000);
    }

    // ── Record equality ──────────────────────────────────────────────────────

    @Test
    void equalEndpointsAreEqual() {
        assertThat(Endpoint.https("example.com", "/"))
                .isEqualTo(Endpoint.https("example.com", "/"));
    }

    @Test
    void differentHostsAreNotEqual() {
        assertThat(Endpoint.https("a.com", "/"))
                .isNotEqualTo(Endpoint.https("b.com", "/"));
    }
}
