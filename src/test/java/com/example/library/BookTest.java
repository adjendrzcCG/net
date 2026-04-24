package com.example.library;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BookTest {

    private static Book makeBook(ItemStatus status) {
        return new Book("B001", "978-0-13-468599-1", "Effective Java",
                "Joshua Bloch", 2018, status);
    }

    @Test
    void record_accessors_are_generated() {
        var book = makeBook(new ItemStatus.Available());
        assertThat(book.id()).isEqualTo("B001");
        assertThat(book.title()).isEqualTo("Effective Java");
        assertThat(book.author()).isEqualTo("Joshua Bloch");
        assertThat(book.year()).isEqualTo(2018);
    }

    @Test
    void withStatus_returns_new_instance_with_updated_status() {
        var book    = makeBook(new ItemStatus.Available());
        var updated = book.withStatus(new ItemStatus.CheckedOut("M001", "2025-06-30"));

        // original is immutable
        assertThat(book.status()).isInstanceOf(ItemStatus.Available.class);
        // new copy has updated status
        assertThat(updated.status()).isInstanceOf(ItemStatus.CheckedOut.class);
        assertThat(updated.id()).isEqualTo(book.id());
    }

    @Test
    void summary_contains_key_fields() {
        var book = makeBook(new ItemStatus.Available());
        var summary = book.summary();
        assertThat(summary)
                .contains("B001")
                .contains("Effective Java")
                .contains("Joshua Bloch")
                .contains("2018");
    }

    @Test
    void compact_constructor_rejects_blank_id() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Book("", "978-0-13-468599-1",
                        "Title", "Author", 2020, new ItemStatus.Available()));
    }

    @Test
    void compact_constructor_rejects_year_before_1450() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Book("B1", "978-0-13-468599-1",
                        "Title", "Author", 1449, new ItemStatus.Available()));
    }

    @Test
    void record_equals_and_hashCode_are_value_based() {
        var a = makeBook(new ItemStatus.Available());
        var b = makeBook(new ItemStatus.Available());
        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }
}
