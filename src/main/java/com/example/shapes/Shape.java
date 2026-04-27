package com.example.shapes;

/**
 * Abstract base class for all geometric shapes.
 * Java 11-era pattern: abstract class hierarchy with concrete subclasses.
 */
public abstract class Shape {

    private String color;

    public Shape(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public abstract double area();

    public abstract double perimeter();

    public abstract String describe();
}
