package com.example.shapes;

/**
 * Immutable record representing a circle.
 *
 * <p>Java 21 modernization: replaces the mutable class with a record.
 * Records automatically provide:
 * <ul>
 *   <li>Canonical constructor and compact validation</li>
 *   <li>Accessor methods ({@code radius()}, {@code color()})</li>
 *   <li>Correct {@code equals}, {@code hashCode}, and {@code toString}</li>
 * </ul>
 */
public record Circle(double radius, String color) implements Shape {

    /** Compact constructor — validates inputs before the record is created. */
    public Circle {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive, got: " + radius);
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color must not be blank");
        }
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}
