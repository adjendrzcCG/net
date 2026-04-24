package com.example.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory catalogue of library books.
 *
 * <p>Demonstrates modern Java idioms:
 * <ul>
 *   <li>Unmodifiable collections via {@code Collections.unmodifiableList}
 *   <li>{@code Optional} instead of nullable returns
 *   <li>Stream pipelines with method references
 *   <li>Local-variable type inference ({@code var})
 * </ul>
 */
public class Catalogue {

    private final Map<String, Book> books = new HashMap<>();

    // -------------------------------------------------------------------------
    // Mutations
    // -------------------------------------------------------------------------

    /**
     * Adds or replaces a book in the catalogue.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if book is null
     */
    public void addBook(Book book) {
        if (book == null) throw new IllegalArgumentException("book must not be null");
        books.put(book.id(), book);
    }

    /**
     * Removes a book from the catalogue by its ID.
     *
     * @param id the catalogue ID
     * @return {@code true} if the book was present and removed
     */
    public boolean removeBook(String id) {
        return books.remove(id) != null;
    }

    /**
     * Updates the status of an existing book.
     *
     * @param id        catalogue ID
     * @param newStatus the new status
     * @return the updated {@link Book}, or empty if the ID is unknown
     */
    public Optional<Book> updateStatus(String id, ItemStatus newStatus) {
        var existing = books.get(id);
        if (existing == null) return Optional.empty();
        var updated = existing.withStatus(newStatus);
        books.put(id, updated);
        return Optional.of(updated);
    }

    // -------------------------------------------------------------------------
    // Queries
    // -------------------------------------------------------------------------

    /**
     * Looks up a book by its catalogue ID.
     *
     * @param id the catalogue ID
     * @return the book, or empty if not found
     */
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    /**
     * Returns all books whose title contains the given fragment
     * (case-insensitive).
     *
     * @param fragment search fragment
     * @return unmodifiable list of matching books
     */
    public List<Book> searchByTitle(String fragment) {
        if (fragment == null || fragment.isBlank()) return List.of();
        var lower = fragment.toLowerCase();
        return books.values().stream()
                .filter(b -> b.title().toLowerCase().contains(lower))
                .sorted((a, b) -> a.title().compareToIgnoreCase(b.title()))
                .toList();   // Java 16+ Stream.toList() — unmodifiable
    }

    /**
     * Returns all books currently available for borrowing.
     *
     * @return unmodifiable list of available books
     */
    public List<Book> availableBooks() {
        return books.values().stream()
                .filter(b -> b.status() instanceof ItemStatus.Available)
                .sorted((a, b) -> a.title().compareToIgnoreCase(b.title()))
                .toList();
    }

    /**
     * Returns all books checked out by a specific member.
     *
     * @param memberId the member's ID
     * @return unmodifiable list of books checked out by that member
     */
    public List<Book> checkedOutBy(String memberId) {
        return books.values().stream()
                .filter(b -> b.status() instanceof ItemStatus.CheckedOut(var m, var d)
                             && m.equals(memberId))
                .sorted((a, b) -> a.title().compareToIgnoreCase(b.title()))
                .toList();
    }

    /**
     * Returns all books in the catalogue (unmodifiable snapshot).
     *
     * @return unmodifiable list of all books
     */
    public List<Book> allBooks() {
        var sorted = new ArrayList<>(books.values());
        sorted.sort((a, b) -> a.title().compareToIgnoreCase(b.title()));
        return Collections.unmodifiableList(sorted);
    }

    /** Total number of books in the catalogue. */
    public int size() {
        return books.size();
    }
}
