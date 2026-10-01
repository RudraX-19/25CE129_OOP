// ============================================================
//  PRACTICLE 2 - Part A - Task 1: Smart Thermostat
//  File: PRACTICLE_2/SmartThermostat.java
// ============================================================

public class SmartThermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;
    
    // Constructor with location and start temperature
    public SmartThermostat(String location, int startTemp) {
        this.location = location;
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }
        activeCount++;
    }
    
    // Constructor with location only (chain)
    public SmartThermostat(String location) {
        this(location, 22);
    }
    
    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }
    
    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }
    
    public int getTemperature() {
        return temperature;
    }
    
    public static int getActiveCount() {
        return activeCount;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Smart Thermostat System ===\n");
        
        SmartThermostat thermostat1 = new SmartThermostat("Living Room", 20);
        SmartThermostat thermostat2 = new SmartThermostat("Bedroom");
        
        System.out.println("Thermostat 1 - Initial: " + thermostat1.getTemperature() + "°C");
        
        // Raise 10 times
        System.out.println("\nRaising temperature:");
        for (int i = 0; i < 10; i++) {
            thermostat1.raise();
            System.out.println("Temperature: " + thermostat1.getTemperature() + "°C");
        }
        
        // Lower 20 times
        System.out.println("\nLowering temperature:");
        for (int i = 0; i < 20; i++) {
            thermostat1.lower();
            System.out.println("Temperature: " + thermostat1.getTemperature() + "°C");
        }
        
        System.out.println("\nActive thermostats count: " + getActiveCount());
    }
}
