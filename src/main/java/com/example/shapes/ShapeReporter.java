package com.example.shapes;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Utility class for reporting shape information.
 * Java 11-era pattern: static utility methods, traditional collections.
 */
public class ShapeReporter {

    /**
     * Generates a full report of a list of shapes.
     * Java 11-era pattern: StringBuilder, old-style loops.
     */
    public static String generateReport(List<Shape> shapes) {
        ShapeCalculator calculator = new ShapeCalculator();

        StringBuilder sb = new StringBuilder();
        sb.append("=== Shape Report ===\n");
        sb.append("Total shapes: ").append(shapes.size()).append("\n");
        sb.append("Total area: ").append(
            String.format("%.4f", calculator.totalArea(shapes))).append("\n");
        sb.append("Total perimeter: ").append(
            String.format("%.4f", calculator.totalPerimeter(shapes))).append("\n");
        sb.append("\n");

        Map<String, Integer> typeCounts = new HashMap<>();
        for (Shape shape : shapes) {
            String name = shape.getClass().getSimpleName();
            Integer count = typeCounts.get(name);
            if (count == null) {
                typeCounts.put(name, 1);
            } else {
                typeCounts.put(name, count + 1);
            }
        }

        sb.append("By type:\n");
        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            sb.append("  ").append(entry.getKey())
              .append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("\nDetails:\n");
        for (Shape shape : shapes) {
            sb.append("  ").append(calculator.formatShapeSummary(shape))
              .append("\n  ---\n");
        }

        return sb.toString();
    }

    /**
     * Returns a list of colors used by the shapes.
     * Java 11-era pattern: manual deduplication with ArrayList.
     */
    public static List<String> distinctColors(List<Shape> shapes) {
        List<String> colors = new ArrayList<>();
        for (Shape shape : shapes) {
            String color = shape.getColor();
            if (!colors.contains(color)) {
                colors.add(color);
            }
        }
        return colors;
    }
}
