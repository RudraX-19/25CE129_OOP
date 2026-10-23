package util;

import model.Account;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/* ================================================================
   Practical 12 — Part B (AccountStore)
   HashMap-based store keyed by account number, with transaction
   history and sorting by balance.
   ================================================================ */

public class AccountStore {
    private final HashMap<String, Account> accounts = new HashMap<>();
    private final ArrayList<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }
        accounts.put(account.getAccountNumber(), account);
        transactionHistory.add("ADDED " + account.getAccountNumber()
                + " (" + account.getOwnerName() + ")");
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public void recordTransaction(String description) {
        transactionHistory.add(description);
    }

    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());
        list.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return list;
    }

    public int size() {
        return accounts.size();
    }
}
