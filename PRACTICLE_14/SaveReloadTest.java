// ============================================================
//  PRACTICLE 14 - Part A - Task 2: Save and Reload Test (Advanced)
//  Organise a layered app into packages concept; build a test
//  that saves and reloads data and checks it is unchanged
//  File: PRACTICLE_14/SaveReloadTest.java
// ============================================================

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// ---- Model (Serializable) ----

class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String       name;
    private int          value;

    public Record(String id, String name, int value) {
        this.id    = id;
        this.name  = name;
        this.value = value;
    }

    public String getId()    { return id;    }
    public String getName()  { return name;  }
    public int    getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record r)) return false;
        return value == r.value
            && Objects.equals(id, r.id)
            && Objects.equals(name, r.name);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, value); }

    @Override
    public String toString() {
        return "Record{id='" + id + "', name='" + name + "', value=" + value + "}";
    }
}

// ---- Persistence layer ----

class DataStore {
    @SuppressWarnings("unchecked")
    public static List<Record> load(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Record>) ois.readObject();
        }
    }

    public static void save(List<Record> records, String filename) throws IOException {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(records);
        }
    }
}

// ---- Inline tests ----

class DataStoreTest {
    private static int passed = 0;
    private static int failed = 0;

    // @Test — save then load returns identical data
    static void testSaveAndReloadUnchanged() throws Exception {
        List<Record> original = new ArrayList<>();
        original.add(new Record("R1", "Alpha", 100));
        original.add(new Record("R2", "Beta",  200));
        original.add(new Record("R3", "Gamma", 300));

        String file = "test_records.dat";
        DataStore.save(original, file);
        List<Record> loaded = DataStore.load(file);

        boolean sizeOk = original.size() == loaded.size();
        boolean contentOk = true;
        for (int i = 0; i < original.size(); i++) {
            if (!original.get(i).equals(loaded.get(i))) {
                contentOk = false;
                break;
            }
        }

        if (sizeOk && contentOk) {
            System.out.println("  [PASS] testSaveAndReloadUnchanged: "
                             + loaded.size() + " records match exactly");
            passed++;
        } else {
            System.out.println("  [FAIL] testSaveAndReloadUnchanged");
            failed++;
        }

        // Cleanup
        new File(file).delete();
    }

    // @Test — loading non-existent file throws IOException
    static void testLoadMissingFileThrows() {
        try {
            DataStore.load("no_such_file.dat");
            System.out.println("  [FAIL] testLoadMissingFileThrows: exception not thrown");
            failed++;
        } catch (IOException e) {
            System.out.println("  [PASS] testLoadMissingFileThrows: IOException caught");
            passed++;
        } catch (Exception e) {
            System.out.println("  [FAIL] testLoadMissingFileThrows: wrong exception: " + e);
            failed++;
        }
    }

    // @Test — save empty list and reload returns empty list
    static void testSaveEmptyList() throws Exception {
        String file = "empty_records.dat";
        DataStore.save(new ArrayList<>(), file);
        List<Record> loaded = DataStore.load(file);

        if (loaded.isEmpty()) {
            System.out.println("  [PASS] testSaveEmptyList: empty list reloads as empty");
            passed++;
        } else {
            System.out.println("  [FAIL] testSaveEmptyList: got " + loaded.size() + " records");
            failed++;
        }
        new File(file).delete();
    }

    static void runAll() throws Exception {
        System.out.println("=== Running Save/Reload Tests ===\n");
        testSaveAndReloadUnchanged();
        testLoadMissingFileThrows();
        testSaveEmptyList();
        System.out.println("\n--- Results: " + passed + " passed, " + failed + " failed ---");
    }
}

// ---- Main ----

public class SaveReloadTest {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Save and Reload Test ===\n");

        // Demonstrate manual save/reload
        List<Record> data = new ArrayList<>();
        data.add(new Record("R1", "Alice", 500));
        data.add(new Record("R2", "Bob",   300));
        data.add(new Record("R3", "Carol", 700));

        System.out.println("Original data:");
        data.forEach(r -> System.out.println("  " + r));

        String file = "records.dat";
        DataStore.save(data, file);
        System.out.println("\nSaved to: " + file);

        List<Record> reloaded = DataStore.load(file);
        System.out.println("\nReloaded data:");
        reloaded.forEach(r -> System.out.println("  " + r));

        boolean allMatch = data.equals(reloaded);
        System.out.println("\nAll records match after reload: " + allMatch);

        new File(file).delete();

        // Run the automated tests
        System.out.println();
        DataStoreTest.runAll();
    }
}
