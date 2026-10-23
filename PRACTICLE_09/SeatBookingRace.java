// ============================================================
//  PRACTICLE 9 - Part A - Task 2: Seat Booking Race
//  Shared seatsLeft=5; ~10 threads each try to book one seat.
//  Without sync: more than 5 can succeed (oversell).
//  With synchronized book(): exactly 5 succeed.
//  File: PRACTICLE_9/SeatBookingRace.java
// ============================================================

import java.util.concurrent.atomic.AtomicInteger;

class UnsafeBooking {
    private int seatsLeft = 5;
    private final AtomicInteger successCount = new AtomicInteger(0);

    public boolean book(String threadName) {
        if (seatsLeft > 0) {
            // Artificial delay to expose the race condition
            try { Thread.sleep(1); } catch (InterruptedException ignored) {}
            seatsLeft--;
            successCount.incrementAndGet();
            System.out.println("  [UNSAFE] " + threadName + " booked a seat  (seatsLeft=" + seatsLeft + ")");
            return true;
        }
        return false;
    }

    public int getSuccessCount() { return successCount.get(); }
}

class SafeBooking {
    private int seatsLeft = 5;
    private final AtomicInteger successCount = new AtomicInteger(0);

    public synchronized boolean book(String threadName) {
        if (seatsLeft > 0) {
            seatsLeft--;
            successCount.incrementAndGet();
            System.out.println("  [SAFE ] " + threadName + " booked a seat  (seatsLeft=" + seatsLeft + ")");
            return true;
        }
        return false;
    }

    public int getSuccessCount() { return successCount.get(); }
}

public class SeatBookingRace {

    static final int NUM_THREADS = 10;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Seat Booking Race Demo ===");
        System.out.println("Available seats: 5  |  Threads trying to book: " + NUM_THREADS);
        System.out.println("Expected successful bookings: 5\n");

        // ---- WITHOUT synchronization ----
        System.out.println("--- WITHOUT synchronization ---");
        UnsafeBooking unsafe = new UnsafeBooking();
        Thread[] unsafeThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            final String name = "T-" + (i + 1);
            unsafeThreads[i] = new Thread(() -> unsafe.book(name), name);
            unsafeThreads[i].start();
        }
        for (Thread t : unsafeThreads) t.join();

        System.out.println("Successful bookings (unsafe): " + unsafe.getSuccessCount()
                         + "  (may be > 5 — oversell!)\n");

        // ---- WITH synchronization ----
        System.out.println("--- WITH synchronization ---");
        SafeBooking safe = new SafeBooking();
        Thread[] safeThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            final String name = "T-" + (i + 1);
            safeThreads[i] = new Thread(() -> safe.book(name), name);
            safeThreads[i].start();
        }
        for (Thread t : safeThreads) t.join();

        System.out.println("Successful bookings (safe)  : " + safe.getSuccessCount()
                         + "  (always exactly 5)");
    }
}
