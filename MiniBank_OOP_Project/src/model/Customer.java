package model;

/* ================================================================
   Practical 2 — Part B (MiniBank Customer & Account classes)
   ================================================================
   Customer class — encapsulation with private fields, static id
   generation, and public getters. No public setters.
   ================================================================ */

public class Customer {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;

    private static long customerCounter = 100;

    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Customer{id='" + customerId + "', name='" + name
                + "', email='" + email + "', mobile='" + mobile + "'}";
    }
}
