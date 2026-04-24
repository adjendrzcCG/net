package com.example.net;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link HttpResult}.
 *
 * <p>Uses JUnit 5 + AssertJ and exercises the sealed-interface hierarchy,
 * record components, and pattern-matching switch expressions.
 */
class HttpResultTest {

    // ── Success ─────────────────────────────────────────────────────────────

    @Test
    void successRecordExposesComponents() {
        var result = new HttpResult.Success(200, "OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isEqualTo("OK");
    }

    @ParameterizedTest
    @CsvSource({"200,true", "201,true", "299,true", "300,false", "404,false", "500,false"})
    void successIsOkReflectsStatusRange(int code, boolean expected) {
        var result = new HttpResult.Success(code, "body");
        assertThat(result.isOk()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {99, 600, -1, 0})
    void successRejectsInvalidStatusCode(int code) {
        assertThatThrownBy(() -> new HttpResult.Success(code, "body"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.valueOf(code));
    }

    @Test
    void successRejectsNullBody() {
        assertThatThrownBy(() -> new HttpResult.Success(200, null))
                .isInstanceOf(NullPointerException.class);
    }

    // ── Failure ─────────────────────────────────────────────────────────────

    @Test
    void failureRecordExposesError() {
        var cause = new RuntimeException("timeout");
        var result = new HttpResult.Failure(cause);
        assertThat(result.error()).isSameAs(cause);
    }

    @Test
    void failureRejectsNullError() {
        assertThatThrownBy(() -> new HttpResult.Failure(null))
                .isInstanceOf(NullPointerException.class);
    }

    // ── Pattern-matching switch ──────────────────────────────────────────────

    @Test
    void patternMatchingSwitchOnSuccess() {
        HttpResult result = new HttpResult.Success(200, "hello");

        // Switch expression with record-deconstruction patterns (Java 21)
        String description = switch (result) {
            case HttpResult.Success(var code, var body) when code >= 200 && code < 300 ->
                    "2xx: " + body;
            case HttpResult.Success(var code, var body) ->
                    "non-2xx: " + code;
            case HttpResult.Failure(var error) ->
                    "error: " + error.getMessage();
        };

        assertThat(description).isEqualTo("2xx: hello");
    }

    @Test
    void patternMatchingSwitchOnFailure() {
        HttpResult result = new HttpResult.Failure(new IllegalStateException("gone"));

        String description = switch (result) {
            case HttpResult.Success(var code, var body) -> "success";
            case HttpResult.Failure(var error) -> "failure: " + error.getMessage();
        };

        assertThat(description).isEqualTo("failure: gone");
    }

    // ── Record equality ──────────────────────────────────────────────────────

    @Test
    void successEquality() {
        assertThat(new HttpResult.Success(200, "OK"))
                .isEqualTo(new HttpResult.Success(200, "OK"));
        assertThat(new HttpResult.Success(200, "OK"))
                .isNotEqualTo(new HttpResult.Success(201, "OK"));
    }
}
