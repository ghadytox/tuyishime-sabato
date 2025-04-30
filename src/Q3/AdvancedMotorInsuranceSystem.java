package Q3;

import java.time.LocalDate;
import java.util.*;

// ----------- Abstract Class ------------
abstract class InsurancePolicy {
    protected String policyId;
    protected Vehicle vehicle;
    protected Person policyHolder;
    protected double coverageAmount;
    protected double premiumAmount;
    protected LocalDate policyStartDate, policyEndDate;

    public InsurancePolicy(String policyId, Vehicle vehicle, Person holder, double coverageAmount,
                           LocalDate startDate, LocalDate endDate) {
        if (coverageAmount <= 0) throw new IllegalArgumentException("Coverage must be positive");
        this.policyId = policyId;
        this.vehicle = vehicle;
        this.policyHolder = holder;
        this.coverageAmount = coverageAmount;
        this.policyStartDate = startDate;
        this.policyEndDate = endDate;
    }

    public abstract void calculatePremium();
    public abstract boolean processClaim(Claim claim);
    public abstract void generatePolicyReport();
    public abstract boolean validatePolicy();
}

// ----------- Concrete Class ------------
class ComprehensivePolicy extends InsurancePolicy {
    public ComprehensivePolicy(String policyId, Vehicle vehicle, Person holder, double coverageAmount,
                               LocalDate start, LocalDate end) {
        super(policyId, vehicle, holder, coverageAmount, start, end);
        calculatePremium();
    }

    @Override
    public void calculatePremium() {
        int age = LocalDate.now().getYear() - vehicle.getVehicleYear();
        premiumAmount = coverageAmount * (0.02 + (age * 0.001)); // base + age factor
    }

    @Override
    public boolean processClaim(Claim claim) {
        if (claim.getClaimAmount() > coverageAmount) {
            claim.setStatus("Rejected: Exceeds coverage");
            return false;
        } else {
            claim.setStatus("Approved");
            return true;
        }
    }

    @Override
    public void generatePolicyReport() {
        System.out.println("\n--- Insurance Policy Report ---");
        System.out.println("Policy ID: " + policyId);
        System.out.println("Holder: " + policyHolder);
        System.out.println("Vehicle: " + vehicle);
        System.out.println("Coverage: $" + coverageAmount);
        System.out.println("Premium: $" + premiumAmount);
        System.out.println("Valid From: " + policyStartDate + " To: " + policyEndDate);
    }

    @Override
    public boolean validatePolicy() {
        return vehicle.getVehicleYear() >= 2000;
    }
}

// ----------- Encapsulated Classes ------------
class Vehicle {
    private String vehicleId, make, model, type;
    private int year;

    public Vehicle(String id, String make, String model, int year, String type) {
        if (year < 1980 || year > LocalDate.now().getYear()) throw new IllegalArgumentException("Invalid year");
        this.vehicleId = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    public int getVehicleYear() {
        return year;
    }

    public String toString() {
        return make + " " + model + " (" + year + ", " + type + ")";
    }
}

class Person {
    private String personId, name, email, phone;
    private LocalDate dob;

    public Person(String id, String name, LocalDate dob, String email, String phone) {
        if (!email.contains("@") || phone.length() < 10) throw new IllegalArgumentException("Invalid contact details");
        this.personId = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
    }

    public String toString() {
        return name + " | DOB: " + dob + " | Email: " + email + " | Phone: " + phone;
    }
}

class Claim {
    private String claimId;
    private double claimAmount;
    private LocalDate claimDate;
    private String claimStatus;

    public Claim(String id, double amount, LocalDate date) {
        this.claimId = id;
        this.claimAmount = amount;
        this.claimDate = date;
        this.claimStatus = "Pending";
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setStatus(String status) {
        this.claimStatus = status;
    }

    public String toString() {
        return "Claim ID: " + claimId + " | Amount: $" + claimAmount +
                " | Date: " + claimDate + " | Status: " + claimStatus;
    }
}

// ----------- Main Program ------------
public class AdvancedMotorInsuranceSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input Person
        System.out.println("Enter Policyholder Info:");
        System.out.print("Person ID: ");
        String pid = sc.nextLine();
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        System.out.print("DOB (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(sc.nextLine());
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        Person person = new Person(pid, name, dob, email, phone);

        // Input Vehicle
        System.out.println("\nEnter Vehicle Info:");
        System.out.print("Vehicle ID: ");
        String vid = sc.nextLine();
        System.out.print("Make: ");
        String make = sc.nextLine();
        System.out.print("Model: ");
        String model = sc.nextLine();
        System.out.print("Year: ");
        int year = sc.nextInt(); sc.nextLine();
        System.out.print("Type (Car/Bike/etc): ");
        String type = sc.nextLine();
        Vehicle vehicle = new Vehicle(vid, make, model, year, type);

        // Policy Info
        System.out.println("\nEnter Insurance Policy Info:");
        System.out.print("Policy ID: ");
        String polId = sc.nextLine();
        System.out.print("Coverage Amount: ");
        double coverage = sc.nextDouble(); sc.nextLine();
        System.out.print("Start Date (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(sc.nextLine());
        System.out.print("End Date (YYYY-MM-DD): ");
        LocalDate end = LocalDate.parse(sc.nextLine());

        ComprehensivePolicy policy = new ComprehensivePolicy(polId, vehicle, person, coverage, start, end);

        // Validate Policy
        if (!policy.validatePolicy()) {
            System.out.println("Policy is not valid due to vehicle restrictions.");
        }

        // Claim
        System.out.println("\nEnter Claim Info:");
        System.out.print("Claim ID: ");
        String claimId = sc.nextLine();
        System.out.print("Claim Amount: ");
        double claimAmount = sc.nextDouble(); sc.nextLine();
        System.out.print("Claim Date (YYYY-MM-DD): ");
        LocalDate claimDate = LocalDate.parse(sc.nextLine());

        Claim claim = new Claim(claimId, claimAmount, claimDate);
        policy.processClaim(claim);

        // Output Report
        policy.generatePolicyReport();
        System.out.println("\n--- Claim Info ---");
        System.out.println(claim);

        sc.close();
    }
}

