package com.example.library;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Orchestrates borrowing and returning of books.
 *
 * <p>Demonstrates:
 * <ul>
 *   <li>Sealed-interface pattern matching for business logic
 *   <li>{@code SequencedCollection} ({@link Deque}) for ordered audit log
 *   <li>Modern {@code Map.getOrDefault} / {@code computeIfAbsent}
 *   <li>String templates via {@code String.formatted}
 * </ul>
 */
public class LoanService {

    /** Immutable value object recording a single loan event. */
    public record LoanEvent(
            String eventType,
            String bookId,
            String memberId,
            String timestamp
    ) {}

    private final Catalogue catalogue;
    private final MemberRegistry registry;

    /** Ordered audit log – a {@code Deque} is a {@code SequencedCollection}. */
    private final Deque<LoanEvent> auditLog = new ArrayDeque<>();

    /** Tracks active loan counts per member. */
    private final Map<String, Integer> activeLoanCount = new HashMap<>();

    public LoanService(Catalogue catalogue, MemberRegistry registry) {
        if (catalogue == null) throw new IllegalArgumentException("catalogue must not be null");
        if (registry  == null) throw new IllegalArgumentException("registry must not be null");
        this.catalogue = catalogue;
        this.registry  = registry;
    }

    // -------------------------------------------------------------------------
    // Checkout
    // -------------------------------------------------------------------------

    /**
     * Checks out a book to a member.
     *
     * @param bookId   catalogue ID of the book
     * @param memberId member's ID
     * @param dueDate  ISO-8601 due-date string
     * @return the updated {@link Book} on success, or empty on failure
     */
    public Optional<Book> checkOut(String bookId, String memberId, String dueDate) {
        var memberOpt = registry.findById(memberId);
        if (memberOpt.isEmpty()) return Optional.empty();

        var bookOpt = catalogue.findById(bookId);
        if (bookOpt.isEmpty()) return Optional.empty();

        var book   = bookOpt.get();
        var member = memberOpt.get();

        // Only available (or reserved-for-this-member) books can be checked out
        boolean canCheckOut = switch (book.status()) {
            case ItemStatus.Available()     -> true;
            case ItemStatus.Reserved(var m) -> m.equals(memberId);
            default                         -> false;
        };
        if (!canCheckOut) return Optional.empty();

        // Enforce borrow limit
        int current = activeLoanCount.getOrDefault(memberId, 0);
        if (current >= member.tier().borrowLimit()) return Optional.empty();

        var updated = catalogue.updateStatus(bookId,
                new ItemStatus.CheckedOut(memberId, dueDate));
        updated.ifPresent(b -> {
            activeLoanCount.merge(memberId, 1, Integer::sum);
            auditLog.addLast(new LoanEvent("CHECKOUT", bookId, memberId, dueDate));
        });
        return updated;
    }

    // -------------------------------------------------------------------------
    // Return
    // -------------------------------------------------------------------------

    /**
     * Returns a book to the library.
     *
     * @param bookId the catalogue ID of the book being returned
     * @return the updated {@link Book} on success, or empty on failure
     */
    public Optional<Book> returnBook(String bookId) {
        var bookOpt = catalogue.findById(bookId);
        if (bookOpt.isEmpty()) return Optional.empty();

        var book = bookOpt.get();
        if (!(book.status() instanceof ItemStatus.CheckedOut(var memberId, var due)))
            return Optional.empty();

        var updated = catalogue.updateStatus(bookId, new ItemStatus.Available());
        updated.ifPresent(b -> {
            activeLoanCount.merge(memberId, -1, Integer::sum);
            auditLog.addLast(new LoanEvent("RETURN", bookId, memberId, due));
        });
        return updated;
    }

    // -------------------------------------------------------------------------
    // Reserve
    // -------------------------------------------------------------------------

    /**
     * Places a hold/reservation on a book.
     *
     * @param bookId   catalogue ID of the book
     * @param memberId member's ID
     * @return the updated {@link Book} on success, or empty on failure
     */
    public Optional<Book> reserve(String bookId, String memberId) {
        if (registry.findById(memberId).isEmpty()) return Optional.empty();
        var bookOpt = catalogue.findById(bookId);
        if (bookOpt.isEmpty()) return Optional.empty();

        var book = bookOpt.get();
        // Can only reserve books that are available
        if (!(book.status() instanceof ItemStatus.Available)) return Optional.empty();

        var updated = catalogue.updateStatus(bookId, new ItemStatus.Reserved(memberId));
        updated.ifPresent(b ->
                auditLog.addLast(new LoanEvent("RESERVE", bookId, memberId, "")));
        return updated;
    }

    // -------------------------------------------------------------------------
    // Queries
    // -------------------------------------------------------------------------

    /**
     * Returns an unmodifiable snapshot of the audit log in insertion order.
     *
     * <p>Uses {@link java.util.SequencedCollection} semantics available
     * from Java 21 — the first event is {@code auditLog.peekFirst()}.
     */
    public List<LoanEvent> auditLog() {
        return Collections.unmodifiableList(new ArrayList<>(auditLog));
    }

    /** Returns the number of items currently on loan for a given member. */
    public int activeLoanCount(String memberId) {
        return activeLoanCount.getOrDefault(memberId, 0);
    }
}
