// ============================================================
//  PRACTICLE 4 - Part A - Task 2: Chat-log Filter
//  File: PRACTICLE_4/ChatFilter.java
// ============================================================

import java.util.Scanner;

public class ChatFilter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Chat Log Filter ===\n");
        
        String[] logs = {
            "10:05 alice Hello there",
            "10:07 bob Hi everyone",
            "10:10 charlie hello world",
            "10:15 badline",  // malformed - only 1 part
            "10:20 alice See you later",
            "10:25 bob Hello alice",
            "10:30 charlie Good morning"
        };
        
        System.out.println("Chat logs:");
        for (String log : logs) {
            System.out.println("  " + log);
        }
        
        System.out.print("\nEnter keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();
        
        int matchCount = 0;
        StringBuilder report = new StringBuilder();
        
        System.out.println("\nFiltering logs...\n");
        
        for (String line : logs) {
            String[] parts = line.split(" ", 3);
            
            // Skip malformed lines (less than 3 parts)
            if (parts.length < 3) {
                System.out.println("Skipping malformed line: " + line);
                continue;
            }
            
            String time = parts[0];
            String user = parts[1];
            String message = parts[2];
            
            // Check if message contains keyword (case-insensitive)
            if (message.toLowerCase().contains(keyword)) {
                matchCount++;
                report.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            }
        }
        
        System.out.println("\n=== Search Results ===");
        System.out.println("Matches: " + matchCount);
        if (matchCount > 0) {
            System.out.println("\nMatching messages:");
            System.out.print(report.toString());
        }
        
        sc.close();
    }
}
