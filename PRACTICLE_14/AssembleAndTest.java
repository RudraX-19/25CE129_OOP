// ============================================================
//  PRACTICLE 14 - Part A - Task 1: Assemble and Test
//  A tiny layered app: model, service using ArrayList, menu main
//  JUnit @Test methods check: adding increases count,
//  invalid action throws the right exception
//  File: PRACTICLE_14/AssembleAndTest.java
// ============================================================

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ---- Model ----

class Item {
    private final String name;
    private double price;

    public Item(String name, double price) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name must not be blank");
        if (price < 0)
            throw new IllegalArgumentException("Price must be >= 0, got: " + price);
        this.name  = name;
        this.price = price;
    }

    public String getName()  { return name;  }
    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price must be >= 0");
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Item{name='%s', price=%.2f}", name, price);
    }
}

// ---- Service ----

class ItemService {
    private final List<Item> items = new ArrayList<>();

    public void add(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must not be null");
        items.add(item);
    }

    public boolean remove(String name) {
        return items.removeIf(i -> i.getName().equalsIgnoreCase(name));
    }

    public Item find(String name) {
        return items.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
    }

    public List<Item> getAll() { return new ArrayList<>(items); }

    public int count() { return items.size(); }

    public double totalValue() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }
}

// ---- Inline JUnit-style tests (no external library needed) ----

class ItemServiceTest {
    private static int passed = 0;
    private static int failed = 0;

    // @Test — adding increases count
    static void testAddIncreasesCount() {
        ItemService svc = new ItemService();
        int before = svc.count();
        svc.add(new Item("Widget", 99.0));
        svc.add(new Item("Gadget", 149.0));
        int after = svc.count();

        if (after == before + 2) {
            System.out.println("  [PASS] testAddIncreasesCount: count " + before + " -> " + after);
            passed++;
        } else {
            System.out.println("  [FAIL] testAddIncreasesCount: expected " + (before + 2)
                             + " but got " + after);
            failed++;
        }
    }

    // @Test — adding duplicate does not prevent separate items
    static void testAddMultipleItems() {
        ItemService svc = new ItemService();
        svc.add(new Item("A", 10.0));
        svc.add(new Item("B", 20.0));
        svc.add(new Item("C", 30.0));

        if (svc.count() == 3 && svc.totalValue() == 60.0) {
            System.out.println("  [PASS] testAddMultipleItems: count=3, total=60.0");
            passed++;
        } else {
            System.out.println("  [FAIL] testAddMultipleItems: count=" + svc.count()
                             + ", total=" + svc.totalValue());
            failed++;
        }
    }

    // @Test — invalid action (negative price) throws IllegalArgumentException
    static void testInvalidPriceThrows() {
        try {
            new Item("Bad", -5.0);
            System.out.println("  [FAIL] testInvalidPriceThrows: expected exception not thrown");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [PASS] testInvalidPriceThrows: caught -> " + e.getMessage());
            passed++;
        }
    }

    // @Test — blank name throws
    static void testBlankNameThrows() {
        try {
            new Item("   ", 10.0);
            System.out.println("  [FAIL] testBlankNameThrows: expected exception not thrown");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [PASS] testBlankNameThrows: caught -> " + e.getMessage());
            passed++;
        }
    }

    // @Test — remove reduces count
    static void testRemoveReducesCount() {
        ItemService svc = new ItemService();
        svc.add(new Item("X", 5.0));
        svc.add(new Item("Y", 10.0));
        boolean removed = svc.remove("X");

        if (removed && svc.count() == 1) {
            System.out.println("  [PASS] testRemoveReducesCount: removed=true, count=1");
            passed++;
        } else {
            System.out.println("  [FAIL] testRemoveReducesCount: removed=" + removed
                             + ", count=" + svc.count());
            failed++;
        }
    }

    static void runAll() {
        System.out.println("=== Running Tests ===\n");
        testAddIncreasesCount();
        testAddMultipleItems();
        testInvalidPriceThrows();
        testBlankNameThrows();
        testRemoveReducesCount();
        System.out.println("\n--- Results: " + passed + " passed, " + failed + " failed ---");
    }
}

// ---- Menu main ----

public class AssembleAndTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ItemService service = new ItemService();

        // Pre-load some items
        service.add(new Item("Laptop",    55000.0));
        service.add(new Item("Mouse",       450.0));
        service.add(new Item("Keyboard",    850.0));

        while (true) {
            System.out.println("\n=== Item Manager ===");
            System.out.println("1. List items");
            System.out.println("2. Add item");
            System.out.println("3. Remove item");
            System.out.println("4. Find item");
            System.out.println("5. Run tests");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("\nAll items (" + service.count() + "):");
                    service.getAll().forEach(i -> System.out.println("  " + i));
                    System.out.printf("Total value: Rs %.2f%n", service.totalValue());
                }
                case "2" -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Price: ");
                    try {
                        double price = Double.parseDouble(sc.nextLine().trim());
                        service.add(new Item(name, price));
                        System.out.println("Added. Count: " + service.count());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.print("Name to remove: ");
                    String name = sc.nextLine().trim();
                    boolean ok = service.remove(name);
                    System.out.println(ok ? "Removed." : "Not found.");
                }
                case "4" -> {
                    System.out.print("Name to find: ");
                    String name = sc.nextLine().trim();
                    Item found = service.find(name);
                    System.out.println(found != null ? found : "Not found.");
                }
                case "5" -> ItemServiceTest.runAll();
                case "6" -> {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try 1-6.");
            }
        }
    }
}
