package com.example.library;

import com.example.library.Member.MemberTier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberRegistryTest {

    private MemberRegistry registry;
    private Member alice;

    @BeforeEach
    void setUp() {
        registry = new MemberRegistry();
        alice = new Member("M001", "Alice Smith", "alice@example.com", new MemberTier.Standard());
        registry.register(alice);
    }

    @Test
    void register_increases_size() {
        assertThat(registry.size()).isEqualTo(1);
    }

    @Test
    void findById_returns_member_when_present() {
        assertThat(registry.findById("M001")).contains(alice);
    }

    @Test
    void findById_returns_empty_when_absent() {
        assertThat(registry.findById("UNKNOWN")).isEmpty();
    }

    @Test
    void deregister_removes_member() {
        registry.deregister("M001");
        assertThat(registry.size()).isEqualTo(0);
    }

    @Test
    void deregister_returns_false_for_unknown_id() {
        assertThat(registry.deregister("UNKNOWN")).isFalse();
    }

    @Test
    void register_throws_for_duplicate_id() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> registry.register(alice));
    }

    @Test
    void register_throws_for_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> registry.register(null));
    }

    @Test
    void searchByName_is_case_insensitive() {
        var results = registry.searchByName("alice");
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().id()).isEqualTo("M001");
    }

    @Test
    void searchByName_returns_empty_for_blank_fragment() {
        assertThat(registry.searchByName("  ")).isEmpty();
    }
}
