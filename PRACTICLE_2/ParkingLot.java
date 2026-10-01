// ============================================================
//  PRACTICLE 2 - Part A - Task 3: Parking Lot (Advanced)
//  File: PRACTICLE_2/ParkingLot.java
// ============================================================

public class ParkingLot {
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;
    
    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }
    
    public boolean park(String type) {
        if (type.equalsIgnoreCase("two")) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                return true;
            } else {
                System.out.println("Two-wheeler section Full!");
                return false;
            }
        } else if (type.equalsIgnoreCase("four")) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                return true;
            } else {
                System.out.println("Four-wheeler section Full!");
                return false;
            }
        }
        return false;
    }
    
    public void leave(String type) {
        if (type.equalsIgnoreCase("two")) {
            if (twoWheelers > 0) {
                twoWheelers--;
            }
        } else if (type.equalsIgnoreCase("four")) {
            if (fourWheelers > 0) {
                fourWheelers--;
            }
        }
    }
    
    public int getTwoWheelers() {
        return twoWheelers;
    }
    
    public int getFourWheelers() {
        return fourWheelers;
    }
    
    public static long getRevenue() {
        return revenue;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Parking Lot Management System ===\n");
        
        ParkingLot lot = new ParkingLot(3, 2);
        
        System.out.println("Parking lot capacity: 3 two-wheelers, 2 four-wheelers");
        System.out.println("Charges: Two-wheeler ₹20, Four-wheeler ₹40\n");
        
        // Simulate parking
        System.out.println("Parking 2 two-wheelers...");
        lot.park("two");
        lot.park("two");
        System.out.println("Occupancy: " + lot.getTwoWheelers() + " two-wheelers, " + 
                         lot.getFourWheelers() + " four-wheelers");
        
        System.out.println("\nParking 2 four-wheelers...");
        lot.park("four");
        lot.park("four");
        System.out.println("Occupancy: " + lot.getTwoWheelers() + " two-wheelers, " + 
                         lot.getFourWheelers() + " four-wheelers");
        
        System.out.println("\nParking 1 more two-wheeler...");
        lot.park("two");
        System.out.println("Occupancy: " + lot.getTwoWheelers() + " two-wheelers, " + 
                         lot.getFourWheelers() + " four-wheelers");
        
        System.out.println("\nTrying to park another two-wheeler (should be full)...");
        lot.park("two");
        
        System.out.println("\nTrying to park another four-wheeler (should be full)...");
        lot.park("four");
        
        System.out.println("\n1 two-wheeler leaving...");
        lot.leave("two");
        System.out.println("Occupancy: " + lot.getTwoWheelers() + " two-wheelers, " + 
                         lot.getFourWheelers() + " four-wheelers");
        
        System.out.println("\nParking 1 two-wheeler...");
        lot.park("two");
        System.out.println("Occupancy: " + lot.getTwoWheelers() + " two-wheelers, " + 
                         lot.getFourWheelers() + " four-wheelers");
        
        System.out.println("\n=== Final Report ===");
        System.out.println("Total revenue: ₹" + getRevenue());
        System.out.println("Current occupancy: " + lot.getTwoWheelers() + " two-wheelers, " + 
                         lot.getFourWheelers() + " four-wheelers");
    }
}
