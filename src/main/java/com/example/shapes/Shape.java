package com.example.shapes;

/**
 * Sealed interface representing a geometric shape.
 *
 * <p>Java 21 modernization: replaces the abstract class hierarchy with a sealed
 * interface whose permitted subtypes are all records. This gives us:
 * <ul>
 *   <li>Exhaustive pattern matching in switch expressions</li>
 *   <li>Immutable, null-safe value types via records</li>
 *   <li>No manual equals/hashCode/toString boilerplate</li>
 * </ul>
 */
public sealed interface Shape permits Circle, Rectangle, Triangle {

    String color();

    double area();

    double perimeter();

    default String describe() {
        return switch (this) {
            case Circle c       -> "Circle with radius %.2f and color %s".formatted(c.radius(), c.color());
            case Rectangle r    -> "Rectangle with width %.2f, height %.2f and color %s"
                                       .formatted(r.width(), r.height(), r.color());
            case Triangle t     -> "Triangle with sides %.2f/%.2f/%.2f and color %s"
                                       .formatted(t.sideA(), t.sideB(), t.sideC(), t.color());
        };
    }
}
