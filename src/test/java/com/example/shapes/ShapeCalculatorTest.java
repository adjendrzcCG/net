package com.example.shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Shape Calculator application.
 *
 * <p>Updated for Java 21: uses record accessors (e.g. {@code radius()} instead
 * of {@code getRadius()}), and the new {@code Optional}-returning
 * {@code largestByArea} and generic {@code filterByType} APIs.
 */
class ShapeCalculatorTest {

    private ShapeCalculator calculator;
    private List<Shape> shapes;

    @BeforeEach
    void setUp() {
        calculator = new ShapeCalculator();
        shapes = List.of(
            new Circle(5.0, "red"),
            new Rectangle(4.0, 6.0, "blue"),
            new Triangle(3.0, 4.0, 5.0, "green")
        );
    }

    // ── Circle ──────────────────────────────────────────────────────────────

    @Test
    void testCircleArea() {
        var circle = new Circle(5.0, "red");
        assertEquals(Math.PI * 25, circle.area(), 1e-9);
    }

    @Test
    void testCirclePerimeter() {
        var circle = new Circle(5.0, "red");
        assertEquals(2 * Math.PI * 5, circle.perimeter(), 1e-9);
    }

    @Test
    void testCircleValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(-1.0, "red"));
        assertThrows(IllegalArgumentException.class, () -> new Circle(1.0, "  "));
    }

    // ── Rectangle ───────────────────────────────────────────────────────────

    @Test
    void testRectangleArea() {
        var rect = new Rectangle(4.0, 6.0, "blue");
        assertEquals(24.0, rect.area(), 1e-9);
    }

    @Test
    void testRectanglePerimeter() {
        var rect = new Rectangle(4.0, 6.0, "blue");
        assertEquals(20.0, rect.perimeter(), 1e-9);
    }

    @Test
    void testRectangleIsSquare() {
        assertTrue(new Rectangle(4.0, 4.0, "blue").isSquare());
        assertFalse(new Rectangle(4.0, 6.0, "blue").isSquare());
    }

    // ── Triangle ────────────────────────────────────────────────────────────

    @Test
    void testTriangleArea() {
        // 3-4-5 right triangle: area = 0.5 * base * height = 6
        var triangle = new Triangle(3.0, 4.0, 5.0, "green");
        assertEquals(6.0, triangle.area(), 1e-9);
    }

    @Test
    void testTrianglePerimeter() {
        var triangle = new Triangle(3.0, 4.0, 5.0, "green");
        assertEquals(12.0, triangle.perimeter(), 1e-9);
    }

    @Test
    void testInvalidTriangleThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(1.0, 2.0, 10.0, "red"));
    }

    @Test
    void testTriangleKind() {
        assertEquals(Triangle.TriangleKind.EQUILATERAL,
            new Triangle(5.0, 5.0, 5.0, "green").kind());
        assertEquals(Triangle.TriangleKind.ISOSCELES,
            new Triangle(5.0, 5.0, 6.0, "green").kind());
        assertEquals(Triangle.TriangleKind.SCALENE,
            new Triangle(3.0, 4.0, 5.0, "green").kind());
    }

    // ── Calculator ──────────────────────────────────────────────────────────

    @Test
    void testTotalArea() {
        double expected = Math.PI * 25 + 24.0 + 6.0;
        assertEquals(expected, calculator.totalArea(shapes), 1e-9);
    }

    @Test
    void testTotalPerimeter() {
        double expected = 2 * Math.PI * 5 + 20.0 + 12.0;
        assertEquals(expected, calculator.totalPerimeter(shapes), 1e-9);
    }

    @Test
    void testLargestByArea() {
        Optional<Shape> largest = calculator.largestByArea(shapes);
        assertTrue(largest.isPresent());
        assertInstanceOf(Circle.class, largest.get());
    }

    @Test
    void testLargestByAreaEmptyList() {
        assertTrue(calculator.largestByArea(List.of()).isEmpty());
    }

    @Test
    void testFilterByType() {
        var circles = calculator.filterByType(shapes, Circle.class);
        assertEquals(1, circles.size());
        assertInstanceOf(Circle.class, circles.getFirst());

        var rects = calculator.filterByType(shapes, Rectangle.class);
        assertEquals(1, rects.size());
        assertInstanceOf(Rectangle.class, rects.getFirst());
    }

    @Test
    void testFormatShapeSummary() {
        String summary = calculator.formatShapeSummary(new Circle(3.0, "red"));
        assertTrue(summary.contains("Circle"));
        assertTrue(summary.contains("red"));
        assertTrue(summary.contains("radius=3.0000"));
    }

    @Test
    void testClassifyCircle() {
        assertEquals("tiny circle",   calculator.classifyShape(new Circle(0.5, "red")));
        assertEquals("medium circle", calculator.classifyShape(new Circle(5.0, "red")));
        assertEquals("large circle",  calculator.classifyShape(new Circle(15.0, "red")));
    }

    @Test
    void testClassifyRectangle() {
        assertEquals("square",    calculator.classifyShape(new Rectangle(4.0, 4.0, "blue")));
        assertEquals("rectangle", calculator.classifyShape(new Rectangle(4.0, 6.0, "blue")));
    }

    @Test
    void testClassifyTriangle() {
        assertEquals("equilateral triangle",
            calculator.classifyShape(new Triangle(5.0, 5.0, 5.0, "green")));
        assertEquals("isosceles triangle",
            calculator.classifyShape(new Triangle(5.0, 5.0, 6.0, "green")));
        assertEquals("scalene triangle",
            calculator.classifyShape(new Triangle(3.0, 4.0, 5.0, "green")));
    }

    // ── Records ─────────────────────────────────────────────────────────────

    @Test
    void testCircleEquality() {
        // Record equality is structural
        var c1 = new Circle(5.0, "red");
        var c2 = new Circle(5.0, "red");
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testCircleAccessors() {
        var c = new Circle(3.5, "blue");
        assertEquals(3.5, c.radius());
        assertEquals("blue", c.color());
    }

    // ── Reporter ────────────────────────────────────────────────────────────

    @Test
    void testDistinctColors() {
        var colors = ShapeReporter.distinctColors(shapes);
        assertEquals(3, colors.size());
        assertTrue(colors.contains("red"));
        assertTrue(colors.contains("blue"));
        assertTrue(colors.contains("green"));
    }

    @Test
    void testGenerateReport() {
        String report = ShapeReporter.generateReport(shapes);
        assertTrue(report.contains("Shape Report"));
        assertTrue(report.contains("Total shapes:    3"));
    }

    // ── Describe (sealed interface default method) ───────────────────────────

    @Test
    void testDescribeCircle() {
        String desc = new Circle(5.0, "red").describe();
        assertTrue(desc.contains("Circle"));
        assertTrue(desc.contains("red"));
    }

    @Test
    void testDescribeRectangle() {
        String desc = new Rectangle(4.0, 6.0, "blue").describe();
        assertTrue(desc.contains("Rectangle"));
        assertTrue(desc.contains("blue"));
    }

    @Test
    void testDescribeTriangle() {
        String desc = new Triangle(3.0, 4.0, 5.0, "green").describe();
        assertTrue(desc.contains("Triangle"));
        assertTrue(desc.contains("green"));
    }
}
