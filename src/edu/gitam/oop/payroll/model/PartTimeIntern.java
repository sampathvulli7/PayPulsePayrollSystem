package edu.gitam.oop.payroll.model;

import edu.gitam.oop.payroll.exception.NegativeSalaryException;

/**
 * Syllabus Mapping:
 * - Unit 3: Using 'final' to prevent inheritance (final class PartTimeIntern)
 * - Unit 3: Superclass reference (Employee) holding Subclass object (PartTimeIntern)
 */
public final class PartTimeIntern extends Employee {

    private int stipendHoursLogged;
    private double hourlyStipendRate;

    public PartTimeIntern(String fullName, String department, int stipendHoursLogged, double hourlyStipendRate)
            throws NegativeSalaryException {
        super(fullName, department, stipendHoursLogged * hourlyStipendRate);
        this.stipendHoursLogged = stipendHoursLogged;
        this.hourlyStipendRate = hourlyStipendRate;
    }

    @Override
    public String getRoleTitle() {
        return "Part-Time Intern";
    }

    @Override
    public double calculateGrossPay() {
        return (stipendHoursLogged * hourlyStipendRate) + getPerkAllowance() + getAccumulatedSpotBonus();
    }

    @Override
    public double calculateProvidentFund() {
        return 0.0; // Interns are exempt from Provident Fund deductions
    }

    @Override
    public double calculateIncomeTax() {
        // Flat 5% TDS only if stipend exceeds ₹25,000
        double gross = calculateGrossPay();
        return gross > 25_000 ? (gross - 25_000) * 0.05 : 0.0;
    }

    @Override
    public double calculateNetPay() {
        return (int) (calculateGrossPay() - calculateIncomeTax());
    }

    @Override
    public String getPayStructureSummary() {
        return "Hourly Stipend (" + stipendHoursLogged + "h @ ₹" + hourlyStipendRate + "/h) + Perks (No PF)";
    }
}
