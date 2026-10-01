// ============================================================
//  PRACTICLE 1 - Part A - Task 2: Toll Booth System using Enum
//  File: PRACTICLE_1/TollBooth.java
// ============================================================

import java.util.Scanner;

public class TollBooth {
    
    enum VehicleType {
        CAR(50), BIKE(30), TRUCK(100), BUS(80);
        
        private final int toll;
        
        VehicleType(int toll) {
            this.toll = toll;
        }
        
        public int getToll() {
            return toll;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Highway Toll Booth System ===\n");
        System.out.println("Vehicle Types and Toll Charges:");
        System.out.println("--------------------------------");
        for (VehicleType vehicle : VehicleType.values()) {
            System.out.printf("%-10s : ₹%d%n", vehicle, vehicle.getToll());
        }
        
        int totalCollection = 0;
        int vehicleCount = 0;
        
        while (true) {
            System.out.print("\nEnter vehicle type (CAR/BIKE/TRUCK/BUS) or 'EXIT' to quit: ");
            String input = sc.next().toUpperCase();
            
            if (input.equals("EXIT")) {
                break;
            }
            
            try {
                VehicleType vehicle = VehicleType.valueOf(input);
                int toll = vehicle.getToll();
                totalCollection += toll;
                vehicleCount++;
                
                System.out.println("✓ " + vehicle + " passed - Toll collected: ₹" + toll);
                System.out.println("Total vehicles: " + vehicleCount + 
                                 " | Total collection: ₹" + totalCollection);
            } catch (IllegalArgumentException e) {
                System.out.println("✗ Invalid vehicle type! Please try again.");
            }
        }
        
        System.out.println("\n=== End of Day Report ===");
        System.out.println("Total Vehicles: " + vehicleCount);
        System.out.println("Total Collection: ₹" + totalCollection);
        
        sc.close();
    }
}
