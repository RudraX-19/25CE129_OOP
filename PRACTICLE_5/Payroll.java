// ============================================================
//  PRACTICLE 5 - Part A - Task 2: Payroll
//  Abstract Employee with abstract monthlySalary();
//  FullTime (fixed), PartTime (hours x rate), Intern (stipend)
//  File: PRACTICLE_5/Payroll.java
// ============================================================

abstract class Employee {
    private String name;
    private String id;

    public Employee(String name, String id) {
        this.name = name;
        this.id   = id;
    }

    public String getName() { return name; }
    public String getId()   { return id;   }

    public abstract double monthlySalary();

    @Override
    public String toString() {
        return String.format("[%s] %s", id, name);
    }
}

class FullTime extends Employee {
    private double fixedSalary;

    public FullTime(String name, String id, double fixedSalary) {
        super(name, id);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public double monthlySalary() { return fixedSalary; }
}

class PartTime extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public PartTime(String name, String id, double hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate  = hourlyRate;
    }

    @Override
    public double monthlySalary() { return hoursWorked * hourlyRate; }
}

class Intern extends Employee {
    private double stipend;

    public Intern(String name, String id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    public double monthlySalary() { return stipend; }
}

public class Payroll {
    public static void main(String[] args) {

        Employee[] staff = {
            new FullTime("Alice Johnson", "FT-001", 75000),
            new PartTime("Bob Smith",     "PT-001", 80, 350),
            new Intern  ("Carol White",   "IN-001", 15000),
            new FullTime("David Brown",   "FT-002", 68000),
            new PartTime("Eva Green",     "PT-002", 60, 400),
            new Intern  ("Frank Lee",     "IN-002", 12000)
        };

        double totalPayroll = 0;

        System.out.println("========== Monthly Payroll Report ==========");
        System.out.printf("%-30s %-10s %12s   %s%n",
                          "Employee", "Type", "Salary (Rs)", "Note");
        System.out.println("-".repeat(65));

        for (Employee e : staff) {
            double salary = e.monthlySalary();
            totalPayroll += salary;

            String type;
            if      (e instanceof FullTime) type = "Full-Time";
            else if (e instanceof PartTime) type = "Part-Time";
            else                            type = "Intern";

            // instanceof to add intern-only note
            String note = (e instanceof Intern)
                          ? "** Intern - check tax exemption"
                          : "";

            System.out.printf("%-30s %-10s %12.2f   %s%n",
                              e, type, salary, note);
        }

        System.out.println("-".repeat(65));
        System.out.printf("%-41s %12.2f%n", "TOTAL PAYROLL (Rs)", totalPayroll);
        System.out.println("=".repeat(65));
    }
}
