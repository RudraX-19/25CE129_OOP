package service;

import model.Account;
import model.TransactionType;
import util.AnnotationValidator;
import util.Validator;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import exception.AccountNotFoundException;

import java.util.ArrayList;

/* ================================================================
   Practical 14 — Part B (BankService — capstone service layer)
   Wires together Repository<Account>, Validator, and exceptions.
   Provides: openAccount, deposit, withdraw, transfer, statement, listAccounts.
   ================================================================ */

public class BankService {
    private final util.Repository<Account> repository = new util.Repository<>();
    private final util.AccountStore accountStore = new util.AccountStore();

    public void openAccount(Account account) {
        // Validate with reflection-based annotation validator
        String[] errors = AnnotationValidator.validate(account);
        if (errors.length > 0) {
            StringBuilder sb = new StringBuilder("Validation failed:");
            for (String e : errors) {
                sb.append(" ").append(e);
            }
            throw new IllegalArgumentException(sb.toString());
        }
        repository.save(account);
        accountStore.add(account);
        accountStore.recordTransaction("OPEN " + account.getAccountNumber());
    }

    public void deposit(String accountNumber, long amount)
            throws BankException {
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        try {
            account.deposit(amount);
            accountStore.recordTransaction("DEPOSIT " + accountNumber + " " + amount);
        } catch (InvalidAmountException e) {
            throw new InvalidAmountException(amount);
        }
    }

    public void withdraw(String accountNumber, long amount)
            throws BankException {
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        try {
            boolean ok = account.withdraw(amount);
            if (ok) {
                accountStore.recordTransaction("WITHDRAW " + accountNumber + " " + amount);
            }
        } catch (InsufficientFundsException e) {
            throw e;
        } catch (InvalidAmountException e) {
            throw new InvalidAmountException(amount);
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, long amount)
            throws BankException {
        Account from = repository.findById(fromAccountNumber);
        Account to = repository.findById(toAccountNumber);
        if (from == null) {
            throw new AccountNotFoundException(fromAccountNumber);
        }
        if (to == null) {
            throw new AccountNotFoundException(toAccountNumber);
        }
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        try {
            if (from.canWithdraw(amount)) {
                from.setBalance(from.getBalance() - amount);
                to.setBalance(to.getBalance() + amount);
                accountStore.recordTransaction("TRANSFER " + fromAccountNumber
                        + " -> " + toAccountNumber + " " + amount);
            } else {
                throw new InsufficientFundsException(fromAccountNumber, amount, from.getBalance());
            }
        } catch (InsufficientFundsException e) {
            throw e;
        }
    }

    public String statement(String accountNumber)
            throws AccountNotFoundException {
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return util.StatementFormatter.buildStatement(account);
    }

    public ArrayList<Account> listAccounts() {
        return repository.findAll();
    }

    public ArrayList<Account> listAccountsSortedByBalance() {
        return accountStore.sortedByBalance();
    }

    public boolean accountExists(String accountNumber) {
        return repository.findById(accountNumber) != null;
    }

    /* Used when loading saved accounts on start-up — bypasses validation */
    public void loadAccount(Account account) {
        repository.save(account);
        accountStore.add(account);
    }
}
