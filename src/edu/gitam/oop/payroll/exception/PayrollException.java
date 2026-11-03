package edu.gitam.oop.payroll.exception;

/**
 * Syllabus Mapping:
 * - Unit 5: Creating Exception Subclasses, Exception Hierarchy, Superclass Constructor Invocation
 */
public class PayrollException extends Exception {
    private static final long serialVersionUID = 1L;
    private final int errorCode;

    public PayrollException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PayrollException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
