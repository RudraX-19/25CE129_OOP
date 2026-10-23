package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/* ================================================================
   Practical 11 — Part B (TransactionLog)
   Append-only transaction log using Files.write with CREATE/APPEND.
   ================================================================ */

public class TransactionLog {
    public static void append(Path file, String line) throws IOException {
        Files.write(file,
                java.util.List.of(line + System.lineSeparator()),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
}
