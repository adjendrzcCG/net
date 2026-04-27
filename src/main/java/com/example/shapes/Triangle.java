package com.example.shapes;

/**
 * Represents a triangle shape.
 * Java 11-era pattern: concrete class extending abstract base.
 */
public class Triangle extends Shape {

    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC, String color) {
        super(color);
        if (!isValid(sideA, sideB, sideC)) {
            throw new IllegalArgumentException(
                "Invalid triangle: sides " + sideA + ", " + sideB + ", " + sideC);
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    private static boolean isValid(double a, double b, double c) {
        return a > 0 && b > 0 && c > 0 &&
               a + b > c && a + c > b && b + c > a;
    }

    public double getSideA() { return sideA; }
    public double getSideB() { return sideB; }
    public double getSideC() { return sideC; }

    @Override
    public double area() {
        // Heron's formula
        double s = (sideA + sideB + sideC) / 2.0;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double perimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String describe() {
        return "Triangle with sides " + sideA + ", " + sideB + ", " + sideC +
               " and color " + getColor();
    }

    @Override
    public String toString() {
        return "Triangle{sideA=" + sideA + ", sideB=" + sideB +
               ", sideC=" + sideC + ", color=" + getColor() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Triangle triangle = (Triangle) obj;
        return Double.compare(triangle.sideA, sideA) == 0 &&
               Double.compare(triangle.sideB, sideB) == 0 &&
               Double.compare(triangle.sideC, sideC) == 0 &&
               getColor().equals(triangle.getColor());
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(sideA);
        result = 31 * result + Double.hashCode(sideB);
        result = 31 * result + Double.hashCode(sideC);
        result = 31 * result + getColor().hashCode();
        return result;
    }
}
