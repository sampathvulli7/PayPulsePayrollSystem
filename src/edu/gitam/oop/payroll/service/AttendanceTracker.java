package edu.gitam.oop.payroll.service;

import edu.gitam.oop.payroll.exception.OvertimeBurnoutException;
import edu.gitam.oop.payroll.model.Employee;

import java.util.Arrays;

/**
 * Syllabus Mapping:
 * - Unit 3: 1D Arrays, Multidimensional & Irregular Arrays, Arrays class of java.util package
 * - Unit 5: Throwing and Rethrowing an Exception
 */
public class AttendanceTracker {

    // Updates a specific week's daily overtime array inside the jagged 2D matrix
    public static void updateWeekOvertime(Employee emp, int weekIndex, int[] dailyHours)
            throws OvertimeBurnoutException {
        try {
            int[][] currentMatrix = emp.getWeeklyOvertimeHours();
            currentMatrix[weekIndex] = dailyHours; // Array reference assignment (can be any length!)

            int totalMonthlyOt = emp.getTotalOvertimeHours();
            if (totalMonthlyOt > 45) {
                throw new OvertimeBurnoutException(emp.getFullName(), totalMonthlyOt);
            }
        } catch (OvertimeBurnoutException e) {
            System.out.println(" [Log] Caught burnout exception locally, rethrowing to UI (Example of Rethrow)...");
            throw e; // Unit 5: Rethrowing an exception to the caller!
        }
    }

    // Uses java.util.Arrays utility methods (sort, copyOf, toString) on a flattened 1D array
    public static String getOvertimeAnalyticsSummary(Employee emp) {
        int[][] jagged = emp.getWeeklyOvertimeHours();
        int totalDaysLogged = 0;
        for (int[] week : jagged) { // Enhanced for loop
            totalDaysLogged += week.length;
        }

        // Flatten jagged 2D array into 1D array for statistical sorting
        int[] flatDays = new int[totalDaysLogged];
        int idx = 0;
        for (int[] week : jagged) {
            for (int hrs : week) {
                flatDays[idx++] = hrs;
            }
        }

        int[] sortedCopy = Arrays.copyOf(flatDays, flatDays.length);
        Arrays.sort(sortedCopy); // java.util.Arrays.sort()

        StringBuilder report = new StringBuilder();
        report.append("Jagged Week Lengths: [");
        for (int i = 0; i < jagged.length; i++) {
            report.append("W").append(i + 1).append("=").append(jagged[i].length).append("d");
            if (i < jagged.length - 1) report.append(", ");
        }
        report.append("] | Sorted Daily OT: ").append(Arrays.toString(sortedCopy));
        return report.toString();
    }
}
