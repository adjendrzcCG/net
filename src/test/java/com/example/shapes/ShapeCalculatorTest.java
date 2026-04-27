package com.example.shapes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Shape Calculator application.
 */
class ShapeCalculatorTest {

    private ShapeCalculator calculator;
    private List<Shape> shapes;

    @BeforeEach
    void setUp() {
        calculator = new ShapeCalculator();
        shapes = Arrays.asList(
            new Circle(5.0, "red"),
            new Rectangle(4.0, 6.0, "blue"),
            new Triangle(3.0, 4.0, 5.0, "green")
        );
    }

    @Test
    void testCircleArea() {
        Circle circle = new Circle(5.0, "red");
        assertEquals(Math.PI * 25, circle.area(), 1e-9);
    }

    @Test
    void testCirclePerimeter() {
        Circle circle = new Circle(5.0, "red");
        assertEquals(2 * Math.PI * 5, circle.perimeter(), 1e-9);
    }

    @Test
    void testRectangleArea() {
        Rectangle rect = new Rectangle(4.0, 6.0, "blue");
        assertEquals(24.0, rect.area(), 1e-9);
    }

    @Test
    void testRectanglePerimeter() {
        Rectangle rect = new Rectangle(4.0, 6.0, "blue");
        assertEquals(20.0, rect.perimeter(), 1e-9);
    }

    @Test
    void testTriangleArea() {
        // 3-4-5 right triangle: area = 0.5 * 3 * 4 = 6
        Triangle triangle = new Triangle(3.0, 4.0, 5.0, "green");
        assertEquals(6.0, triangle.area(), 1e-9);
    }

    @Test
    void testTrianglePerimeter() {
        Triangle triangle = new Triangle(3.0, 4.0, 5.0, "green");
        assertEquals(12.0, triangle.perimeter(), 1e-9);
    }

    @Test
    void testInvalidTriangleThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Triangle(1.0, 2.0, 10.0, "red"));
    }

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
        Shape largest = calculator.largestByArea(shapes);
        assertNotNull(largest);
        assertInstanceOf(Circle.class, largest);
    }

    @Test
    void testLargestByAreaEmptyList() {
        assertNull(calculator.largestByArea(List.of()));
    }

    @Test
    void testFilterByType() {
        List<Shape> circles = calculator.filterByType(shapes, "Circle");
        assertEquals(1, circles.size());
        assertInstanceOf(Circle.class, circles.get(0));

        List<Shape> rects = calculator.filterByType(shapes, "Rectangle");
        assertEquals(1, rects.size());
        assertInstanceOf(Rectangle.class, rects.get(0));
    }

    @Test
    void testFormatShapeSummary() {
        String summary = calculator.formatShapeSummary(new Circle(3.0, "red"));
        assertTrue(summary.contains("Circle"));
        assertTrue(summary.contains("red"));
        assertTrue(summary.contains("radius=3.0"));
    }

    @Test
    void testClassifyCircle() {
        assertEquals("tiny circle", calculator.classifyShape(new Circle(0.5, "red")));
        assertEquals("medium circle", calculator.classifyShape(new Circle(5.0, "red")));
        assertEquals("large circle", calculator.classifyShape(new Circle(15.0, "red")));
    }

    @Test
    void testClassifyRectangle() {
        assertEquals("square", calculator.classifyShape(new Rectangle(4.0, 4.0, "blue")));
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

    @Test
    void testCircleEquality() {
        Circle c1 = new Circle(5.0, "red");
        Circle c2 = new Circle(5.0, "red");
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testDistinctColors() {
        List<String> colors = ShapeReporter.distinctColors(shapes);
        assertEquals(3, colors.size());
        assertTrue(colors.contains("red"));
        assertTrue(colors.contains("blue"));
        assertTrue(colors.contains("green"));
    }

    @Test
    void testGenerateReport() {
        String report = ShapeReporter.generateReport(shapes);
        assertTrue(report.contains("Shape Report"));
        assertTrue(report.contains("Total shapes: 3"));
    }
}
