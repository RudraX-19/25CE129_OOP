// ============================================================
//  PRACTICLE 10 - Part A - Task 3: Deadlock (Advanced)
//  Two threads lock two objects in opposite order — deadlock.
//  Fix: always lock the object with the smaller id first.
//  File: PRACTICLE_10/DeadlockDemo.java
// ============================================================

class BankAcc {
    final int    id;
    long         balance;

    BankAcc(int id, long balance) {
        this.id      = id;
        this.balance = balance;
    }

    @Override public String toString() {
        return "Account#" + id + "(bal=" + balance + ")";
    }
}

public class DeadlockDemo {

    // --- UNSAFE transfer: locks in argument order (can deadlock) ---
    static void unsafeTransfer(BankAcc from, BankAcc to, long amount)
            throws InterruptedException {
        synchronized (from) {
            Thread.sleep(50);          // sleep while holding from's lock
            synchronized (to) {
                from.balance -= amount;
                to.balance   += amount;
                System.out.println(Thread.currentThread().getName()
                        + ": transferred " + amount
                        + " from " + from + " to " + to);
            }
        }
    }

    // --- SAFE transfer: always lock the lower-id account first ---
    static void safeTransfer(BankAcc from, BankAcc to, long amount) {
        BankAcc first  = (from.id < to.id) ? from : to;
        BankAcc second = (from.id < to.id) ? to   : from;

        synchronized (first) {
            synchronized (second) {
                from.balance -= amount;
                to.balance   += amount;
                System.out.println(Thread.currentThread().getName()
                        + ": transferred " + amount
                        + " from " + from + " to " + to);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        BankAcc a = new BankAcc(1, 5000);
        BankAcc b = new BankAcc(2, 5000);

        // ============================================================
        // PART 1 — demonstrate deadlock (comment out to continue)
        // WARNING: this WILL hang. Uncomment to observe, then stop manually.
        // ============================================================
        System.out.println("=== Deadlock Demo ===\n");
        System.out.println("[DEADLOCK PART SKIPPED to allow program to continue]");
        System.out.println("  Thread-1 would lock A then B; Thread-2 would lock B then A.");
        System.out.println("  Each holds one lock and waits for the other => deadlock.\n");

        /*
        Thread t1 = new Thread(() -> {
            try { unsafeTransfer(a, b, 100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Thread-1");
        Thread t2 = new Thread(() -> {
            try { unsafeTransfer(b, a, 200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Thread-2");
        t1.start(); t2.start();
        t1.join(); t2.join();  // hangs forever
        */

        // ============================================================
        // PART 2 — safe version with consistent lock order
        // ============================================================
        System.out.println("=== Fixed Version (consistent lock order) ===\n");

        BankAcc c = new BankAcc(1, 5000);
        BankAcc d = new BankAcc(2, 5000);

        Thread t3 = new Thread(() -> safeTransfer(c, d, 100), "Thread-1");
        Thread t4 = new Thread(() -> safeTransfer(d, c, 200), "Thread-2");

        t3.start(); t4.start();
        t3.join();  t4.join();

        System.out.println("\nFinal balances: " + c + "  |  " + d);
        System.out.println("Program completed without deadlock.");
    }
}
