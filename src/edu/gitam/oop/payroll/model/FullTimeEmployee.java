package edu.gitam.oop.payroll.model;

import edu.gitam.oop.payroll.exception.NegativeSalaryException;

import static edu.gitam.oop.payroll.util.PayrollConstants.*;

/**
 * Syllabus Mapping:
 * - Unit 3: Level 3 in Multilevel Inheritance (Person -> Employee -> FullTimeEmployee)
 * - Unit 3: Method Overriding & 'super' keyword
 * - Unit 1: Progressive Tax Slab calculation using if-else ladder & explicit casting
 */
public class FullTimeEmployee extends Employee {

    protected double performanceMultiplier;

    public FullTimeEmployee(String fullName, String email, String bankAcc, String department,
                            double baseSalary, int perkBitmask, double performanceMultiplier)
            throws NegativeSalaryException {
        super(fullName, email, bankAcc, department, baseSalary, perkBitmask);
        this.performanceMultiplier = performanceMultiplier;
    }

    public FullTimeEmployee(String fullName, String department, double baseSalary) throws NegativeSalaryException {
        super(fullName, department, baseSalary);
        this.performanceMultiplier = 1.0;
    }

    @Override
    public String getRoleTitle() {
        return "Full-Time Engineer";
    }

    public double calculateHRA() {
        return this.baseSalary * STANDARD_HRA_RATE;
    }

    public double calculateOvertimePay() {
        // Explicit type conversion from float constant to double
        return (double) getTotalOvertimeHours() * OVERTIME_HOURLY_RATE * performanceMultiplier;
    }

    @Override
    public double calculateGrossPay() {
        return this.baseSalary
                + calculateHRA()
                + calculateOvertimePay()
                + getPerkAllowance()
                + getAccumulatedSpotBonus();
    }

    @Override
    public double calculateProvidentFund() {
        return this.baseSalary * PF_DEDUCTION_RATE;
    }

    @Override
    public double calculateIncomeTax() {
        double gross = calculateGrossPay();
        double tax;
        // Unit 1: Control structure (if - else if - else ladder)
        if (gross <= 30_000) {
            tax = 0.0; // Tax-free slab
        } else if (gross <= 60_000) {
            tax = (gross - 30_000) * 0.10; // 10% on amount above 30k
        } else if (gross <= 100_000) {
            tax = (30_000 * 0.10) + ((gross - 60_000) * 0.20); // 20% slab
        } else {
            tax = (30_000 * 0.10) + (40_000 * 0.20) + ((gross - 100_000) * 0.30); // 30% slab
        }
        return tax;
    }

    @Override
    public double calculateNetPay() {
        double rawNet = calculateGrossPay() - (calculateIncomeTax() + calculateProvidentFund() + getLossOfPayDeduction());
        // Unit 1: Explicit narrowing cast (double -> int) and widening back to round off paise
        int roundedRupees = (int) (rawNet + 0.5);
        return (double) roundedRupees;
    }

    @Override
    public String getPayStructureSummary() {
        return "Base + 20% HRA + OT(" + getTotalOvertimeHours() + "h) + Perks - 12% PF - Slab Tax";
    }
}
