package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

/* ================================================================
   Practical 5 — Part B (CurrentAccount)
   interestRate() = 0; canWithdraw allows balance down to -overdraftLimit.
   ================================================================ */

public class CurrentAccount extends Account {
    private final long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public CurrentAccount(String ownerName, long overdraftLimit) {
        this(ownerName, 0, overdraftLimit);
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= -overdraftLimit;
    }

    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(getAccountNumber(), amount, getBalance());
        }
        setBalance(getBalance() - amount);
        return true;
    }
}
