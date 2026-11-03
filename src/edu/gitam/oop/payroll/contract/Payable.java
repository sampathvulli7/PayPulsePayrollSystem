package edu.gitam.oop.payroll.contract;

/**
 * Syllabus Mapping:
 * - Unit 4: Creating and Implementing an Interface, Interface Fundamentals
 */
public interface Payable {
    double calculateGrossPay();
    double calculateNetPay();
    String getPayStructureSummary();
}
