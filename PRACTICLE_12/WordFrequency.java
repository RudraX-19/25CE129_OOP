// ============================================================
//  PRACTICLE 12 - Part A - Task 1: Word Frequency
//  Split a paragraph into words, count each in a HashMap,
//  print sorted by frequency (highest first) using a Comparator
//  File: PRACTICLE_12/WordFrequency.java
// ============================================================

import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        System.out.println("=== Word Frequency Counter ===\n");

        String paragraph =
            "the quick brown fox jumps over the lazy dog " +
            "the dog barked at the fox and the fox ran away " +
            "the quick fox was very quick and the dog was very lazy";

        System.out.println("Paragraph:\n\"" + paragraph + "\"\n");

        // Split into words and count in a HashMap
        String[] words = paragraph.toLowerCase().split("\\s+");
        HashMap<String, Integer> freq = new HashMap<>();

        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }

        // Sort entries by frequency descending, then alphabetically for ties
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort(
            Comparator.<Map.Entry<String, Integer>>comparingByValue().reversed()
                      .thenComparing(Map.Entry.comparingByKey())
        );

        System.out.printf("%-15s %s%n", "Word", "Count");
        System.out.println("-".repeat(25));
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.printf("%-15s %d%n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nTotal unique words: " + freq.size());
        System.out.println("Most frequent    : " + entries.get(0).getKey()
                         + " (" + entries.get(0).getValue() + " times)");
    }
}
