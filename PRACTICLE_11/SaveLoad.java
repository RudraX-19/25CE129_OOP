// ============================================================
//  PRACTICLE 11 - Part A - Task 1: Save / Load (Serialization)
//  Make a small class Serializable, save an array to a file with
//  ObjectOutputStream, read it back; mark one field transient
//  File: PRACTICLE_11/SaveLoad.java
// ============================================================

import java.io.*;

class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int    quantity;
    private double price;
    private transient String sessionToken; // NOT saved

    public Product(String name, int quantity, double price, String sessionToken) {
        this.name         = name;
        this.quantity     = quantity;
        this.price        = price;
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return String.format("Product{name='%s', qty=%d, price=%.2f, token='%s'}",
                             name, quantity, price, sessionToken);
    }
}

public class SaveLoad {
    public static void main(String[] args) {
        System.out.println("=== Serialization: Save / Load Demo ===\n");

        Product[] products = {
            new Product("Laptop",    10, 55000.0, "tok-001"),
            new Product("Mouse",    100,   450.0, "tok-002"),
            new Product("Keyboard",  50,   850.0, "tok-003")
        };

        System.out.println("--- Before save ---");
        for (Product p : products) System.out.println("  " + p);

        String filename = "products.dat";

        // Save
        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(products);
            System.out.println("\nSaved " + products.length + " products to " + filename);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
            return;
        }

        // Load
        Product[] loaded = null;
        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(filename))) {
            loaded = (Product[]) ois.readObject();
            System.out.println("Loaded " + loaded.length + " products from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load error: " + e.getMessage());
            return;
        }

        System.out.println("\n--- After load ---");
        for (Product p : loaded) System.out.println("  " + p);

        System.out.println("\nNotice: sessionToken is null after load (transient field not saved)");

        // Cleanup
        new File(filename).delete();
    }
}
