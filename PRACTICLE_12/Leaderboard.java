// ============================================================
//  PRACTICLE 12 - Part A - Task 3: Leaderboard (Advanced)
//  Player scores in a Map; print top-N sorted by score,
//  ties broken alphabetically by name
//  File: PRACTICLE_12/Leaderboard.java
// ============================================================

import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {
        System.out.println("=== Game Leaderboard ===\n");

        // Player scores in a HashMap
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice",   9500);
        scores.put("Bob",     8200);
        scores.put("Charlie", 9500);   // tie with Alice
        scores.put("Diana",   7800);
        scores.put("Eve",     9500);   // tie with Alice and Charlie
        scores.put("Frank",   8800);
        scores.put("Grace",   7200);
        scores.put("Heidi",   8800);   // tie with Frank

        System.out.println("All scores:");
        scores.forEach((name, score) ->
            System.out.printf("  %-10s : %d%n", name, score));

        // Sort: descending score, then alphabetical for ties
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(scores.entrySet());
        sorted.sort(
            Comparator.<Map.Entry<String, Integer>>comparingByValue().reversed()
                      .thenComparing(Map.Entry.comparingByKey())
        );

        int topN = 5;
        System.out.println("\nTop-" + topN + " Leaderboard:");
        System.out.println("-".repeat(35));
        System.out.printf("%-5s %-12s %s%n", "Rank", "Player", "Score");
        System.out.println("-".repeat(35));

        for (int i = 0; i < Math.min(topN, sorted.size()); i++) {
            System.out.printf("%-5d %-12s %d%n",
                i + 1,
                sorted.get(i).getKey(),
                sorted.get(i).getValue());
        }

        System.out.println("-".repeat(35));
        System.out.println("Top player: " + sorted.get(0).getKey()
                         + " with " + sorted.get(0).getValue() + " points");
    }
}
