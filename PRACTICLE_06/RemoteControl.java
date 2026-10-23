// ============================================================
//  PRACTICLE 6 - Part A - Task 1: Remote Control
//  Interface Switchable with on()/off() and default toggle()
//  Fan and Light implement it; functional interface for on-rules
//  File: PRACTICLE_6/RemoteControl.java
// ============================================================

interface Switchable {
    void on();
    void off();

    default void toggle(boolean currentlyOn) {
        if (currentlyOn) off();
        else             on();
    }
}

// Functional interface: may a device switch on at a given hour?
@FunctionalInterface
interface SwitchRule {
    boolean canSwitchOn(Switchable device, int hour);
}

class Fan implements Switchable {
    private String name;
    private boolean isOn = false;

    public Fan(String name) { this.name = name; }

    @Override public void on()  { isOn = true;  System.out.println(name + " fan is ON");  }
    @Override public void off() { isOn = false; System.out.println(name + " fan is OFF"); }

    public boolean isOn() { return isOn; }

    @Override public String toString() { return name + " Fan"; }
}

class Light implements Switchable {
    private String name;
    private boolean isOn = false;

    public Light(String name) { this.name = name; }

    @Override public void on()  { isOn = true;  System.out.println(name + " light is ON");  }
    @Override public void off() { isOn = false; System.out.println(name + " light is OFF"); }

    public boolean isOn() { return isOn; }

    @Override public String toString() { return name + " Light"; }
}

public class RemoteControl {
    public static void main(String[] args) {
        System.out.println("=== Remote Control Demo ===\n");

        Fan   ceilingFan  = new Fan("Ceiling");
        Fan   tableFan    = new Fan("Table");
        Light livingLight = new Light("Living Room");
        Light kitchenLight= new Light("Kitchen");

        Switchable[] devices = { ceilingFan, tableFan, livingLight, kitchenLight };

        // Toggle all devices (all start OFF → turn ON)
        System.out.println("--- Toggling all devices ---");
        boolean[] states = { false, false, false, false };
        for (int i = 0; i < devices.length; i++) {
            devices[i].toggle(states[i]);
            states[i] = !states[i];
        }

        // Toggle again (all ON → turn OFF)
        System.out.println("\n--- Toggling again ---");
        for (int i = 0; i < devices.length; i++) {
            devices[i].toggle(states[i]);
            states[i] = !states[i];
        }

        // Functional interface — anonymous class
        System.out.println("\n--- Switch rule (anonymous class): only allow 8am-10pm ---");
        SwitchRule timeRule = new SwitchRule() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                return hour >= 8 && hour <= 22;
            }
        };

        // Functional interface — lambda
        System.out.println("--- Switch rule (lambda): same rule ---");
        SwitchRule lambdaRule = (device, hour) -> hour >= 8 && hour <= 22;

        int[] testHours = { 6, 9, 14, 23 };
        for (int h : testHours) {
            boolean allowed = lambdaRule.canSwitchOn(ceilingFan, h);
            System.out.printf("Hour %2d : canSwitchOn = %s%n", h, allowed);
        }
    }
}
