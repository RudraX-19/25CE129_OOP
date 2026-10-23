// ============================================================
//  PRACTICLE 9 - Part A - Task 3: Parallel Array Sum (Advanced)
//  Split a large array across threads; show wrong result without
//  sync, then fix two ways and compare
//  File: PRACTICLE_9/ParallelArraySum.java
// ============================================================

import java.util.concurrent.atomic.AtomicLong;

public class ParallelArraySum {

    static final int ARRAY_SIZE  = 100_000;
    static final int NUM_THREADS = 4;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Parallel Array Sum Demo ===");
        System.out.println("Array size: " + ARRAY_SIZE
                         + "  |  Threads: " + NUM_THREADS + "\n");

        // Build array of 1s — expected sum = ARRAY_SIZE
        int[] data = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) data[i] = 1;

        int chunk = ARRAY_SIZE / NUM_THREADS;

        // ---- 1. WITHOUT synchronization (wrong result) ----
        final long[] unsafeTotal = { 0 };
        Thread[] t1 = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunk;
            int end   = (i == NUM_THREADS - 1) ? ARRAY_SIZE : start + chunk;
            t1[i] = new Thread(() -> {
                for (int k = start; k < end; k++) {
                    unsafeTotal[0] += data[k];   // race condition
                }
            });
            t1[i].start();
        }
        for (Thread t : t1) t.join();
        System.out.println("Without sync : " + unsafeTotal[0]
                         + "  (expected " + ARRAY_SIZE + ", likely wrong)");

        // ---- 2. FIX A: synchronized block ----
        final long[] safeTotal = { 0 };
        final Object lock = new Object();
        Thread[] t2 = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunk;
            int end   = (i == NUM_THREADS - 1) ? ARRAY_SIZE : start + chunk;
            t2[i] = new Thread(() -> {
                long localSum = 0;
                for (int k = start; k < end; k++) localSum += data[k];
                synchronized (lock) { safeTotal[0] += localSum; }
            });
            t2[i].start();
        }
        for (Thread t : t2) t.join();
        System.out.println("With sync    : " + safeTotal[0]
                         + "  (always exactly " + ARRAY_SIZE + ")");

        // ---- 3. FIX B: AtomicLong ----
        AtomicLong atomicTotal = new AtomicLong(0);
        Thread[] t3 = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunk;
            int end   = (i == NUM_THREADS - 1) ? ARRAY_SIZE : start + chunk;
            t3[i] = new Thread(() -> {
                long localSum = 0;
                for (int k = start; k < end; k++) localSum += data[k];
                atomicTotal.addAndGet(localSum);
            });
            t3[i].start();
        }
        for (Thread t : t3) t.join();
        System.out.println("With AtomicL : " + atomicTotal.get()
                         + "  (always exactly " + ARRAY_SIZE + ")");
    }
}
