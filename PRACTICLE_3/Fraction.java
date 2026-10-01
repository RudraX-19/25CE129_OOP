// ============================================================
//  PRACTICLE 3 - Part A - Task 3: Fractions (Advanced)
//  File: PRACTICLE_3/Fraction.java
// ============================================================

import java.util.Objects;

class Fraction {
    private int num;
    private int den;
    
    public Fraction(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        // Reduce to lowest terms
        int g = gcd(Math.abs(num), Math.abs(den));
        this.num = num / g;
        this.den = den / g;
        
        // Keep denominator positive
        if (this.den < 0) {
            this.num = -this.num;
            this.den = -this.den;
        }
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    @Override
    public String toString() {
        return num + "/" + den;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction fraction = (Fraction) obj;
        return num == fraction.num && den == fraction.den;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}

public class FractionTest {
    public static void main(String[] args) {
        System.out.println("=== Fraction Equivalence Test ===\n");
        
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);
        Fraction f4 = new Fraction(4, 8);
        Fraction f5 = new Fraction(2, 3);
        
        System.out.println("Created fractions:");
        System.out.println("f1 = new Fraction(1, 2) → " + f1);
        System.out.println("f2 = new Fraction(2, 4) → " + f2);
        System.out.println("f3 = new Fraction(3, 6) → " + f3);
        System.out.println("f4 = new Fraction(4, 8) → " + f4);
        System.out.println("f5 = new Fraction(2, 3) → " + f5);
        
        System.out.println("\nEquality tests:");
        System.out.println("f1.equals(f2): " + f1.equals(f2));
        System.out.println("f2.equals(f3): " + f2.equals(f3));
        System.out.println("f3.equals(f4): " + f3.equals(f4));
        System.out.println("f1.equals(f5): " + f1.equals(f5));
        
        System.out.println("\nAll equivalent fractions (1/2, 2/4, 3/6, 4/8) are reduced to: " + f1);
        System.out.println("They all print the same and are equal to each other!");
    }
}
