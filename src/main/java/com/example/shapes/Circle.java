package com.example.shapes;

/**
 * Represents a circle shape.
 * Java 11-era pattern: concrete class extending abstract base.
 */
public class Circle extends Shape {

    private double radius;

    public Circle(double radius, String color) {
        super(color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String describe() {
        return "Circle with radius " + radius + " and color " + getColor();
    }

    @Override
    public String toString() {
        return "Circle{radius=" + radius + ", color=" + getColor() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Circle circle = (Circle) obj;
        return Double.compare(circle.radius, radius) == 0 &&
               getColor().equals(circle.getColor());
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(radius);
        result = 31 * result + getColor().hashCode();
        return result;
    }
}
