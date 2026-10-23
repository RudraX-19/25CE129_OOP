// ============================================================
//  PRACTICLE 6 - Part A - Task 3: Discount Engine (Advanced)
//  Each discount rule is a lambda; user picks one at runtime
//  and applies it to a list of prices
//  File: PRACTICLE_6/DiscountEngine.java
// ============================================================

import java.util.Scanner;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Discount Engine ===\n");

        double[] prices = { 1200.0, 450.0, 3500.0, 89.0, 670.0 };

        // Define discount rules as lambdas
        DiscountRule[] rules = {
            price -> price,                              // 0 — no discount
            price -> price * 0.90,                      // 1 — 10% off
            price -> price * 0.80,                      // 2 — 20% off
            price -> price > 500 ? price * 0.85 : price // 3 — 15% off items above Rs 500
        };

        String[] ruleNames = {
            "No discount",
            "10% off all items",
            "20% off all items",
            "15% off items above Rs 500"
        };

        System.out.println("Available discount rules:");
        for (int i = 0; i < ruleNames.length; i++) {
            System.out.printf("  %d. %s%n", i, ruleNames[i]);
        }

        System.out.print("\nSelect rule (0-3): ");
        int choice = sc.nextInt();

        if (choice < 0 || choice >= rules.length) {
            System.out.println("Invalid choice. Using no discount.");
            choice = 0;
        }

        DiscountRule selected = rules[choice];
        System.out.println("\nApplying: " + ruleNames[choice]);
        System.out.println("-".repeat(45));
        System.out.printf("%-15s %-15s %-15s%n", "Original (Rs)", "Discount (Rs)", "Final (Rs)");
        System.out.println("-".repeat(45));

        double totalOriginal  = 0;
        double totalFinal     = 0;

        for (double price : prices) {
            double finalPrice = selected.apply(price);
            double discount   = price - finalPrice;
            totalOriginal    += price;
            totalFinal       += finalPrice;
            System.out.printf("%-15.2f %-15.2f %-15.2f%n", price, discount, finalPrice);
        }

        System.out.println("-".repeat(45));
        System.out.printf("Total saved : Rs %.2f%n", totalOriginal - totalFinal);
        System.out.printf("Amount to pay: Rs %.2f%n", totalFinal);

        sc.close();
    }
}
