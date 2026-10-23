// ============================================================
//  PRACTICLE 13 - Part A - Task 2: Bounded Stats
//  Generic method <N extends Number> double average(N[] values)
//  works for Integer[], Double[], Long[]; refuses non-Number types
//  File: PRACTICLE_13/BoundedStats.java
// ============================================================

public class BoundedStats {

    // Bounded type parameter — only Number subtypes accepted
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("Array must not be empty");

        double sum = 0;
        for (N n : values) {
            sum += n.doubleValue();   // Number provides doubleValue()
        }
        return sum / values.length;
    }

    public static <N extends Number> N max(N[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("Array must not be empty");

        N maxVal = values[0];
        for (N n : values) {
            if (n.doubleValue() > maxVal.doubleValue()) {
                maxVal = n;
            }
        }
        return maxVal;
    }

    public static <N extends Number> N min(N[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("Array must not be empty");

        N minVal = values[0];
        for (N n : values) {
            if (n.doubleValue() < minVal.doubleValue()) {
                minVal = n;
            }
        }
        return minVal;
    }

    public static void main(String[] args) {
        System.out.println("=== Bounded Type Parameter: Stats ===\n");

        // Integer array
        Integer[] intArr = {10, 25, 7, 42, 18, 33};
        System.out.println("Integer array: " + java.util.Arrays.toString(intArr));
        System.out.printf("  Average: %.2f%n", average(intArr));
        System.out.println("  Max    : " + max(intArr));
        System.out.println("  Min    : " + min(intArr));

        // Double array
        Double[] dblArr = {3.14, 2.71, 1.41, 1.73, 2.23};
        System.out.println("\nDouble array: " + java.util.Arrays.toString(dblArr));
        System.out.printf("  Average: %.4f%n", average(dblArr));
        System.out.println("  Max    : " + max(dblArr));
        System.out.println("  Min    : " + min(dblArr));

        // Long array
        Long[] longArr = {100L, 200L, 150L, 300L, 250L};
        System.out.println("\nLong array: " + java.util.Arrays.toString(longArr));
        System.out.printf("  Average: %.2f%n", average(longArr));
        System.out.println("  Max    : " + max(longArr));
        System.out.println("  Min    : " + min(longArr));

        // Note: average(String[]) would cause a compile-time error
        // because String does not extend Number — type safety enforced at compile time
        System.out.println("\nNote: average(String[]) would NOT compile.");
        System.out.println("Bounded type <N extends Number> rejects non-Number types.");
    }
}
