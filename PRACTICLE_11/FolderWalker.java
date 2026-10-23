// ============================================================
//  PRACTICLE 11 - Part A - Task 3: Folder Walker (Advanced)
//  Walk a folder tree with NIO; produce a report of files
//  (name, size, modified time); write summary to a file
//  File: PRACTICLE_11/FolderWalker.java
// ============================================================

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FolderWalker {

    record FileInfo(String name, long sizeBytes, String lastModified) {}

    public static void main(String[] args) throws IOException {
        System.out.println("=== Folder Walker using NIO ===\n");

        // Walk the current directory (submission folder)
        Path startDir   = Paths.get(".");
        Path reportFile = Paths.get("folder_report.txt");
        List<FileInfo> fileList = new ArrayList<>();

        System.out.println("Walking: " + startDir.toAbsolutePath());
        System.out.println("-".repeat(70));
        System.out.printf("%-35s %12s  %s%n", "Name", "Size (bytes)", "Last Modified");
        System.out.println("-".repeat(70));

        // Use DirectoryStream to walk only immediate directory (non-recursive)
        // For deeper walk use Files.walkFileTree
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(startDir)) {
            for (Path entry : ds) {
                if (Files.isRegularFile(entry)) {
                    BasicFileAttributes attrs =
                        Files.readAttributes(entry, BasicFileAttributes.class);
                    FileInfo fi = new FileInfo(
                        entry.getFileName().toString(),
                        attrs.size(),
                        attrs.lastModifiedTime().toString()
                    );
                    fileList.add(fi);
                    System.out.printf("%-35s %12d  %s%n",
                                      fi.name(), fi.sizeBytes(), fi.lastModified());
                }
            }
        }

        // Write summary report
        long totalSize = fileList.stream().mapToLong(FileInfo::sizeBytes).sum();

        try (BufferedWriter bw = Files.newBufferedWriter(reportFile)) {
            bw.write("=== Folder Report ===\n");
            bw.write("Directory: " + startDir.toAbsolutePath() + "\n");
            bw.write("Files found: " + fileList.size() + "\n");
            bw.write("Total size : " + totalSize + " bytes\n\n");
            bw.write(String.format("%-35s %12s  %s%n", "Name", "Size", "Modified"));
            bw.write("-".repeat(70) + "\n");
            for (FileInfo fi : fileList) {
                bw.write(String.format("%-35s %12d  %s%n",
                         fi.name(), fi.sizeBytes(), fi.lastModified()));
            }
        }

        System.out.println("-".repeat(70));
        System.out.println("Files found : " + fileList.size());
        System.out.println("Total size  : " + totalSize + " bytes");
        System.out.println("Report saved: " + reportFile.toAbsolutePath());

        // Cleanup
        Files.deleteIfExists(reportFile);
    }
}
