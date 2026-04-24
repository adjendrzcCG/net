package com.example.library;

import com.example.library.Member.MemberTier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LoanServiceTest {

    private Catalogue       catalogue;
    private MemberRegistry  registry;
    private LoanService     service;

    @BeforeEach
    void setUp() {
        catalogue = new Catalogue();
        registry  = new MemberRegistry();
        service   = new LoanService(catalogue, registry);

        catalogue.addBook(new Book("B001", "978-0-13-468599-1",
                "Effective Java", "Joshua Bloch", 2018, new ItemStatus.Available()));
        catalogue.addBook(new Book("B002", "978-0-13-235088-4",
                "Clean Code", "Robert Martin", 2008, new ItemStatus.Available()));

        registry.register(new Member("M001", "Alice", "alice@example.com", new MemberTier.Standard()));
        registry.register(new Member("M002", "Bob",   "bob@example.com",   new MemberTier.Premium()));
    }

    // -----------------------------------------------------------------------
    // checkOut
    // -----------------------------------------------------------------------

    @Test
    void checkOut_succeeds_for_available_book() {
        var result = service.checkOut("B001", "M001", "2025-06-30");
        assertThat(result).isPresent();
        assertThat(result.get().status()).isInstanceOf(ItemStatus.CheckedOut.class);
    }

    @Test
    void checkOut_increments_active_loan_count() {
        service.checkOut("B001", "M001", "2025-06-30");
        assertThat(service.activeLoanCount("M001")).isEqualTo(1);
    }

    @Test
    void checkOut_fails_for_unknown_member() {
        assertThat(service.checkOut("B001", "UNKNOWN", "2025-06-30")).isEmpty();
    }

    @Test
    void checkOut_fails_for_unknown_book() {
        assertThat(service.checkOut("UNKNOWN", "M001", "2025-06-30")).isEmpty();
    }

    @Test
    void checkOut_fails_for_already_checked_out_book() {
        service.checkOut("B001", "M001", "2025-06-30");
        assertThat(service.checkOut("B001", "M002", "2025-07-01")).isEmpty();
    }

    @Test
    void checkOut_succeeds_when_book_is_reserved_for_that_member() {
        service.reserve("B001", "M001");
        var result = service.checkOut("B001", "M001", "2025-06-30");
        assertThat(result).isPresent();
    }

    @Test
    void checkOut_fails_when_book_is_reserved_for_another_member() {
        service.reserve("B001", "M002");
        assertThat(service.checkOut("B001", "M001", "2025-06-30")).isEmpty();
    }

    @Test
    void checkOut_enforces_standard_borrow_limit() {
        // Standard limit is 5; add 4 more books and check them all out
        for (int i = 2; i <= 5; i++) {
            catalogue.addBook(new Book("B00" + i + "X", "978-0-00-000000-" + i,
                    "Book " + i, "Author", 2020, new ItemStatus.Available()));
            service.checkOut("B00" + i + "X", "M001", "2025-06-30");
        }
        // First book already checked out in previous calls — seed state is clean per test
        // Borrow B001 + 4 extra = 5 loans used up
        service.checkOut("B001", "M001", "2025-06-30"); // 1st book
        // Now try to exceed the limit with a 6th
        catalogue.addBook(new Book("B006X", "978-0-00-000000-6",
                "Book 6", "Author", 2020, new ItemStatus.Available()));
        assertThat(service.checkOut("B006X", "M001", "2025-06-30")).isEmpty();
    }

    // -----------------------------------------------------------------------
    // returnBook
    // -----------------------------------------------------------------------

    @Test
    void returnBook_makes_book_available_again() {
        service.checkOut("B001", "M001", "2025-06-30");
        var result = service.returnBook("B001");
        assertThat(result).isPresent();
        assertThat(result.get().status()).isInstanceOf(ItemStatus.Available.class);
    }

    @Test
    void returnBook_decrements_active_loan_count() {
        service.checkOut("B001", "M001", "2025-06-30");
        service.returnBook("B001");
        assertThat(service.activeLoanCount("M001")).isEqualTo(0);
    }

    @Test
    void returnBook_fails_for_unknown_book() {
        assertThat(service.returnBook("UNKNOWN")).isEmpty();
    }

    @Test
    void returnBook_fails_for_available_book() {
        assertThat(service.returnBook("B001")).isEmpty();
    }

    // -----------------------------------------------------------------------
    // reserve
    // -----------------------------------------------------------------------

    @Test
    void reserve_marks_book_as_reserved() {
        var result = service.reserve("B001", "M001");
        assertThat(result).isPresent();
        assertThat(result.get().status()).isInstanceOf(ItemStatus.Reserved.class);
    }

    @Test
    void reserve_fails_for_checked_out_book() {
        service.checkOut("B001", "M001", "2025-06-30");
        assertThat(service.reserve("B001", "M002")).isEmpty();
    }

    @Test
    void reserve_fails_for_unknown_member() {
        assertThat(service.reserve("B001", "UNKNOWN")).isEmpty();
    }

    // -----------------------------------------------------------------------
    // auditLog
    // -----------------------------------------------------------------------

    @Test
    void auditLog_records_checkout_and_return() {
        service.checkOut("B001", "M001", "2025-06-30");
        service.returnBook("B001");

        var log = service.auditLog();
        assertThat(log).hasSize(2);
        assertThat(log.get(0).eventType()).isEqualTo("CHECKOUT");
        assertThat(log.get(1).eventType()).isEqualTo("RETURN");
    }

    @Test
    void auditLog_records_reserve_event() {
        service.reserve("B001", "M001");
        assertThat(service.auditLog())
                .hasSize(1)
                .first()
                .satisfies(e -> assertThat(e.eventType()).isEqualTo("RESERVE"));
    }

    @Test
    void auditLog_is_unmodifiable() {
        var log = service.auditLog();
        assertThatThrownBy(() -> log.add(new LoanService.LoanEvent("X","Y","Z","D")))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
