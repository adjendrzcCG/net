package com.example.shapes;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates statistics over a collection of shapes.
 * Java 11-era pattern: traditional if-else chains, verbose loops.
 */
public class ShapeCalculator {

    /**
     * Returns the total area of all shapes in the list.
     */
    public double totalArea(List<Shape> shapes) {
        double total = 0;
        for (Shape shape : shapes) {
            total = total + shape.area();
        }
        return total;
    }

    /**
     * Returns the total perimeter of all shapes in the list.
     */
    public double totalPerimeter(List<Shape> shapes) {
        double total = 0;
        for (Shape shape : shapes) {
            total = total + shape.perimeter();
        }
        return total;
    }

    /**
     * Returns the shape with the largest area.
     * Returns null if the list is empty.
     */
    public Shape largestByArea(List<Shape> shapes) {
        if (shapes == null || shapes.isEmpty()) {
            return null;
        }
        Shape largest = shapes.get(0);
        for (int i = 1; i < shapes.size(); i++) {
            Shape current = shapes.get(i);
            if (current.area() > largest.area()) {
                largest = current;
            }
        }
        return largest;
    }

    /**
     * Filters shapes by type name.
     * Java 11-era pattern: instanceof checks with explicit casts.
     */
    public List<Shape> filterByType(List<Shape> shapes, String typeName) {
        List<Shape> result = new ArrayList<>();
        for (Shape shape : shapes) {
            if ("Circle".equals(typeName) && shape instanceof Circle) {
                result.add(shape);
            } else if ("Rectangle".equals(typeName) && shape instanceof Rectangle) {
                result.add(shape);
            } else if ("Triangle".equals(typeName) && shape instanceof Triangle) {
                result.add(shape);
            }
        }
        return result;
    }

    /**
     * Returns a formatted summary of a shape.
     * Java 11-era pattern: old-style switch statement, string concatenation.
     */
    public String formatShapeSummary(Shape shape) {
        String typeName;
        String dimensions;

        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            typeName = "Circle";
            dimensions = "radius=" + c.getRadius();
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            typeName = "Rectangle";
            dimensions = "width=" + r.getWidth() + ", height=" + r.getHeight();
        } else if (shape instanceof Triangle) {
            Triangle t = (Triangle) shape;
            typeName = "Triangle";
            dimensions = "sides=" + t.getSideA() + "/" + t.getSideB() + "/" + t.getSideC();
        } else {
            typeName = "Unknown";
            dimensions = "n/a";
        }

        return "Shape: " + typeName + "\n" +
               "  Color:     " + shape.getColor() + "\n" +
               "  Dims:      " + dimensions + "\n" +
               "  Area:      " + String.format("%.4f", shape.area()) + "\n" +
               "  Perimeter: " + String.format("%.4f", shape.perimeter());
    }

    /**
     * Classifies a shape into a category.
     * Java 11-era pattern: traditional if-else chain.
     */
    public String classifyShape(Shape shape) {
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            if (c.getRadius() < 1.0) {
                return "tiny circle";
            } else if (c.getRadius() < 10.0) {
                return "medium circle";
            } else {
                return "large circle";
            }
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            if (r.getWidth() == r.getHeight()) {
                return "square";
            } else {
                return "rectangle";
            }
        } else if (shape instanceof Triangle) {
            Triangle t = (Triangle) shape;
            if (t.getSideA() == t.getSideB() && t.getSideB() == t.getSideC()) {
                return "equilateral triangle";
            } else if (t.getSideA() == t.getSideB() || t.getSideB() == t.getSideC()
                    || t.getSideA() == t.getSideC()) {
                return "isosceles triangle";
            } else {
                return "scalene triangle";
            }
        }
        return "unknown shape";
    }
}
