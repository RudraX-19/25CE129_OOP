package model;

/* ================================================================
   Practical 6 — Part B (WithdrawRule functional interface)
   ================================================================ */

@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
