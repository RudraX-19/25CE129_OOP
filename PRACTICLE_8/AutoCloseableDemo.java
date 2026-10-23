// ============================================================
//  PRACTICLE 8 - Part A - Task 3: AutoCloseable Resource (Advanced)
//  try-with-resources guarantees close() even when an exception
//  is thrown; the original error is the one reported
//  File: PRACTICLE_8/AutoCloseableDemo.java
// ============================================================

// ---- A simple AutoCloseable resource ----

class DatabaseConnection implements AutoCloseable {
    private final String url;
    private boolean open;

    public DatabaseConnection(String url) {
        this.url  = url;
        this.open = true;
        System.out.println("[DB] Connection opened: " + url);
    }

    public void query(String sql) {
        if (!open) throw new IllegalStateException("Connection is already closed");
        System.out.println("[DB] Executing query: " + sql);

        // Simulate an error on a specific query
        if (sql.contains("BAD_TABLE")) {
            throw new RuntimeException("Table 'BAD_TABLE' does not exist");
        }
    }

    @Override
    public void close() {
        open = false;
        System.out.println("[DB] Connection closed: " + url);
    }
}

// ---- Driver ----

public class AutoCloseableDemo {

    public static void main(String[] args) {
        System.out.println("=== AutoCloseable + try-with-resources Demo ===\n");

        // --- Case 1: successful query — connection is still closed ---
        System.out.println("--- Case 1: successful query ---");
        try (DatabaseConnection conn = new DatabaseConnection("jdbc:mydb://localhost/accounts")) {
            conn.query("SELECT * FROM accounts");
            conn.query("SELECT balance FROM accounts WHERE id = 'AC0001'");
        }
        System.out.println();

        // --- Case 2: exception thrown inside the block ---
        // close() MUST still be called; the original exception is what propagates
        System.out.println("--- Case 2: exception inside try block ---");
        try (DatabaseConnection conn = new DatabaseConnection("jdbc:mydb://localhost/accounts")) {
            conn.query("SELECT * FROM accounts");
            conn.query("SELECT * FROM BAD_TABLE");   // throws RuntimeException
            conn.query("SELECT * FROM transactions"); // never reached
        } catch (RuntimeException e) {
            System.out.println("[CAUGHT] " + e.getMessage());
            System.out.println("Connection was still closed before this catch ran.");
        }
        System.out.println();

        // --- Case 3: without try-with-resources (manual close — error-prone) ---
        System.out.println("--- Case 3: manual close (for comparison) ---");
        DatabaseConnection conn = null;
        try {
            conn = new DatabaseConnection("jdbc:mydb://localhost/accounts");
            conn.query("SELECT 1");
        } finally {
            if (conn != null) conn.close();
        }

        System.out.println("\n=== Demo complete ===");
    }
}
