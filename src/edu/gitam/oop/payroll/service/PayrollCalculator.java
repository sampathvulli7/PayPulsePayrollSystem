package edu.gitam.oop.payroll.service;

import edu.gitam.oop.payroll.exception.NegativeSalaryException;
import edu.gitam.oop.payroll.model.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Syllabus Mapping:
 * - Unit 2: Method Overloading & Variable-Length Arguments (varargs)
 * - Unit 5: Anonymous Inner Classes & Lambda Expressions
 */
public class PayrollCalculator {

    // 1. Overloaded Method #1: Flat festival bonus
    public static double applyBonus(Employee emp, double flatAmount) throws NegativeSalaryException {
        if (flatAmount < 0) throw new NegativeSalaryException(flatAmount);
        emp.addSpotBonus(flatAmount);
        return emp.getAccumulatedSpotBonus();
    }

    // 2. Overloaded Method #2: Percentage-based appraisal bonus
    public static double applyBonus(Employee emp, int percentageOfBase) throws NegativeSalaryException {
        if (percentageOfBase < 0) throw new NegativeSalaryException(percentageOfBase);
        double calculated = emp.getBaseSalary() * (percentageOfBase / 100.0);
        emp.addSpotBonus(calculated);
        return emp.getAccumulatedSpotBonus();
    }

    // 3. Overloaded Method #3 with VARARGS (double... spotAwards): Combine any number of micro-bonuses!
    public static double applyBonus(Employee emp, String campaignName, double... spotAwards)
            throws NegativeSalaryException {
        double sum = 0.0;
        for (double award : spotAwards) { // Enhanced for loop over varargs array
            if (award < 0) throw new NegativeSalaryException(award);
            sum += award;
        }
        emp.addSpotBonus(sum);
        System.out.printf(" -> Applied '%s' (%d micro-bonuses) totaling ₹%,.2f to %s%n",
                campaignName, spotAwards.length, sum, emp.getFullName());
        return emp.getAccumulatedSpotBonus();
    }

    // Unit 5: Sorting with a Lambda Expression
    public static List<Employee> sortByNetPayDescending(List<Employee> employees) {
        List<Employee> copy = new ArrayList<>(employees);
        // Lambda expression implementing Comparator<Employee>
        copy.sort((e1, e2) -> Double.compare(e2.calculateNetPay(), e1.calculateNetPay()));
        return copy;
    }

    // Unit 5: Sorting with an Anonymous Inner Class (great contrast with Lambda for viva!)
    public static List<Employee> sortByNameAlphabetical(List<Employee> employees) {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee a, Employee b) {
                return a.getFullName().compareToIgnoreCase(b.getFullName());
            }
        });
        return copy;
    }

    // Unit 5: Filtering using a Lambda Predicate
    public static List<Employee> filterEmployees(List<Employee> employees, Predicate<Employee> condition) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (condition.test(emp)) {
                result.add(emp);
            }
        }
        return result;
    }
}
