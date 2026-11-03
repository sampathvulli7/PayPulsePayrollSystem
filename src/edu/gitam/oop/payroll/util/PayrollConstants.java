package edu.gitam.oop.payroll.util;

/**
 * Syllabus Mapping:
 * - Unit 1: Java's Primitive Types, Literals (Binary 0b, Hex 0x, Underscore numeric literals)
 * - Unit 3: Using final keyword
 * - Unit 4: Target for 'import static' in service classes
 */
public final class PayrollConstants {

    // Private constructor prevents instantiation of utility constants class
    private PayrollConstants() {}

    // Primitive literals with underscores for readability (JDK 7+ feature)
    public static final double MIN_BASE_SALARY = 10_000.00;
    public static final double STANDARD_HRA_RATE = 0.20;      // 20% House Rent Allowance
    public static final double PF_DEDUCTION_RATE = 0.12;      // 12% Provident Fund
    public static final float OVERTIME_HOURLY_RATE = 350.50f; // float literal with 'f'
    public static final byte MAX_DAILY_OVERTIME_HOURS = 12;   // byte primitive
    public static final short WORKING_DAYS_STANDARD = 22;     // short primitive
    public static final long COMPANY_REGISTRATION_ID = 98765432101234L; // long literal
    public static final char CURRENCY_SYMBOL = '₹';           // char literal
    public static final int AUDIT_MAGIC_HEADER = 0xCAFE;      // Hexadecimal literal

    // Bitwise Perk Masks using Binary Literals (0b prefix)
    public static final int PERK_NONE        = 0b0000; // 0
    public static final int PERK_FREE_COFFEE = 0b0001; // 1 (Bit 0) -> ₹500
    public static final int PERK_GYM_PASS    = 0b0010; // 2 (Bit 1) -> ₹1200
    public static final int PERK_WFH_WIFI    = 0b0100; // 4 (Bit 2) -> ₹1500
    public static final int PERK_NIGHT_CAB   = 0b1000; // 8 (Bit 3) -> ₹2000

    // File Paths
    public static final String DATA_DIR = "data";
    public static final String CSV_EXPORT_PATH = "data/payroll_export.csv";
    public static final String TXT_ARCHIVE_PATH = "data/payslips_archive.txt";
    public static final String HTML_DASHBOARD_PATH = "data/payroll_dashboard.html";
}
