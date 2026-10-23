package model;

import annotation.Id;
import annotation.Positive;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

import java.io.Serializable;

/* ================================================================
   Practical 2 — Part B (Account class)
   Practical 5 — Part B (Account made abstract with interest/canWithdraw)
   Practical 6 — Part B (Account implements Transactable, InterestBearing)
   Practical 7 — Part B (@Id on accountNumber, @Positive on balance)
   Practical 8 — Part B (withdraw/deposit throw exceptions)
   Practical 11 — Part B (Account implements Serializable)
   Practical 12 — Part B (Account implements Comparable by accountNumber)
   ================================================================ */

public abstract class Account implements Serializable, Transactable, InterestBearing, Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private String ownerName;
    @Positive(message = "balance must be > 0")
    private long balance;
    private boolean active;

    private static long accountCounter = 1000;

    private static String generateAccountNumber() {
        return "AC" + (++accountCounter);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    @Override
    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        balance += amount;
    }

    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(accountNumber, amount, balance);
        }
        balance -= amount;
        return true;
    }

    @Override
    public double yearlyInterest() {
        return (double) balance * interestRate() / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Account a))
            return false;
        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | balance=%,d | active=%s",
                accountNumber, ownerName, balance, active);
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }
}
