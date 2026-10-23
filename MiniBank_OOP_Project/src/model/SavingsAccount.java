package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

/* ================================================================
   Practical 5 — Part B (SavingsAccount)
   interestRate() = 4.0; canWithdraw only if balance >= minBalance.
   ================================================================ */

public class SavingsAccount extends Account {
    private final long minBalance;

    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);
        this.minBalance = minBalance;
    }

    public SavingsAccount(String ownerName, long minBalance) {
        this(ownerName, 0, minBalance);
    }

    public long getMinBalance() {
        return minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= minBalance;
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
