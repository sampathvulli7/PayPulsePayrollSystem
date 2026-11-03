package edu.gitam.oop.payroll.model;

/**
 * Syllabus Mapping:
 * - Unit 3: Inheritance Basics, Multilevel Hierarchy (Level 1: Person), Member Access (protected)
 */
public class Person {
    protected String fullName;
    protected String email;
    protected String maskedBankAccount;

    public Person() {
        this("Unnamed Person", "none@gitam.edu", "0000000000");
    }

    public Person(String fullName, String email, String maskedBankAccount) {
        this.fullName = fullName;
        this.email = email;
        this.maskedBankAccount = maskedBankAccount;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMaskedBankAccount() {
        return maskedBankAccount;
    }
}
