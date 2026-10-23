package model;

/* ================================================================
   Practical 6 — Part B (Transactable interface)
   ================================================================ */

public interface Transactable {
    void deposit(long amount) throws exception.InvalidAmountException;

    boolean withdraw(long amount)
            throws exception.InsufficientFundsException, exception.InvalidAmountException;
}
