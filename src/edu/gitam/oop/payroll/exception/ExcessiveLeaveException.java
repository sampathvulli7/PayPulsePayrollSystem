package edu.gitam.oop.payroll.exception;

/**
 * Syllabus Mapping:
 * - Unit 5: Custom Exception Subclasses, Exception Hierarchy
 * Cheeky Custom Exception #3: Thrown when unapproved leave days exceed safe policy limit.
 */
public class ExcessiveLeaveException extends PayrollException {
    private static final long serialVersionUID = 1L;

    public ExcessiveLeaveException(String message) {
        super(message, 4003);
    }

    public ExcessiveLeaveException(int days) {
        super("Excessive Unapproved Leave [" + days + " days]: Cannot exceed 10 unapproved leave days per month!", 4003);
    }
}

