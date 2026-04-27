package com.example.shapes;

/**
 * Represents a rectangle shape.
 * Java 11-era pattern: concrete class extending abstract base.
 */
public class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle(double width, double height, String color) {
        super(color);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public String describe() {
        return "Rectangle with width " + width + ", height " + height +
               " and color " + getColor();
    }

    @Override
    public String toString() {
        return "Rectangle{width=" + width + ", height=" + height +
               ", color=" + getColor() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rectangle rectangle = (Rectangle) obj;
        return Double.compare(rectangle.width, width) == 0 &&
               Double.compare(rectangle.height, height) == 0 &&
               getColor().equals(rectangle.getColor());
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(width);
        result = 31 * result + Double.hashCode(height);
        result = 31 * result + getColor().hashCode();
        return result;
    }
}
