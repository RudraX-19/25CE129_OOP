package model;

/* ================================================================
   Practical 6 — Part B (InterestBearing interface)
   default yearlyInterest() using interestRate() and balance.
   ================================================================ */

public interface InterestBearing {
    double interestRate();

    long getBalance();

    default double yearlyInterest() {
        return (double) getBalance() * interestRate() / 100.0;
    }
}
