// ============================================================
//  PRACTICLE 3 - Part A - Task 1: Distinct Points
//  File: PRACTICLE_3/Point.java
// ============================================================

import java.util.Objects;

class Point {
    private int x;
    private int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class DistinctPoints {
    public static void main(String[] args) {
        System.out.println("=== Distinct Points Counter ===\n");
        
        Point[] points = {
            new Point(1, 2),
            new Point(3, 4),
            new Point(1, 2),  // duplicate
            new Point(5, 6),
            new Point(3, 4),  // duplicate
            new Point(7, 8)
        };
        
        System.out.println("All points:");
        for (Point p : points) {
            System.out.println(p);
        }
        
        // Count distinct points
        int distinctCount = 0;
        for (int i = 0; i < points.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                distinctCount++;
            }
        }
        
        System.out.println("\nTotal points: " + points.length);
        System.out.println("Distinct: " + distinctCount);
    }
}
