// ============================================================
//  PRACTICLE 10 - Part A - Task 2: Producer-Consumer
//  One thread adds numbers to a small shared buffer; another
//  removes them — coordinated with wait() / notify()
//  File: PRACTICLE_10/ProducerConsumer.java
// ============================================================

class SharedBuffer {
    private final int[] buffer;
    private int count = 0;

    public SharedBuffer(int capacity) {
        this.buffer = new int[capacity];
    }

    // Called by producer
    public synchronized void produce(int value) throws InterruptedException {
        while (count == buffer.length) {   // buffer full — wait
            System.out.println("  [Producer] Buffer full, waiting...");
            wait();
        }
        buffer[count++] = value;
        System.out.println("  [Producer] Produced: " + value
                         + "  (buffer size=" + count + ")");
        notify();   // wake the consumer
    }

    // Called by consumer
    public synchronized int consume() throws InterruptedException {
        while (count == 0) {               // buffer empty — wait
            System.out.println("  [Consumer] Buffer empty, waiting...");
            wait();
        }
        int value = buffer[--count];
        System.out.println("  [Consumer] Consumed: " + value
                         + "  (buffer size=" + count + ")");
        notify();   // wake the producer
        return value;
    }
}

public class ProducerConsumer {
    static final int ITEMS = 8;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Producer-Consumer with wait/notify ===");
        System.out.println("Buffer capacity: 3  |  Items: " + ITEMS + "\n");

        SharedBuffer buf = new SharedBuffer(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) {
                    buf.produce(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException ignored) {}
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= ITEMS; i++) {
                    buf.consume();
                    Thread.sleep(200);
                }
            } catch (InterruptedException ignored) {}
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("\nAll " + ITEMS + " items produced and consumed — none lost.");
    }
}
