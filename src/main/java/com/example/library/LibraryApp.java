package com.example.library;

import com.example.library.Member.MemberTier;

/**
 * Entry point for the Library Management System demo.
 *
 * <p>Showcases Java 21 features end-to-end:
 * <ul>
 *   <li>Records ({@link Book}, {@link Member}, {@link LoanService.LoanEvent})
 *   <li>Sealed interfaces ({@link ItemStatus}, {@link MemberTier})
 *   <li>Pattern matching for {@code switch}
 *   <li>Text blocks
 *   <li>Local-variable type inference ({@code var})
 *   <li>Modern {@code Stream} terminals ({@code .toList()})
 * </ul>
 */
public class LibraryApp {

    public static void main(String[] args) {
        System.out.println("""
                ╔══════════════════════════════════════════╗
                ║     Library Management System  v1.0      ║
                ║           Java 21 Edition                ║
                ╚══════════════════════════════════════════╝
                """);

        // --- Set up catalogue and registry ---
        var catalogue = new Catalogue();
        var registry  = new MemberRegistry();
        var service   = new LoanService(catalogue, registry);

        // --- Seed books ---
        catalogue.addBook(new Book("B001", "978-0-13-468599-1",
                "Effective Java",       "Joshua Bloch",   2018, new ItemStatus.Available()));
        catalogue.addBook(new Book("B002", "978-0-13-110362-7",
                "The C Programming Language", "Brian Kernighan", 1988, new ItemStatus.Available()));
        catalogue.addBook(new Book("B003", "978-0-20-161622-4",
                "The Pragmatic Programmer",   "David Thomas",   2019, new ItemStatus.Available()));
        catalogue.addBook(new Book("B004", "978-0-13-235088-4",
                "Clean Code",           "Robert Martin",  2008, new ItemStatus.Available()));

        // --- Seed members ---
        registry.register(new Member("M001", "Alice Smith",   "alice@example.com",  new MemberTier.Standard()));
        registry.register(new Member("M002", "Bob Johnson",   "bob@example.com",    new MemberTier.Premium()));
        registry.register(new Member("M003", "Carol Williams","carol@example.com",  new MemberTier.Staff("Engineering")));

        // --- Demonstrate checkout / return / reserve ---
        System.out.println("=== Checking out B001 to Alice (M001) ===");
        service.checkOut("B001", "M001", "2025-06-30")
               .ifPresent(b -> System.out.println(b.summary()));

        System.out.println("=== Reserving B002 for Bob (M002) ===");
        service.reserve("B002", "M002")
               .ifPresent(b -> System.out.println(b.summary()));

        System.out.println("=== Available books ===");
        catalogue.availableBooks()
                 .forEach(b -> System.out.printf("  • [%s] %s%n", b.id(), b.title()));

        System.out.println("\n=== Returning B001 ===");
        service.returnBook("B001")
               .ifPresent(b -> System.out.println("  Returned: " + b.title() + " → " + b.status().describe()));

        System.out.println("\n=== Audit log ===");
        service.auditLog()
               .forEach(e -> System.out.printf("  %-10s book=%-5s member=%-5s%n",
                       e.eventType(), e.bookId(), e.memberId()));

        // Use a switch expression to produce a tier summary for each member
        System.out.println("\n=== Member tier labels ===");
        for (var id : new String[]{"M001", "M002", "M003"}) {
            registry.findById(id).ifPresent(m -> {
                String tierSummary = switch (m.tier()) {
                    case MemberTier.Standard()       -> "up to 5 items";
                    case MemberTier.Premium()         -> "up to 15 items";
                    case MemberTier.Staff(var dept)  -> "unlimited (dept: %s)".formatted(dept);
                };
                System.out.printf("  %-20s can borrow %s%n", m.name(), tierSummary);
            });
        }
    }
}
