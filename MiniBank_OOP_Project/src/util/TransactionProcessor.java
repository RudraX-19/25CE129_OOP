package util;

import model.Account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* ================================================================
   Practical 10 — Part B (TransactionProcessor)
   Practical 12 — Part B (uses ConcurrentHashMap for thread-safe accounts)
   ================================================================ */

public class TransactionProcessor {
    private final ExecutorService pool = Executors.newFixedThreadPool(4);
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public ExecutorService getPool() {
        return pool;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String id, Account account) {
        accounts.put(id, account);
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }
}
