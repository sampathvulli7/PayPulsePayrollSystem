package edu.gitam.oop.payroll.model;

import edu.gitam.oop.payroll.contract.AuditLoggable;
import edu.gitam.oop.payroll.contract.TaxablePayable;
import edu.gitam.oop.payroll.exception.ExcessiveLeaveException;
import edu.gitam.oop.payroll.exception.NegativeSalaryException;
import edu.gitam.oop.payroll.util.PerkBitmaskUtil;

import static edu.gitam.oop.payroll.util.PayrollConstants.*;

/**
 * Syllabus Mapping:
 * - Unit 2: Encapsulation, 'this' keyword, Overloading Constructors, static counter, finalize()
 * - Unit 3: Abstract class, Level 2 in Multilevel Hierarchy, Irregular (Jagged) 2D array field
 * - Unit 4: Implementing Multiple Interfaces (TaxablePayable, AuditLoggable, AuditLoggable.Verifiable)
 */
public abstract class Employee extends Person implements TaxablePayable, AuditLoggable, AuditLoggable.Verifiable {

    // static variable shared across all Employee instances for auto-ID generation
    private static int nextEmployeeSequence = 101;
    private static int totalActiveObjects = 0;

    private int empId;
    private String department;
    protected double baseSalary;
    private int perkBitmask;
    private double accumulatedSpotBonus;
    private int unapprovedLeaveDays;

    // Irregular (Jagged) 2D Array: 4 weeks, each week can have different number of logged days!
    private int[][] weeklyOvertimeHours;

    // Constructor 1: Full Parameterized Constructor
    public Employee(String fullName, String email, String bankAcc, String department, double baseSalary, int perkBitmask)
            throws NegativeSalaryException {
        super(fullName, email, bankAcc); // Calls Person constructor
        if (baseSalary < 0) {
            throw new NegativeSalaryException(baseSalary);
        }
        this.empId = nextEmployeeSequence++;
        this.department = department;
        this.baseSalary = baseSalary;
        this.perkBitmask = perkBitmask;
        this.accumulatedSpotBonus = 0.0;
        this.initDefaultJaggedAttendance();
        totalActiveObjects++;
    }

    // Constructor 2: Overloaded Constructor (Default Perks & Bank Account)
    public Employee(String fullName, String department, double baseSalary) throws NegativeSalaryException {
        this(fullName, fullName.toLowerCase().replace(" ", ".") + "@corp.in", "9876543210", department, baseSalary, PERK_FREE_COFFEE);
    }

    // Initializes an Irregular (Jagged) 2D Array where rows have different lengths (3, 5, 6, 4 days)
    private void initDefaultJaggedAttendance() {
        this.weeklyOvertimeHours = new int[4][];
        this.weeklyOvertimeHours[0] = new int[]{1, 0, 2};          // Week 1: 3-day onboarding week
        this.weeklyOvertimeHours[1] = new int[]{0, 1, 1, 0, 2};    // Week 2: 5-day normal week
        this.weeklyOvertimeHours[2] = new int[]{2, 2, 0, 1, 3, 1}; // Week 3: 6-day release sprint
        this.weeklyOvertimeHours[3] = new int[]{0, 0, 1, 1};       // Week 4: 4-day holiday week
    }

    // Abstract method: every subclass must provide its own role title
    public abstract String getRoleTitle();

    // Concrete helper using jagged array and 'length' member
    public int getTotalOvertimeHours() {
        int sum = 0;
        for (int week = 0; week < weeklyOvertimeHours.length; week++) {
            for (int day = 0; day < weeklyOvertimeHours[week].length; day++) {
                int hrs = weeklyOvertimeHours[week][day];
                if (hrs <= 0) {
                    continue; // Unit 1: 'continue' skips zero-overtime days
                }
                if (hrs > MAX_DAILY_OVERTIME_HOURS) {
                    break;    // Unit 1: 'break' stops processing corrupted daily logs
                }
                sum += hrs;
            }
        }
        return sum;
    }

    public double getPerkAllowance() {
        return PerkBitmaskUtil.calculatePerkAllowance(this.perkBitmask, false);
    }

    // Implementing AuditLoggable & Nested Interface AuditLoggable.Verifiable
    @Override
    public String generateAuditRecord() {
        return String.format("AUDIT[0x%X] EMP#%d (%s) Gross=%.2f Net=%.2f",
                AUDIT_MAGIC_HEADER, empId, fullName, calculateGrossPay(), calculateNetPay());
    }

    @Override
    public boolean verifyIntegrity() {
        return this.baseSalary >= 0 && this.empId >= 100;
    }

    @Override
    public String getSecurityToken() {
        return "EMP" + this.empId + "-" + (int) calculateNetPay();
    }

    // Unit 2: finalize() method demonstration (Note: deprecated in modern JDKs, included for Unit 2 syllabus)
    @SuppressWarnings({"deprecation", "removal"})
    @Override
    protected void finalize() throws Throwable {
        try {
            totalActiveObjects--;
        } finally {
            super.finalize();
        }
    }

    // Getters & Setters (Encapsulation)
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) {
        this.empId = empId;
        if (empId >= nextEmployeeSequence) {
            nextEmployeeSequence = empId + 1;
        }
    }
    public static void resetSequence(int next) {
        nextEmployeeSequence = next;
    }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getBaseSalary() { return baseSalary; }
    public int getPerkBitmask() { return perkBitmask; }
    public void setPerkBitmask(int perkBitmask) { this.perkBitmask = perkBitmask; }
    public double getAccumulatedSpotBonus() { return accumulatedSpotBonus; }
    public void addSpotBonus(double amount) { this.accumulatedSpotBonus += amount; }
    public int[][] getWeeklyOvertimeHours() { return weeklyOvertimeHours; }
    public void setWeeklyOvertimeHours(int[][] weeklyOvertimeHours) { this.weeklyOvertimeHours = weeklyOvertimeHours; }
    public int getUnapprovedLeaveDays() { return unapprovedLeaveDays; }
    public void logUnapprovedLeaves(int days) throws ExcessiveLeaveException {
        if (days > 10) {
            throw new ExcessiveLeaveException(days);
        }
        this.unapprovedLeaveDays = Math.max(0, days);
    }
    public double getLossOfPayDeduction() {
        return (this.baseSalary / WORKING_DAYS_STANDARD) * this.unapprovedLeaveDays;
    }
    public static int getTotalActiveObjects() { return totalActiveObjects; }

    @Override
    public String toString() {
        return String.format("#%d | %-18s | %-16s | %-10s | Base: ₹%,9.2f | OT: %2d hrs | Net: ₹%,9.2f",
                empId, fullName, getRoleTitle(), department, baseSalary, getTotalOvertimeHours(), calculateNetPay());
    }
}
