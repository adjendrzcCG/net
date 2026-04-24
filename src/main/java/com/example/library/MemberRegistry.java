package com.example.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles member registration and look-ups.
 *
 * <p>Demonstrates {@code Optional}, modern collection factories, and the
 * sequenced-collections view of a {@link HashMap}.
 */
public class MemberRegistry {

    private final Map<String, Member> members = new HashMap<>();

    // -------------------------------------------------------------------------
    // Mutations
    // -------------------------------------------------------------------------

    /**
     * Registers a new member.
     *
     * @param member the member to register
     * @throws IllegalArgumentException if the member is null or its ID is already registered
     */
    public void register(Member member) {
        if (member == null) throw new IllegalArgumentException("member must not be null");
        if (members.containsKey(member.id()))
            throw new IllegalArgumentException("Member already registered: " + member.id());
        members.put(member.id(), member);
    }

    /**
     * Removes a member from the registry.
     *
     * @param id the member ID
     * @return {@code true} if the member was present and removed
     */
    public boolean deregister(String id) {
        return members.remove(id) != null;
    }

    // -------------------------------------------------------------------------
    // Queries
    // -------------------------------------------------------------------------

    /**
     * Looks up a member by ID.
     *
     * @param id the member ID
     * @return the member, or empty if not found
     */
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(members.get(id));
    }

    /**
     * Searches for members whose name contains the given fragment
     * (case-insensitive).
     *
     * @param fragment search fragment
     * @return unmodifiable list of matching members
     */
    public List<Member> searchByName(String fragment) {
        if (fragment == null || fragment.isBlank()) return List.of();
        var lower = fragment.toLowerCase();
        return members.values().stream()
                .filter(m -> m.name().toLowerCase().contains(lower))
                .sorted((a, b) -> a.name().compareToIgnoreCase(b.name()))
                .toList();
    }

    /** Total number of registered members. */
    public int size() {
        return members.size();
    }
}
