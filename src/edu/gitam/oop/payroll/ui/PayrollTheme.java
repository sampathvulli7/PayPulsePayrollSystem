package edu.gitam.oop.payroll.ui;

/**
 * Companion Utility: Zero-dependency ANSI Escape Codes for a clean student CLI look.
 */
public class PayrollTheme {
    public static boolean colorEnabled = true;

    public static final String RESET  = "\u001B[0m";
    public static final String CYAN   = "\u001B[36m";
    public static final String GREEN  = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED    = "\u001B[31m";
    public static final String BOLD   = "\u001B[1m";

    public static String style(String color, String text) {
        return colorEnabled ? (color + text + RESET) : text;
    }

    public static void printBanner() {
        System.out.println(style(CYAN + BOLD,
                "\n============================================================\n" +
                "   PAYPULSE — Employee Payroll Management System\n" +
                "============================================================"));
    }
}
