package com.example.library;

/**
 * Represents the availability status of a library item.
 * Uses Java 21 sealed interface with record implementations for
 * exhaustive pattern matching.
 */
public sealed interface ItemStatus permits ItemStatus.Available,
                                           ItemStatus.CheckedOut,
                                           ItemStatus.Reserved,
                                           ItemStatus.Lost {

    /** The item is on the shelf and can be borrowed. */
    record Available() implements ItemStatus {}

    /**
     * The item has been checked out by a member.
     *
     * @param memberId  the ID of the member who borrowed the item
     * @param dueDate   the ISO-8601 due-date string (yyyy-MM-dd)
     */
    record CheckedOut(String memberId, String dueDate) implements ItemStatus {}

    /**
     * The item is reserved for a member but not yet collected.
     *
     * @param memberId the ID of the member who placed the hold
     */
    record Reserved(String memberId) implements ItemStatus {}

    /** The item has been reported lost and is no longer in circulation. */
    record Lost() implements ItemStatus {}

    /**
     * Returns a human-readable description of this status using Java 21
     * pattern matching for switch (no fall-through, exhaustive).
     */
    default String describe() {
        return switch (this) {
            case Available()              -> "Available for borrowing";
            case CheckedOut(var m, var d) -> "Checked out by member %s (due %s)".formatted(m, d);
            case Reserved(var m)          -> "Reserved for member %s".formatted(m);
            case Lost()                   -> "Reported lost";
        };
    }
}
