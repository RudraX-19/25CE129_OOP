// ============================================================
//  PRACTICLE 8 - Part A - Task 1: Guarded Calculator
//  Custom DivideByZeroException; catches invalid-number input
//  separately; finally logs each attempt; loops until success
//  File: PRACTICLE_8/GuardedCalculator.java
// ============================================================

import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    public static double calculate(double a, String op, double b)
            throws DivideByZeroException {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new DivideByZeroException(
                    "Cannot divide " + a + " by zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException(
                "Unknown operator: " + op);
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int attempt = 0;

        System.out.println("=== Guarded Calculator ===");
        System.out.println("Enter: <number> <op> <number>  (e.g. 10 / 2)");
        System.out.println("Type 'quit' to exit.\n");

        while (true) {
            attempt++;
            System.out.print("Expression: ");
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("quit")) break;

            try {
                String[] parts = line.split("\\s+");
                if (parts.length != 3) throw new IllegalArgumentException(
                    "Expected: <number> <op> <number>");

                double a  = Double.parseDouble(parts[0]);
                String op = parts[1];
                double b  = Double.parseDouble(parts[2]);

                double result = calculate(a, op, b);
                System.out.println("Result: " + result);
                break;                         // success — exit loop

            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + e.getMessage());
            } catch (DivideByZeroException e) {
                System.out.println("Math error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } finally {
                System.out.println("[LOG] Attempt " + attempt + " completed.\n");
            }
        }

        sc.close();
    }
}
