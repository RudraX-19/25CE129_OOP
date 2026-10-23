// ============================================================
//  PRACTICLE 2 - Part A - Task 2: Cinema Show
//  File: PRACTICLE_2/CinemaShow.java
// ============================================================

public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;
    
    // Constructor with title and capacity
    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }
    
    // Constructor with title only (chain)
    public CinemaShow(String title) {
        this(title, 100);
    }
    
    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }
    
    public void cancel(int n) {
        seatsAvailable += n;
        if (seatsAvailable > capacity) {
            seatsAvailable = capacity;
        }
    }
    
    public int getSeatsAvailable() {
        return seatsAvailable;
    }
    
    public static int getTotalBooked() {
        return totalBooked;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Cinema Show Booking System ===\n");
        
        CinemaShow show = new CinemaShow("Avengers Endgame", 50);
        
        System.out.println("Show: Avengers Endgame");
        System.out.println("Initial seats available: " + show.getSeatsAvailable());
        
        // Book 30 seats
        System.out.println("\nBooking 30 seats...");
        boolean result1 = show.book(30);
        System.out.println("Booking successful: " + result1);
        System.out.println("Seats available: " + show.getSeatsAvailable());
        
        // Book 15 seats
        System.out.println("\nBooking 15 seats...");
        boolean result2 = show.book(15);
        System.out.println("Booking successful: " + result2);
        System.out.println("Seats available: " + show.getSeatsAvailable());
        
        // Try to book 10 seats (should fail - only 5 left)
        System.out.println("\nBooking 10 seats...");
        boolean result3 = show.book(10);
        System.out.println("Booking successful: " + result3);
        System.out.println("Seats available: " + show.getSeatsAvailable());
        
        // Cancel 20 seats
        System.out.println("\nCancelling 20 seats...");
        show.cancel(20);
        System.out.println("Seats available: " + show.getSeatsAvailable());
        
        // Book 10 seats again
        System.out.println("\nBooking 10 seats...");
        boolean result4 = show.book(10);
        System.out.println("Booking successful: " + result4);
        System.out.println("Seats available: " + show.getSeatsAvailable());
        
        System.out.println("\nTotal tickets booked across all shows: " + getTotalBooked());
    }
}
