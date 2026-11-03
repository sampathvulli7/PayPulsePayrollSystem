package edu.gitam.oop.payroll.exception;

/**
 * Cheeky Custom Exception #1: Thrown when someone attempts to set a negative salary or bonus.
 */
public class NegativeSalaryException extends PayrollException {
    private static final long serialVersionUID = 1L;

    public NegativeSalaryException(double attemptedAmount) {
        super("HR Violation [Amt: " + attemptedAmount + "]: Employees work for money, they don't pay the company!", 4001);
    }
}
