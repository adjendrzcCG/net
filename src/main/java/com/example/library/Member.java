package com.example.library;

/**
 * An immutable value object representing a library member.
 *
 * @param id       unique member identifier
 * @param name     full name of the member
 * @param email    contact e-mail address
 * @param tier     membership tier
 */
public record Member(
        String id,
        String name,
        String email,
        MemberTier tier
) {
    /** Compact constructor — validates invariants. */
    public Member {
        if (id == null || id.isBlank())     throw new IllegalArgumentException("id must not be blank");
        if (name == null || name.isBlank())  throw new IllegalArgumentException("name must not be blank");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("invalid email");
        if (tier == null)                   throw new IllegalArgumentException("tier must not be null");
    }

    /**
     * Membership tier, modelled as a sealed interface with record variants
     * so that tier-specific data (e.g. borrow limit) travels with the value.
     */
    public sealed interface MemberTier permits MemberTier.Standard,
                                               MemberTier.Premium,
                                               MemberTier.Staff {

        /** Maximum number of items that may be borrowed simultaneously. */
        int borrowLimit();

        /** Standard members may borrow up to 5 items. */
        record Standard() implements MemberTier {
            @Override public int borrowLimit() { return 5; }
        }

        /** Premium members may borrow up to 15 items. */
        record Premium() implements MemberTier {
            @Override public int borrowLimit() { return 15; }
        }

        /** Staff have an unrestricted borrow limit. */
        record Staff(String department) implements MemberTier {
            public Staff {
                if (department == null || department.isBlank())
                    throw new IllegalArgumentException("department must not be blank");
            }
            @Override public int borrowLimit() { return Integer.MAX_VALUE; }
        }

        /** Human-readable label using pattern matching for switch. */
        default String label() {
            return switch (this) {
                case Standard() -> "Standard";
                case Premium()  -> "Premium";
                case Staff(var dept) -> "Staff (%s)".formatted(dept);
            };
        }
    }
}
