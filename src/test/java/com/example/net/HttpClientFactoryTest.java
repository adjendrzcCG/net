package com.example.net;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link HttpClientFactory}.
 */
class HttpClientFactoryTest {

    @Test
    void createDefaultReturnsNonNullClient() {
        var client = HttpClientFactory.createDefault();
        assertThat(client).isNotNull();
    }

    @Test
    void createDefaultUsesHttp2() {
        var client = HttpClientFactory.createDefault();
        assertThat(client.version()).isEqualTo(java.net.http.HttpClient.Version.HTTP_2);
    }

    @Test
    void createWithTimeoutReturnsNonNullClient() {
        var client = HttpClientFactory.createWithTimeout(Duration.ofSeconds(5));
        assertThat(client).isNotNull();
        assertThat(client.connectTimeout()).hasValue(Duration.ofSeconds(5));
    }

    @Test
    void createWithTimeoutRejectsNull() {
        assertThatThrownBy(() -> HttpClientFactory.createWithTimeout(null))
                .isInstanceOf(NullPointerException.class);
    }
}
