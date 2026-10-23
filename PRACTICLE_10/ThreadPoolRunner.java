// ============================================================
//  PRACTICLE 10 - Part A - Task 1: Thread-Pool Runner
//  Fixed pool of 3; submit ~10 Runnable tasks that print their
//  id and sleep briefly; shut down and await completion
//  File: PRACTICLE_10/ThreadPoolRunner.java
// ============================================================

import java.util.concurrent.*;

public class ThreadPoolRunner {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread-Pool Runner Demo ===");
        System.out.println("Pool size: 3  |  Tasks submitted: 10\n");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println("  Task-" + taskId
                        + " started on " + Thread.currentThread().getName());
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                System.out.println("  Task-" + taskId
                        + " done    on " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();
        boolean finished = pool.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("\nAll tasks finished: " + finished);
        System.out.println("(Notice only 3 thread names appear — pool threads are reused)");
    }
}
