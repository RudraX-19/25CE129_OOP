package model;

/* ================================================================
   Practical 4 — Part B (Command record)
   ================================================================ */

public record Command(TransactionType type, String accountNumber, long amount) {
}
