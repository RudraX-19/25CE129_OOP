// ============================================================
//  PRACTICLE 14 - Part A - Task 2 (Advanced/Home):
//  Organise the layered app into packages concept, build a
//  runnable JAR, add a test that saves and reloads data unchanged
//  File: PRACTICLE_14/LayeredAppJar.java
// ============================================================

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ==============================================================
//  model package concept — data classes
// ==============================================================

class Product14 implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final double price;

    public Product14(String id, String name, double price) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID must not be blank");
        if (price < 0)
            throw new IllegalArgumentException("Price must be >= 0");
        this.id    = id;
        this.name  = name;
        this.price = price;
    }

    public String getId()    { return id;    }
    public String getName()  { return name;  }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("Product14{id='%s', name='%s', price=%.2f}", id, name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product14 p)) return false;
        return id.equals(p.id) && name.equals(p.name)
            && Double.compare(price, p.price) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name, price);
    }
}

// ==============================================================
//  service package concept — business logic
// ==============================================================

class ProductService14 {
    private final List<Product14> store = new ArrayList<>();

    public void add(Product14 p) {
        if (p == null) throw new IllegalArgumentException("Product must not be null");
        store.add(p);
    }

    public boolean remove(String id) {
        return store.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    public Product14 find(String id) {
        return store.stream()
                    .filter(p -> p.getId().equalsIgnoreCase(id))
                    .findFirst().orElse(null);
    }

    public List<Product14> getAll() { return new ArrayList<>(store); }
    public int             count()  { return store.size(); }

    /** Persist to file */
    public void saveTo(String filename) throws IOException {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(new ArrayList<>(store));
        }
    }

    /** Load from file — returns number of records loaded */
    @SuppressWarnings("unchecked")
    public int loadFrom(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(filename))) {
            List<Product14> loaded = (List<Product14>) ois.readObject();
            store.clear();
            store.addAll(loaded);
            return loaded.size();
        }
    }
}

// ==============================================================
//  test package concept — inline tests (JUnit @Test pattern)
// ==============================================================

class ProductServiceTest14 {
    private static int passed = 0;
    private static int failed = 0;

    // @Test — save and reload: data unchanged
    static void testSaveAndReloadUnchanged() throws Exception {
        ProductService14 svc = new ProductService14();
        svc.add(new Product14("P1", "Alpha", 100.0));
        svc.add(new Product14("P2", "Beta",  200.0));
        svc.add(new Product14("P3", "Gamma", 300.0));

        String file = "layered_test.dat";
        svc.saveTo(file);

        ProductService14 svc2 = new ProductService14();
        svc2.loadFrom(file);

        boolean pass = svc.getAll().equals(svc2.getAll());
        if (pass) {
            System.out.println("  [PASS] testSaveAndReloadUnchanged: all records match");
            passed++;
        } else {
            System.out.println("  [FAIL] testSaveAndReloadUnchanged");
            failed++;
        }
        new File(file).delete();
    }

    // @Test — adding increases count
    static void testAddIncreasesCount() {
        ProductService14 svc = new ProductService14();
        svc.add(new Product14("P1", "Widget", 50.0));
        svc.add(new Product14("P2", "Gadget", 75.0));
        if (svc.count() == 2) {
            System.out.println("  [PASS] testAddIncreasesCount: count = 2");
            passed++;
        } else {
            System.out.println("  [FAIL] testAddIncreasesCount: count = " + svc.count());
            failed++;
        }
    }

    // @Test — invalid price throws IllegalArgumentException
    static void testInvalidPriceThrows() {
        try {
            new Product14("P1", "Bad", -10.0);
            System.out.println("  [FAIL] testInvalidPriceThrows: no exception thrown");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [PASS] testInvalidPriceThrows: caught -> " + e.getMessage());
            passed++;
        }
    }

    static void runAll() throws Exception {
        System.out.println("=== Running Layered App Tests ===\n");
        testSaveAndReloadUnchanged();
        testAddIncreasesCount();
        testInvalidPriceThrows();
        System.out.println("\n--- Results: " + passed + " passed, " + failed + " failed ---");
    }
}

// ==============================================================
//  ui package concept — menu / main (entry point for JAR)
// ==============================================================

public class LayeredAppJar {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Layered App (model | service | ui) ===\n");

        ProductService14 service = new ProductService14();
        service.add(new Product14("P001", "Laptop",   55000.0));
        service.add(new Product14("P002", "Mouse",      450.0));
        service.add(new Product14("P003", "Keyboard",   850.0));

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. List all products");
            System.out.println("2. Add product");
            System.out.println("3. Find product");
            System.out.println("4. Remove product");
            System.out.println("5. Save to file");
            System.out.println("6. Load from file");
            System.out.println("7. Run tests");
            System.out.println("8. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    service.getAll().forEach(p -> System.out.println("  " + p));
                    System.out.println("Total: " + service.count());
                }
                case "2" -> {
                    try {
                        System.out.print("ID: ");    String id    = sc.nextLine().trim();
                        System.out.print("Name: ");  String name  = sc.nextLine().trim();
                        System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine().trim());
                        service.add(new Product14(id, name, price));
                        System.out.println("Added. Count: " + service.count());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.print("ID: ");
                    Product14 p = service.find(sc.nextLine().trim());
                    System.out.println(p != null ? p : "Not found.");
                }
                case "4" -> {
                    System.out.print("ID: ");
                    System.out.println(service.remove(sc.nextLine().trim()) ? "Removed." : "Not found.");
                }
                case "5" -> {
                    service.saveTo("products14.dat");
                    System.out.println("Saved to products14.dat");
                }
                case "6" -> {
                    try {
                        int n = service.loadFrom("products14.dat");
                        System.out.println("Loaded " + n + " products.");
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found. Save first.");
                    }
                }
                case "7" -> ProductServiceTest14.runAll();
                case "8" -> {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid. Enter 1-8.");
            }
        }
    }
}
