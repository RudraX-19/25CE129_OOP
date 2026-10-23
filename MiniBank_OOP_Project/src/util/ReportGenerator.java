package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* ================================================================
   Practical 11 — Part B (ReportGenerator)
   Walks the logs folder, sums deposits and withdrawals, reads file
   attributes, and writes a summary report.
   ================================================================ */

public class ReportGenerator {
    public static void generate(Path logsDir, Path reportFile) throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int fileCount = 0;

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path logFile : ds) {
                fileCount++;
                BasicFileAttributes attrs =
                        Files.readAttributes(logFile, BasicFileAttributes.class);

                try (BufferedReader br = Files.newBufferedReader(logFile)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String trimmed = line.trim();
                        if (trimmed.startsWith("DEPOSIT")) {
                            totalDeposits += parseAmount(trimmed);
                        } else if (trimmed.startsWith("WITHDRAW")) {
                            totalWithdrawals += parseAmount(trimmed);
                        }
                    }
                }
            }
        }

        long net = totalDeposits - totalWithdrawals;

        try (BufferedWriter bw = Files.newBufferedWriter(reportFile)) {
            bw.write("=== End-of-Day Report ===\n");
            bw.write("Log directory : " + logsDir.toAbsolutePath() + "\n");
            bw.write("Log files processed : " + fileCount + "\n");
            bw.write("Total deposits  : " + totalDeposits + "\n");
            bw.write("Total withdrawals: " + totalWithdrawals + "\n");
            bw.write("Net change      : " + net + "\n");
        }
    }

    private static long parseAmount(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length >= 3) {
            try {
                return Long.parseLong(parts[2]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}
