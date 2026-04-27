package com.example.shapes;

/**
 * Immutable record representing a triangle defined by its three side lengths.
 *
 * <p>Java 21 modernization: replaces the mutable class with a record.
 * Compact constructor validates the triangle inequality.
 */
public record Triangle(double sideA, double sideB, double sideC, String color)
        implements Shape {

    public Triangle {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException(
                "All sides must be positive, got: %s, %s, %s".formatted(sideA, sideB, sideC));
        }
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new IllegalArgumentException(
                "Invalid triangle inequality for sides: %s, %s, %s".formatted(sideA, sideB, sideC));
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color must not be blank");
        }
    }

    @Override
    public double area() {
        // Heron's formula
        var s = (sideA + sideB + sideC) / 2.0;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double perimeter() {
        return sideA + sideB + sideC;
    }

    /** Classifies this triangle by its sides. */
    public TriangleKind kind() {
        if (Double.compare(sideA, sideB) == 0 && Double.compare(sideB, sideC) == 0) {
            return TriangleKind.EQUILATERAL;
        }
        if (Double.compare(sideA, sideB) == 0
                || Double.compare(sideB, sideC) == 0
                || Double.compare(sideA, sideC) == 0) {
            return TriangleKind.ISOSCELES;
        }
        return TriangleKind.SCALENE;
    }

    /** Classification of a triangle by its side lengths. */
    public enum TriangleKind {
        EQUILATERAL, ISOSCELES, SCALENE
    }
}
