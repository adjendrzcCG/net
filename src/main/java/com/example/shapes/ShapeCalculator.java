package com.example.shapes;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Calculates statistics over a collection of shapes.
 *
 * <p>Java 21 modernization highlights:
 * <ul>
 *   <li>Stream API with method references replaces hand-written loops</li>
 *   <li>Pattern matching {@code switch} replaces {@code if-instanceof} chains</li>
 *   <li>{@code Optional} replaces nullable return from {@code largestByArea}</li>
 *   <li>Text blocks for multi-line string templates</li>
 *   <li>{@code var} for local type inference</li>
 * </ul>
 */
public class ShapeCalculator {

    /** Returns the total area of all shapes in the list. */
    public double totalArea(List<Shape> shapes) {
        return shapes.stream()
                .mapToDouble(Shape::area)
                .sum();
    }

    /** Returns the total perimeter of all shapes in the list. */
    public double totalPerimeter(List<Shape> shapes) {
        return shapes.stream()
                .mapToDouble(Shape::perimeter)
                .sum();
    }

    /**
     * Returns the shape with the largest area, or {@link Optional#empty()} if
     * the list is empty.
     */
    public Optional<Shape> largestByArea(List<Shape> shapes) {
        return shapes.stream()
                .max(Comparator.comparingDouble(Shape::area));
    }

    /**
     * Filters shapes by their concrete type.
     *
     * <p>Java 21 modernization: uses {@code instanceof} pattern variable and
     * {@code Stream.filter} instead of explicit loops.
     */
    public <T extends Shape> List<T> filterByType(List<Shape> shapes, Class<T> type) {
        return shapes.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    /**
     * Returns a formatted summary of a shape.
     *
     * <p>Java 21 modernization: pattern matching {@code switch} with guarded
     * patterns and text block template replaces the old {@code if-instanceof} chain.
     */
    public String formatShapeSummary(Shape shape) {
        var dimensions = switch (shape) {
            case Circle c       -> "radius=%.4f".formatted(c.radius());
            case Rectangle r    -> "width=%.4f, height=%.4f".formatted(r.width(), r.height());
            case Triangle t     -> "sides=%.4f/%.4f/%.4f".formatted(t.sideA(), t.sideB(), t.sideC());
        };

        return """
                Shape: %s
                  Color:     %s
                  Dims:      %s
                  Area:      %.4f
                  Perimeter: %.4f"""
                .formatted(
                    shape.getClass().getSimpleName(),
                    shape.color(),
                    dimensions,
                    shape.area(),
                    shape.perimeter());
    }

    /**
     * Classifies a shape into a human-readable category.
     *
     * <p>Java 21 modernization: uses pattern matching {@code switch} with
     * guarded patterns ({@code when} clauses) to cleanly combine type checks
     * and property conditions in a single expression.
     */
    public String classifyShape(Shape shape) {
        return switch (shape) {
            case Circle c when c.radius() < 1.0  -> "tiny circle";
            case Circle c when c.radius() < 10.0 -> "medium circle";
            case Circle c                         -> "large circle";
            case Rectangle r when r.isSquare()   -> "square";
            case Rectangle r                      -> "rectangle";
            case Triangle t when t.kind() == Triangle.TriangleKind.EQUILATERAL -> "equilateral triangle";
            case Triangle t when t.kind() == Triangle.TriangleKind.ISOSCELES   -> "isosceles triangle";
            case Triangle t                        -> "scalene triangle";
        };
    }
}
