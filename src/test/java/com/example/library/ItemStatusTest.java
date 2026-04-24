package com.example.library;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ItemStatusTest {

    @Test
    void available_describe() {
        assertThat(new ItemStatus.Available().describe())
                .isEqualTo("Available for borrowing");
    }

    @Test
    void checkedOut_describe_includes_member_and_due_date() {
        var status = new ItemStatus.CheckedOut("M001", "2025-06-30");
        assertThat(status.describe())
                .contains("M001")
                .contains("2025-06-30");
    }

    @Test
    void reserved_describe_includes_member() {
        assertThat(new ItemStatus.Reserved("M002").describe())
                .contains("M002");
    }

    @Test
    void lost_describe() {
        assertThat(new ItemStatus.Lost().describe())
                .isEqualTo("Reported lost");
    }

    @Test
    void pattern_matching_switch_is_exhaustive() {
        // Compiler guarantees exhaustiveness for sealed types;
        // this test just exercises every branch at runtime.
        ItemStatus[] statuses = {
                new ItemStatus.Available(),
                new ItemStatus.CheckedOut("M1", "2025-01-01"),
                new ItemStatus.Reserved("M2"),
                new ItemStatus.Lost()
        };
        for (var s : statuses) {
            assertThat(s.describe()).isNotBlank();
        }
    }
}
