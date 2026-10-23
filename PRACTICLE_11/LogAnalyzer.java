// ============================================================
//  PRACTICLE 11 - Part A - Task 2: Log Analyzer (NIO)
//  Create text files; use Path/Files to read each line by line;
//  count total lines and keyword matches; print file attributes
//  File: PRACTICLE_11/LogAnalyzer.java
// ============================================================

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class LogAnalyzer {

    // Write a sample log file
    static Path createLog(String filename, String[] lines) throws IOException {
        Path path = Paths.get(filename);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
        return path;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Log Analyzer using NIO Path/Files ===\n");

        // Create sample log files
        Path log1 = createLog("app1.log", new String[]{
            "INFO  2026-09-30 Server started",
            "ERROR 2026-09-30 Connection timeout",
            "INFO  2026-09-30 User login: alice",
            "ERROR 2026-09-30 Null pointer in PayrollService",
            "INFO  2026-09-30 Server stopped"
        });

        Path log2 = createLog("app2.log", new String[]{
            "INFO  2026-09-30 Batch job started",
            "WARN  2026-09-30 Disk usage above 80%",
            "ERROR 2026-09-30 Disk write failed",
            "INFO  2026-09-30 Batch job completed"
        });

        Path[] logFiles = { log1, log2 };

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword to search across all logs: ");
        String keyword = sc.nextLine().trim().toLowerCase();

        int totalLines    = 0;
        int totalMatches  = 0;

        for (Path logFile : logFiles) {
            System.out.println("\n--- " + logFile.getFileName() + " ---");

            // File attributes
            BasicFileAttributes attrs = Files.readAttributes(logFile, BasicFileAttributes.class);
            System.out.println("  Size            : " + attrs.size() + " bytes");
            System.out.println("  Last modified   : " + attrs.lastModifiedTime());

            // Read line by line
            int fileLines   = 0;
            int fileMatches = 0;

            try (BufferedReader br = Files.newBufferedReader(logFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileLines++;
                    if (line.toLowerCase().contains(keyword)) {
                        fileMatches++;
                        System.out.println("  [MATCH] " + line);
                    }
                }
            }

            System.out.println("  Lines: " + fileLines + "  |  Matches: " + fileMatches);
            totalLines   += fileLines;
            totalMatches += fileMatches;
        }

        System.out.println("\n=== Summary ===");
        System.out.println("Total lines across all files : " + totalLines);
        System.out.println("Total keyword matches        : " + totalMatches);

        // Cleanup
        for (Path p : logFiles) Files.deleteIfExists(p);
        sc.close();
    }
}
