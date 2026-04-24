package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CatalogueTest {

    private Catalogue catalogue;
    private Book effectiveJava;
    private Book cleanCode;

    @BeforeEach
    void setUp() {
        catalogue = new Catalogue();
        effectiveJava = new Book("B001", "978-0-13-468599-1",
                "Effective Java", "Joshua Bloch", 2018, new ItemStatus.Available());
        cleanCode = new Book("B002", "978-0-13-235088-4",
                "Clean Code", "Robert Martin", 2008, new ItemStatus.Available());
        catalogue.addBook(effectiveJava);
        catalogue.addBook(cleanCode);
    }

    @Test
    void addBook_increases_size() {
        assertThat(catalogue.size()).isEqualTo(2);
    }

    @Test
    void findById_returns_book_when_present() {
        assertThat(catalogue.findById("B001")).contains(effectiveJava);
    }

    @Test
    void findById_returns_empty_when_absent() {
        assertThat(catalogue.findById("UNKNOWN")).isEmpty();
    }

    @Test
    void removeBook_decreases_size() {
        catalogue.removeBook("B001");
        assertThat(catalogue.size()).isEqualTo(1);
    }

    @Test
    void removeBook_returns_false_for_unknown_id() {
        assertThat(catalogue.removeBook("UNKNOWN")).isFalse();
    }

    @Test
    void updateStatus_changes_book_status() {
        var newStatus = new ItemStatus.CheckedOut("M001", "2025-06-30");
        var updated = catalogue.updateStatus("B001", newStatus);
        assertThat(updated).isPresent();
        assertThat(updated.get().status()).isEqualTo(newStatus);
    }

    @Test
    void updateStatus_returns_empty_for_unknown_id() {
        assertThat(catalogue.updateStatus("UNKNOWN", new ItemStatus.Available())).isEmpty();
    }

    @Test
    void searchByTitle_is_case_insensitive() {
        var results = catalogue.searchByTitle("java");
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().id()).isEqualTo("B001");
    }

    @Test
    void searchByTitle_returns_empty_for_blank_fragment() {
        assertThat(catalogue.searchByTitle("  ")).isEmpty();
    }

    @Test
    void availableBooks_excludes_checked_out_books() {
        catalogue.updateStatus("B001", new ItemStatus.CheckedOut("M001", "2025-06-30"));
        var available = catalogue.availableBooks();
        assertThat(available).hasSize(1);
        assertThat(available.getFirst().id()).isEqualTo("B002");
    }

    @Test
    void checkedOutBy_returns_only_that_members_books() {
        catalogue.updateStatus("B001", new ItemStatus.CheckedOut("M001", "2025-06-30"));
        catalogue.updateStatus("B002", new ItemStatus.CheckedOut("M002", "2025-07-01"));

        assertThat(catalogue.checkedOutBy("M001")).hasSize(1);
        assertThat(catalogue.checkedOutBy("M001").getFirst().id()).isEqualTo("B001");
    }

    @Test
    void allBooks_returns_sorted_snapshot() {
        var all = catalogue.allBooks();
        assertThat(all).hasSize(2);
        // "Clean Code" < "Effective Java" alphabetically
        assertThat(all.get(0).title()).isEqualTo("Clean Code");
    }

    @Test
    void addBook_rejects_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> catalogue.addBook(null));
    }
}
