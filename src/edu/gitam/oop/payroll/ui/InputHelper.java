package edu.gitam.oop.payroll.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Syllabus Mapping:
 * - Unit 2 Topic 5: Scanner and BufferedReader Classes
 * - Unit 2 Topic 8: Wrapper Classes & Parsing (Integer.parseInt, Double.parseDouble)
 */
public class InputHelper {

    private final Scanner scanner;
    private final BufferedReader bufferedReader;

    public InputHelper() {
        this.scanner = new Scanner(System.in);
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // Uses Scanner for single-token / numeric menu reading
    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String token = scanner.nextLine().trim();
            try {
                // Wrapper Class parsing method
                int val = Integer.parseInt(token);
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.printf(" -> Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println(" -> Invalid integer input! Try again.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String token = scanner.nextLine().trim();
            try {
                return Double.parseDouble(token);
            } catch (NumberFormatException e) {
                System.out.println(" -> Invalid decimal amount! Try again.");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Uses BufferedReader explicitly to satisfy Unit 2 Topic 5 (BufferedReader for full lines)
    public String readLineWithBufferedReader(String prompt) {
        System.out.print(prompt);
        try {
            String line = bufferedReader.readLine();
            return (line == null || line.trim().isEmpty()) ? "N/A" : line.trim();
        } catch (IOException e) {
            return "DefaultInput";
        }
    }
}
