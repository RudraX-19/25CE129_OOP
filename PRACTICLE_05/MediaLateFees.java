// ============================================================
//  PRACTICLE 5 - Part A - Task 3: Media Late Fees (Advanced)
//  Different media types each compute a late fee differently
//  File: PRACTICLE_5/MediaLateFees.java
// ============================================================

abstract class MediaItem {
    private String title;
    private int    daysOverdue;

    public MediaItem(String title, int daysOverdue) {
        this.title       = title;
        this.daysOverdue = daysOverdue;
    }

    public String getTitle()      { return title;       }
    public int    getDaysOverdue(){ return daysOverdue;  }

    public abstract double lateFee();

    @Override
    public String toString() {
        return String.format("%-12s %-28s  %3d day(s) late",
                             getClass().getSimpleName(), title, daysOverdue);
    }
}

class Book extends MediaItem {
    private static final double RATE = 5.0;
    public Book(String title, int daysOverdue) { super(title, daysOverdue); }

    @Override
    public double lateFee() { return getDaysOverdue() * RATE; }
}

class DVD extends MediaItem {
    private static final double RATE = 20.0;
    public DVD(String title, int daysOverdue) { super(title, daysOverdue); }

    @Override
    public double lateFee() { return getDaysOverdue() * RATE; }
}

class AudioBook extends MediaItem {
    private static final int    GRACE = 3;
    private static final double RATE  = 8.0;
    public AudioBook(String title, int daysOverdue) { super(title, daysOverdue); }

    @Override
    public double lateFee() {
        int billable = Math.max(0, getDaysOverdue() - GRACE);
        return billable * RATE;
    }
}

class Magazine extends MediaItem {
    private static final double FLAT = 15.0;
    public Magazine(String title, int daysOverdue) { super(title, daysOverdue); }

    @Override
    public double lateFee() { return getDaysOverdue() > 0 ? FLAT : 0.0; }
}

public class MediaLateFees {
    public static void main(String[] args) {

        MediaItem[] batch = {
            new Book     ("The Great Gatsby",     7),
            new DVD      ("Inception",             2),
            new AudioBook("Atomic Habits",         5),   // 2 billable days
            new Magazine ("National Geographic",   3),   // flat Rs 15
            new Book     ("Clean Code",            1),
            new DVD      ("Interstellar",         10),
            new AudioBook("Sapiens",               3),   // all grace, Rs 0
            new Magazine ("Time Apr 2025",         0),   // on time
        };

        double totalFees = 0;

        System.out.println("============  Library Late-Fee Statement  ============");
        System.out.printf("%-50s  %10s%n", "Item", "Fine (Rs)");
        System.out.println("-".repeat(63));

        for (MediaItem item : batch) {
            double fee = item.lateFee();
            totalFees += fee;
            System.out.printf("%-50s  %10.2f%n", item, fee);
        }

        System.out.println("-".repeat(63));
        System.out.printf("%-50s  %10.2f%n", "TOTAL FINES DUE (Rs)", totalFees);
        System.out.println("=".repeat(63));
    }
}
