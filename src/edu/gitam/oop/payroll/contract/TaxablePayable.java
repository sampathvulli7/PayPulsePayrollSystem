package edu.gitam.oop.payroll.contract;

/**
 * Syllabus Mapping:
 * - Unit 4: Extending Interfaces (TaxablePayable extends Payable)
 */
public interface TaxablePayable extends Payable {
    double calculateIncomeTax();
    double calculateProvidentFund();
}
