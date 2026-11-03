package edu.gitam.oop.payroll.model;

import edu.gitam.oop.payroll.exception.NegativeSalaryException;

import static edu.gitam.oop.payroll.util.PayrollConstants.*;

/**
 * Syllabus Mapping:
 * - Unit 3: Dynamic Method Dispatch Extension (Superclass Employee holding ContractConsultant)
 * - Unit 3: Abstract Class method implementation & Method Overriding
 */
public class ContractConsultant extends Employee {

    private int milestonesCompleted;
    private double ratePerMilestone;

    public ContractConsultant(String fullName, String email, String bankAcc, String department,
                              double baseSalary, int perkBitmask, int milestonesCompleted, double ratePerMilestone)
            throws NegativeSalaryException {
        super(fullName, email, bankAcc, department, baseSalary, perkBitmask);
        this.milestonesCompleted = milestonesCompleted;
        this.ratePerMilestone = ratePerMilestone;
    }

    public ContractConsultant(String fullName, String department, double baseSalary,
                              int milestonesCompleted, double ratePerMilestone)
            throws NegativeSalaryException {
        super(fullName, department, baseSalary);
        this.milestonesCompleted = milestonesCompleted;
        this.ratePerMilestone = ratePerMilestone;
    }

    @Override
    public String getRoleTitle() {
        return "Contract Consultant";
    }

    @Override
    public double calculateGrossPay() {
        return this.baseSalary
                + (this.milestonesCompleted * this.ratePerMilestone)
                + getPerkAllowance()
                + getAccumulatedSpotBonus();
    }

    @Override
    public double calculateProvidentFund() {
        return 0.0; // Consultants are exempt from Provident Fund
    }

    @Override
    public double calculateIncomeTax() {
        // Flat 10% TDS on gross earnings
        return calculateGrossPay() * 0.10;
    }

    @Override
    public double calculateNetPay() {
        double rawNet = calculateGrossPay() - (calculateIncomeTax() + calculateProvidentFund() + getLossOfPayDeduction());
        int roundedRupees = (int) (rawNet + 0.5);
        return (double) roundedRupees;
    }

    @Override
    public String getPayStructureSummary() {
        return "Base + Milestones (" + milestonesCompleted + " @ ₹" + ratePerMilestone + ") + Perks - 10% TDS (No PF)";
    }

    public int getMilestonesCompleted() {
        return milestonesCompleted;
    }

    public void setMilestonesCompleted(int milestonesCompleted) {
        this.milestonesCompleted = milestonesCompleted;
    }

    public double getRatePerMilestone() {
        return ratePerMilestone;
    }

    public void setRatePerMilestone(double ratePerMilestone) {
        this.ratePerMilestone = ratePerMilestone;
    }
}

