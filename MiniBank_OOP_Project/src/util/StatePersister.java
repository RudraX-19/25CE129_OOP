package util;

import model.Account;

import java.io.*;
import java.nio.file.Path;

/* ================================================================
   Practical 11 — Part B (StatePersister)
   Save/load Account[] using Object streams inside try-with-resources.
   ================================================================ */

public class StatePersister {
    public static void save(Account[] accounts, Path file) throws IOException {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(accounts);
        }
    }

    @SuppressWarnings("unchecked")
    public static Account[] load(Path file)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(file.toFile()))) {
            return (Account[]) ois.readObject();
        }
    }
}
