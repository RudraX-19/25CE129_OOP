package util;

import model.Command;
import model.TransactionType;

/* ================================================================
   Practical 4 — Part B (CommandParser)
   Parses a line such as "DEPOSIT AC0001 500" into a Command.
   ================================================================ */

public class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Command line must not be blank");
        }
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected format: <TYPE> <ACCOUNT> <AMOUNT>");
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transaction type: " + parts[0]);
        }

        String accountNumber = parts[1];
        long amount;
        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount: " + parts[2]);
        }

        return new Command(type, accountNumber, amount);
    }
}
