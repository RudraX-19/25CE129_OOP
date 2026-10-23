// ============================================================
//  PRACTICLE 5 - Part A - Task 1: Shape Areas
//  Abstract Shape with abstract area(); subclasses Circle, Rectangle, Triangle
//  File: PRACTICLE_5/ShapeAreas.java
// ============================================================

abstract class Shape {
    public abstract double area();

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

class Triangle extends Shape {
    private double base, height;

    public Triangle(double base, double height) {
        this.base  = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}

public class ShapeAreas {
    public static void main(String[] args) {

        // Mixed array — polymorphism lets us treat them all as Shape
        Shape[] shapes = {
            new Circle(7),
            new Rectangle(4, 9),
            new Triangle(6, 8),
            new Circle(3),
            new Rectangle(10, 2),
            new Triangle(5, 12)
        };

        double runningTotal = 0;
        Shape largest = shapes[0];

        System.out.println("=== Shape Area Report ===");
        System.out.printf("%-15s %12s %15s%n", "Shape", "Area", "Running Total");
        System.out.println("-".repeat(45));

        // ONE loop handles every shape through polymorphism
        for (Shape s : shapes) {
            double a = s.area();
            runningTotal += a;

            if (a > largest.area()) {
                largest = s;
            }

            System.out.printf("%-15s %12.2f %15.2f%n", s, a, runningTotal);
        }

        System.out.println("-".repeat(45));
        System.out.printf("Total area of all shapes : %.2f%n", runningTotal);
        System.out.printf("Largest shape            : %s  (area = %.2f)%n",
                          largest, largest.area());
    }
}
