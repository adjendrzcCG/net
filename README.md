# Library Management System

A modern **Java 21** library management application demonstrating the latest
Java language features and idioms.

## Requirements

| Tool  | Version |
|-------|---------|
| JDK   | 21+     |
| Maven | 3.9+    |

## Build & Test

```bash
mvn clean verify
```

## Run the demo

```bash
MAVEN_OPTS="--enable-preview" \
  mvn compile exec:java -Dexec.mainClass=com.example.library.LibraryApp
```

## Java 21 features showcased

| Feature | Where used |
|---|---|
| **Records** | `Book`, `Member`, `LoanService.LoanEvent`, all `ItemStatus` variants |
| **Sealed interfaces + classes** | `ItemStatus`, `Member.MemberTier` |
| **Pattern matching for `switch`** | `ItemStatus.describe()`, `LoanService.checkOut()`, `LibraryApp` |
| **Record deconstruction patterns** | `ItemStatus.CheckedOut(var m, var d)` in switch arms |
| **Text blocks** | `Book.summary()`, `LibraryApp.main()` |
| **`Stream.toList()`** (Java 16+) | `Catalogue.searchByTitle()`, `availableBooks()`, … |
| **Local-variable type inference (`var`)** | Throughout service and app layers |
| **Sequenced Collections (`Deque`)** | `LoanService.auditLog` |
| **`String.formatted()`** | `ItemStatus.describe()`, `Member.MemberTier.label()` |

## Project structure

```
src/
├── main/java/com/example/library/
│   ├── Book.java              # Immutable record — catalogue entry
│   ├── Catalogue.java         # In-memory book store
│   ├── ItemStatus.java        # Sealed interface for book availability
│   ├── LibraryApp.java        # Demo entry point
│   ├── LoanService.java       # Checkout / return / reserve logic
│   ├── Member.java            # Immutable record — library member
│   └── MemberRegistry.java    # In-memory member store
└── test/java/com/example/library/
    ├── BookTest.java
    ├── CatalogueTest.java
    ├── ItemStatusTest.java
    ├── LoanServiceTest.java
    ├── MemberRegistryTest.java
    └── MemberTest.java
```