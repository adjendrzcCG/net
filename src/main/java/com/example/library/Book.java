package com.example.library;

/**
 * An immutable value object representing a book in the library catalogue.
 *
 * <p>Uses a Java 21 record for automatic generation of constructor,
 * accessors, {@code equals}, {@code hashCode}, and {@code toString}.
 * A compact canonical constructor validates the invariants.
 *
 * @param id       unique catalogue identifier
 * @param isbn     ISBN-13 of the book
 * @param title    title of the book
 * @param author   primary author
 * @param year     publication year
 * @param status   current availability status
 */
public record Book(
        String id,
        String isbn,
        String title,
        String author,
        int year,
        ItemStatus status
) {
    /** Compact constructor — validates invariants without boilerplate. */
    public Book {
        if (id == null || id.isBlank())     throw new IllegalArgumentException("id must not be blank");
        if (isbn == null || isbn.isBlank())  throw new IllegalArgumentException("isbn must not be blank");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title must not be blank");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("author must not be blank");
        if (year < 1450)                    throw new IllegalArgumentException("year must be ≥ 1450");
        if (status == null)                 throw new IllegalArgumentException("status must not be null");
    }

    /** Returns a copy of this book with the given status. */
    public Book withStatus(ItemStatus newStatus) {
        return new Book(id, isbn, title, author, year, newStatus);
    }

    /**
     * Returns a formatted summary using a Java 21 text block and
     * {@code String.formatted}.
     */
    public String summary() {
        return """
                Book[%s]
                  Title  : %s
                  Author : %s
                  ISBN   : %s
                  Year   : %d
                  Status : %s
                """.formatted(id, title, author, isbn, year, status.describe());
    }
}
