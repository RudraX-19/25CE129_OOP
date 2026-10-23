// ============================================================
//  PRACTICLE 13 - Part A - Task 1: Generic Containers
//  Generic Box<T> with get/set; Pair<A,B>; generic method
//  that returns the first element of any array — type safety
//  File: PRACTICLE_13/GenericContainers.java
// ============================================================

// ---- Generic Box ----
class Box<T> {
    private T value;

    public Box(T value) { this.value = value; }

    public T    get()          { return value; }
    public void set(T value)   { this.value = value; }

    @Override
    public String toString() {
        return "Box[" + value + "]";
    }
}

// ---- Generic Pair ----
class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first  = first;
        this.second = second;
    }

    public A getFirst()  { return first;  }
    public B getSecond() { return second; }

    @Override
    public String toString() {
        return "Pair(" + first + ", " + second + ")";
    }
}

// ---- Driver ----
public class GenericContainers {

    // Generic method — returns first element of any array
    public static <T> T firstOf(T[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("Array is empty");
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== Generic Containers Demo ===\n");

        // Box with different types — no casts needed
        Box<String>  strBox  = new Box<>("Hello Generics");
        Box<Integer> intBox  = new Box<>(42);
        Box<Double>  dblBox  = new Box<>(3.14);

        System.out.println("String box : " + strBox);
        System.out.println("Integer box: " + intBox);
        System.out.println("Double box : " + dblBox);

        strBox.set("Updated value");
        System.out.println("After set  : " + strBox);

        // Pair with mixed types
        System.out.println("\n--- Pair<A,B> ---");
        Pair<String, Integer> nameAge   = new Pair<>("Alice", 22);
        Pair<Integer, Boolean> idActive = new Pair<>(101, true);
        Pair<String, Double>  itemPrice = new Pair<>("Laptop", 55000.0);

        System.out.println(nameAge);
        System.out.println(idActive);
        System.out.println(itemPrice);

        // Generic method firstOf
        System.out.println("\n--- Generic method firstOf() ---");
        String[]  names    = {"Alice", "Bob", "Charlie"};
        Integer[] numbers  = {10, 20, 30};
        Double[]  decimals = {1.1, 2.2, 3.3};

        System.out.println("First of names   : " + firstOf(names));
        System.out.println("First of numbers : " + firstOf(numbers));
        System.out.println("First of decimals: " + firstOf(decimals));
    }
}
