package com.example.shapes;

/**
 * Immutable record representing a rectangle.
 *
 * <p>Java 21 modernization: replaces the mutable class with a record.
 * The compact constructor validates that width and height are positive,
 * eliminating the need for separate setter guards.
 */
public record Rectangle(double width, double height, String color) implements Shape {

    public Rectangle {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                "Width and height must be positive, got: %s x %s".formatted(width, height));
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color must not be blank");
        }
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    /** Returns {@code true} if this rectangle is a square. */
    public boolean isSquare() {
        return Double.compare(width, height) == 0;
    }
}
