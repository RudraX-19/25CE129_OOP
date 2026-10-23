// ============================================================
//  PRACTICLE 9 - Part A - Task 1: Counter Race
//  Several threads increment a shared counter; first without
//  synchronization (wrong total), then with (correct total)
//  File: PRACTICLE_9/CounterRace.java
// ============================================================

class UnsafeCounter {
    private int count = 0;
    public void increment() { count++; }
    public int  getCount()  { return count; }
}

class SafeCounter {
    private int count = 0;
    public synchronized void increment() { count++; }
    public int getCount() { return count; }
}

public class CounterRace {

    static final int THREADS     = 5;
    static final int INCREMENTS  = 1000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Counter Race Demo ===");
        System.out.println("Threads: " + THREADS
                         + "  |  Increments each: " + INCREMENTS
                         + "  |  Expected total: " + (THREADS * INCREMENTS) + "\n");

        // ---- WITHOUT synchronization ----
        UnsafeCounter unsafe = new UnsafeCounter();
        Thread[] unsafeThreads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    unsafe.increment();
                }
            });
            unsafeThreads[i].start();
        }
        for (Thread t : unsafeThreads) t.join();

        System.out.println("WITHOUT synchronization: " + unsafe.getCount()
                         + "  (likely < " + (THREADS * INCREMENTS) + ", varies each run)");

        // ---- WITH synchronization ----
        SafeCounter safe = new SafeCounter();
        Thread[] safeThreads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    safe.increment();
                }
            });
            safeThreads[i].start();
        }
        for (Thread t : safeThreads) t.join();

        System.out.println("WITH    synchronization: " + safe.getCount()
                         + "  (always exactly " + (THREADS * INCREMENTS) + ")");
    }
}
