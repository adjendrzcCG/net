package com.example.shapes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for reporting shape information.
 *
 * <p>Java 21 modernization highlights:
 * <ul>
 *   <li>Streams + collectors replace hand-rolled accumulation loops</li>
 *   <li>Text block for the report header</li>
 *   <li>{@code List.copyOf} via {@code Stream.toList()} for immutable results</li>
 *   <li>{@code Map.Entry} traversal replaced by {@code forEach}</li>
 * </ul>
 */
public final class ShapeReporter {

    private ShapeReporter() {}   // utility class — no instances

    /**
     * Generates a full report of a list of shapes.
     *
     * <p>Uses a text block for the static header and
     * {@code String.join} / stream collectors for the dynamic sections.
     */
    public static String generateReport(List<Shape> shapes) {
        var calculator = new ShapeCalculator();

        var typeCounts = shapes.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getClass().getSimpleName(),
                        Collectors.counting()));

        var byTypeLines = typeCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> "  %s: %d".formatted(e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));

        var detailLines = shapes.stream()
                .map(s -> "  " + calculator.formatShapeSummary(s).replace("\n", "\n  ") + "\n  ---")
                .collect(Collectors.joining("\n"));

        return """
                === Shape Report ===
                Total shapes:    %d
                Total area:      %.4f
                Total perimeter: %.4f

                By type:
                %s

                Details:
                %s
                """.formatted(
                shapes.size(),
                calculator.totalArea(shapes),
                calculator.totalPerimeter(shapes),
                byTypeLines,
                detailLines);
    }

    /**
     * Returns an immutable, deduplicated list of colors used by the shapes,
     * preserving encounter order.
     *
     * <p>Java 21 modernization: a single stream pipeline with a linked-set
     * collector replaces the manual {@code contains}-before-{@code add} loop.
     */
    public static List<String> distinctColors(List<Shape> shapes) {
        return shapes.stream()
                .map(Shape::color)
                .distinct()
                .toList();
    }
}
