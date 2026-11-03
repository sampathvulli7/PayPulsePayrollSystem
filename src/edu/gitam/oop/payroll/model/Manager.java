package edu.gitam.oop.payroll.model;

import edu.gitam.oop.payroll.exception.NegativeSalaryException;
import edu.gitam.oop.payroll.util.PerkBitmaskUtil;

/**
 * Syllabus Mapping:
 * - Unit 3: Level 4 Multilevel Hierarchy (Person -> Employee -> FullTimeEmployee -> Manager)
 * - Unit 3: Calling super.calculateGrossPay(), using 'final' on a method
 */
public class Manager extends FullTimeEmployee {

    private int teamSize;
    private double leadershipAllowance;

    public Manager(String fullName, String email, String bankAcc, String department,
                   double baseSalary, int perkBitmask, int teamSize, double leadershipAllowance)
            throws NegativeSalaryException {
        super(fullName, email, bankAcc, department, baseSalary, perkBitmask, 1.25);
        this.teamSize = teamSize;
        this.leadershipAllowance = leadershipAllowance;
    }

    @Override
    public String getRoleTitle() {
        return "Engineering Manager (Team: " + teamSize + ")";
    }

    // Overriding getPerkAllowance to activate the cheeky Bitwise Left-Shift (2x executive perks)
    @Override
    public double getPerkAllowance() {
        return PerkBitmaskUtil.calculatePerkAllowance(getPerkBitmask(), true);
    }

    // 'final' prevents any further subclass from altering how Manager gross pay is totaled
    @Override
    public final double calculateGrossPay() {
        // Calls superclass FullTimeEmployee.calculateGrossPay() via 'super'
        return super.calculateGrossPay() + this.leadershipAllowance + (this.teamSize * 750.0);
    }

    public int getTeamSize() {
        return teamSize;
    }
}
