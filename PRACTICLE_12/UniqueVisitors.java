// ============================================================
//  PRACTICLE 12 - Part A - Task 2: Unique Visitors
//  From a list of login names with repeats, use a HashSet to
//  count unique users and a LinkedHashSet to keep first-seen
//  order; print unique count and the first three distinct names
//  File: PRACTICLE_12/UniqueVisitors.java
// ============================================================

import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        System.out.println("=== Unique Visitor Tracker ===\n");

        // Login event stream — includes many repeats
        String[] logins = {
            "alice", "bob", "charlie", "alice", "diana",
            "bob", "eve", "alice", "frank", "charlie",
            "grace", "bob", "heidi", "alice", "ivan",
            "eve", "judy", "bob", "alice", "charlie"
        };

        System.out.println("Login events (" + logins.length + " total):");
        System.out.println(Arrays.toString(logins) + "\n");

        // HashSet — counts unique, no order guarantee
        HashSet<String> uniqueSet = new HashSet<>(Arrays.asList(logins));
        System.out.println("Unique visitor count (HashSet): " + uniqueSet.size());

        // LinkedHashSet — preserves first-seen insertion order
        LinkedHashSet<String> orderedSet = new LinkedHashSet<>();
        for (String name : logins) {
            orderedSet.add(name);   // duplicates are silently ignored
        }

        System.out.println("All unique visitors in first-seen order: " + orderedSet);

        // First three distinct names
        List<String> firstThree = new ArrayList<>(orderedSet).subList(0, 3);
        System.out.println("First 3 distinct visitors: " + firstThree);

        // Extra stats
        System.out.println("\n--- Visit counts ---");
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String name : logins) {
            counts.put(name, counts.getOrDefault(name, 0) + 1);
        }
        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            System.out.printf("  %-10s : %d visit(s)%n", e.getKey(), e.getValue());
        }
    }
}
