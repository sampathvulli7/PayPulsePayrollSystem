package edu.gitam.oop.payroll.exception;

/**
 * Cheeky Custom Exception #2: Thrown when overtime hours exceed safe human thresholds.
 */
public class OvertimeBurnoutException extends PayrollException {
    private static final long serialVersionUID = 1L;

    public OvertimeBurnoutException(String employeeName, int hours) {
        super("Burnout Alert for " + employeeName + " (" + hours + " OT hrs): Touch grass immediately! Max allowed is 45 hrs/month.", 4002);
    }
}
