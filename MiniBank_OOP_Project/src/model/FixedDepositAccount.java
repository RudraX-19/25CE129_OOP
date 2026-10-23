package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

/* ================================================================
   Practical 5 — Part B (FixedDepositAccount)
   interestRate() = 7.0; canWithdraw() = false (locked deposit).
   ================================================================ */

public class FixedDepositAccount extends Account {
    public FixedDepositAccount(String ownerName, long openingBalance) {
        super(ownerName, openingBalance);
    }

    public FixedDepositAccount(String ownerName) {
        this(ownerName, 0);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }

    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        throw new InsufficientFundsException(getAccountNumber(), amount, getBalance());
    }
}
