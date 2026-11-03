package edu.gitam.oop.payroll.ui;

import edu.gitam.oop.payroll.contract.AuditLoggable;
import edu.gitam.oop.payroll.contract.Payable;
import edu.gitam.oop.payroll.exception.NegativeSalaryException;
import edu.gitam.oop.payroll.exception.OvertimeBurnoutException;
import edu.gitam.oop.payroll.model.*;
import edu.gitam.oop.payroll.service.*;
import edu.gitam.oop.payroll.util.PerkBitmaskUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static edu.gitam.oop.payroll.ui.PayrollTheme.*;
import static edu.gitam.oop.payroll.util.PayrollConstants.*;

/**
 * Controller & CLI Router
 * Demonstrates:
 * - Unit 1: Control structures (do-while, switch, for, enhanced-for)
 * - Unit 3: Dynamic dispatch, Vector vs ArrayList, Downcasting & Iterator
 * - Unit 4: Interface references (Payable, AuditLoggable)
 * - Unit 5: Multithreading and Exception Handling
 */
public class PayrollRouter {

    private final List<Employee> staffList = new ArrayList<>();
    private final Vector<PaySlip> batchSlipVault = new Vector<>();
    private final InputHelper input = new InputHelper();

    public PayrollRouter() {
        seedSampleEmployees();
    }

    private void seedSampleEmployees() {
        try {
            staffList.add(new Manager("Aarav Sharma", "aarav@gitam.edu", "458912349012", "Platform", 95_000, PERK_FREE_COFFEE | PERK_WFH_WIFI | PERK_NIGHT_CAB, 6, 12_000));
            staffList.add(new FullTimeEmployee("Diya Nair", "diya@gitam.edu", "771290438811", "Backend", 58_000, PERK_FREE_COFFEE | PERK_GYM_PASS, 1.15));
            staffList.add(new FullTimeEmployee("Rohan Verma", "rohan@gitam.edu", "883412095522", "Security", 44_000, PERK_WFH_WIFI, 1.0));
            staffList.add(new PartTimeIntern("Sneha Reddy", "AI-Lab", 80, 320.0));
            staffList.add(new ContractConsultant("Vikram Patel", "vikram@gitam.edu", "665544332211", "DevOps", 40_000, PERK_FREE_COFFEE | PERK_WFH_WIFI, 5, 4_000.0));
            staffList.add(new FullTimeEmployee("sampath vulli", "sampath@gitam.edu", "554433221100", "Marketing", 75_000, PERK_FREE_COFFEE | PERK_WFH_WIFI, 1.10));
        } catch (NegativeSalaryException e) {
            System.err.println("Seed error: " + e.getMessage());
        }
    }

    public void startMenuLoop() {
        PayrollTheme.printBanner();
        int choice;
        do {
            System.out.println(style(BOLD, "\n--- PAYROLL MAIN MENU ---"));
            System.out.println(" [1] View Staff Roster (Polymorphism)");
            System.out.println(" [2] Hire New Employee (Constructors)");
            System.out.println(" [3] Configure Perks (Bitwise Operators)");
            System.out.println(" [4] Log Weekly Overtime (Jagged 2D Arrays)");
            System.out.println(" [5] Award Spot Bonus (Method Overloading & Varargs)");
            System.out.println(" [6] Run Payroll Batch (Multithreading)");
            System.out.println(" [7] Filter & Sort Staff (Lambda & Inner Class)");
            System.out.println(" [8] Export Payslips to Disk (File I/O)");
            System.out.println(" [9] Import Roster from CSV (File I/O)");
            System.out.println(" [10] Manage Employees (Downcasting & Iterator)");
            System.out.println(" [0] Exit");

            choice = input.readInt("Select action [0-10]: ", 0, 10);
            switch (choice) {
                case 1: showRosterAndInterfaceDemo(); break;
                case 2: hireEmployeeFlow(); break;
                case 3: bitwisePerkFlow(); break;
                case 4: jaggedAttendanceFlow(); break;
                case 5: bonusOverloadAndVarargsFlow(); break;
                case 6: runConcurrentBatchFlow(); break;
                case 7: lambdaAndAnonymousFilterFlow(); break;
                case 8: exportFilesFlow(); break;
                case 9: importCsvFlow(); break;
                case 10: searchPromoteRemoveFlow(); break;
                case 0: System.out.println(style(GREEN, "Exiting PayPulse. Goodbye!")); break;
                default: System.out.println("Unknown option.");
            }
        } while (choice != 0);
    }

    private void showRosterAndInterfaceDemo() {
        System.out.println(style(CYAN, "\n=== Active Staff Roster (Example of Polymorphism) ==="));
        for (Employee emp : staffList) {
            System.out.println(" " + emp.toString());
            Payable payableRef = emp;
            AuditLoggable auditRef = emp;
            System.out.println("    ├─ Structure : " + payableRef.getPayStructureSummary()
                    + " | Perks: " + PerkBitmaskUtil.describeActivePerks(emp.getPerkBitmask()));
            System.out.println("    └─ Audit Log : " + auditRef.generateAuditRecord());
        }
    }

    private void hireEmployeeFlow() {
        System.out.println(style(CYAN, "\n=== Hire New Employee (Example of Constructors) ==="));
        int type = input.readInt("Role [1=Full-Time, 2=Manager, 3=Part-Time Intern, 4=Contract Consultant]: ", 1, 4);
        String name = input.readLineWithBufferedReader("Enter Full Name: ");
        String dept = input.readLineWithBufferedReader("Enter Department: ");

        try {
            Employee created;
            if (type == 1) {
                created = new FullTimeEmployee(name, dept, input.readDouble("Enter Monthly Base Salary (₹): "));
            } else if (type == 2) {
                double base = input.readDouble("Enter Manager Base Salary (₹): ");
                int team = input.readInt("Enter Team Size [1-50]: ", 1, 50);
                created = new Manager(name, name.toLowerCase().replace(" ", ".") + "@gitam.edu", "9988776655", dept, base, PERK_FREE_COFFEE | PERK_WFH_WIFI, team, 10_000);
            } else if (type == 3) {
                int hrs = input.readInt("Enter Monthly Stipend Hours [10-160]: ", 10, 160);
                created = new PartTimeIntern(name, dept, hrs, input.readDouble("Enter Hourly Stipend Rate (₹): "));
            } else {
                double base = input.readDouble("Enter Base Retainer Fee (₹): ");
                int milestones = input.readInt("Enter Milestones Completed [0-20]: ", 0, 20);
                created = new ContractConsultant(name, dept, base, milestones, input.readDouble("Enter Rate per Milestone (₹): "));
            }
            staffList.add(created);
            batchSlipVault.clear();
            System.out.println(style(GREEN, " -> Hired successfully: " + created));
        } catch (NegativeSalaryException e) {
            System.out.println(style(RED, " -> Blocked by Custom Exception: " + e.getMessage()));
        }
    }

    private void bitwisePerkFlow() {
        Employee emp = pickEmployee();
        if (emp == null) return;
        System.out.println(style(CYAN, "\n=== Configure Perks (Example of Bitwise Operators) ==="));
        System.out.println("Current Perks for " + emp.getFullName() + ": " + PerkBitmaskUtil.describeActivePerks(emp.getPerkBitmask()));
        System.out.println(" [1] Toggle Free Coffee (₹500)\n [2] Toggle Gym Pass    (₹1,200)\n [3] Toggle WFH WiFi    (₹1,500)\n [4] Toggle Night Cab   (₹2,000)\n [5] Revoke All Perks");

        int op = input.readInt("Choose option [1-5]: ", 1, 5);
        int mask = emp.getPerkBitmask();
        switch (op) {
            case 1: mask = PerkBitmaskUtil.togglePerk(mask, PERK_FREE_COFFEE); break;
            case 2: mask = PerkBitmaskUtil.togglePerk(mask, PERK_GYM_PASS); break;
            case 3: mask = PerkBitmaskUtil.togglePerk(mask, PERK_WFH_WIFI); break;
            case 4: mask = PerkBitmaskUtil.togglePerk(mask, PERK_NIGHT_CAB); break;
            case 5: mask = PERK_NONE; break;
        }
        emp.setPerkBitmask(mask);
        batchSlipVault.clear();
        System.out.println(style(GREEN, " -> Updated Perks: " + PerkBitmaskUtil.describeActivePerks(mask) + " | Allowance: ₹" + emp.getPerkAllowance()));
    }

    private void jaggedAttendanceFlow() {
        Employee emp = pickEmployee();
        if (emp == null) return;
        System.out.println(style(CYAN, "\n=== Weekly Overtime Log (Example of 2D Arrays) ==="));
        System.out.println("Current Attendance: " + AttendanceTracker.getOvertimeAnalyticsSummary(emp));
        int weekIdx = input.readInt("Which Week to log? [1-4]: ", 1, 4) - 1;
        int daysInWeek = input.readInt("How many working days this week? [1-7]: ", 1, 7);

        int[] newWeekLog = new int[daysInWeek];
        for (int d = 0; d < daysInWeek; d++) {
            newWeekLog[d] = input.readInt("  Day " + (d + 1) + " overtime hours [0-20]: ", 0, 20);
        }

        try {
            AttendanceTracker.updateWeekOvertime(emp, weekIdx, newWeekLog);
            batchSlipVault.clear();
            System.out.println(style(GREEN, " -> Updated! " + AttendanceTracker.getOvertimeAnalyticsSummary(emp)));
        } catch (OvertimeBurnoutException e) {
            System.out.println(style(RED, " -> Overtime Error: " + e.getMessage()));
        }
    }

    private void bonusOverloadAndVarargsFlow() {
        Employee emp = pickEmployee();
        if (emp == null) return;
        System.out.println(style(CYAN, "\n=== Award Spot Bonus (Example of Method Overloading) ==="));
        System.out.println(" [1] Flat Bonus Amount (Overloading)\n [2] Percentage of Base (Overloading)\n [3] Multiple Micro-Bonuses (Varargs)");
        int mode = input.readInt("Choose option [1-3]: ", 1, 3);
        try {
            if (mode == 1) {
                PayrollCalculator.applyBonus(emp, input.readDouble("Enter flat bonus (₹): "));
            } else if (mode == 2) {
                PayrollCalculator.applyBonus(emp, input.readInt("Enter percentage of base [1-100]: ", 1, 100));
            } else {
                double b1 = input.readDouble("  Bonus #1 (Bug Bounty ₹): ");
                double b2 = input.readDouble("  Bonus #2 (Hackathon ₹): ");
                double b3 = input.readDouble("  Bonus #3 (Mentorship ₹): ");
                PayrollCalculator.applyBonus(emp, "Spot-Bundle", b1, b2, b3);
            }
            batchSlipVault.clear();
            System.out.println(style(GREEN, " -> Total Spot Bonus for " + emp.getFullName() + " is now ₹" + emp.getAccumulatedSpotBonus()));
        } catch (NegativeSalaryException e) {
            System.out.println(style(RED, " -> " + e.getMessage()));
        }
    }

    public void runConcurrentBatchFlow() {
        System.out.println(style(CYAN, "\n=== Payroll Batch (Example of Multithreading) ==="));
        batchSlipVault.clear();

        int mid = staffList.size() / 2;
        Thread worker1 = new Thread(new PayrollBatchWorker("Worker-Alpha", staffList.subList(0, mid), batchSlipVault, 5000));
        Thread worker2 = new Thread(new PayrollBatchWorker("Worker-Beta", staffList.subList(mid, staffList.size()), batchSlipVault, 6000));
        worker1.start();
        worker2.start();

        while (worker1.isAlive() || worker2.isAlive()) {
            System.out.printf("\r [Batch Progress] Completed %d/%d slips...", batchSlipVault.size(), staffList.size());
            try { Thread.sleep(90); } catch (InterruptedException ignored) {}
        }
        System.out.printf("\r [Batch Progress] Completed %d/%d slips! (100%%)%n%n", batchSlipVault.size(), staffList.size());

        for (PaySlip slip : batchSlipVault) {
            System.out.println(PayslipFormatter.formatAsciiSlip(slip));
        }
    }

    private void lambdaAndAnonymousFilterFlow() {
        System.out.println(style(CYAN, "\n=== Filter & Sort Staff (Example of Lambda Expressions) ==="));
        System.out.println(style(YELLOW, "1) Ranked by Net Take-Home Pay (High to Low):"));
        for (Employee e : PayrollCalculator.sortByNetPayDescending(staffList)) {
            System.out.println("   " + e);
        }

        System.out.println(style(YELLOW, "\n2) High Earners (> ₹50,000 Net):"));
        List<Employee> highEarners = PayrollCalculator.filterEmployees(staffList, e -> e.calculateNetPay() > 50_000);
        for (Employee e : highEarners) {
            System.out.printf("   * %-18s -> Net: ₹%,.2f%n", e.getFullName(), e.calculateNetPay());
        }
    }

    private void exportFilesFlow() {
        System.out.println(style(CYAN, "\n=== Export Payslips (Example of File I/O) ==="));
        runConcurrentBatchFlow();
        try {
            FileStorageService.exportPayrollToCsvAndTxt(staffList, batchSlipVault);
            System.out.println(style(GREEN, " -> Exported CSV spreadsheet: " + CSV_EXPORT_PATH));
            System.out.println(style(GREEN, " -> Exported ASCII payslips  : " + TXT_ARCHIVE_PATH));
            System.out.println(style(GREEN, " -> Exported Web Dashboard   : " + HTML_DASHBOARD_PATH));
        } catch (IOException e) {
            System.out.println(style(RED, " -> Export failed: " + e.getMessage()));
        }
    }

    private void importCsvFlow() {
        System.out.println(style(CYAN, "\n=== Import Roster from CSV (Example of File Streams) ==="));
        try {
            List<Employee> imported = FileStorageService.importEmployeesFromCsv();
            staffList.clear();
            staffList.addAll(imported);
            batchSlipVault.clear();
            System.out.println(style(GREEN, " -> Successfully imported " + imported.size() + " employees from " + CSV_EXPORT_PATH));
        } catch (IOException | NegativeSalaryException e) {
            System.out.println(style(RED, " -> Import failed: " + e.getMessage()));
        }
    }

    private void searchPromoteRemoveFlow() {
        System.out.println(style(CYAN, "\n=== Manage Employees (Example of Dynamic Dispatch) ==="));
        System.out.println(" [1] Search by Name or Department\n [2] Promote Engineer to Manager\n [3] Remove Employee by ID");

        int sub = input.readInt("Select operation [1-3]: ", 1, 3);
        if (sub == 1) {
            String query = input.readString("Enter search keyword: ").toLowerCase();
            List<Employee> matches = PayrollCalculator.filterEmployees(staffList,
                    e -> e.getFullName().toLowerCase().contains(query) || e.getDepartment().toLowerCase().contains(query));
            if (matches.isEmpty()) {
                System.out.println(style(YELLOW, " -> No matching employees found for: " + query));
            } else {
                System.out.println(style(GREEN, " -> Found " + matches.size() + " matching employee(s):"));
                for (Employee e : matches) System.out.println("   " + e);
            }
        } else if (sub == 2) {
            Employee emp = pickEmployee();
            if (emp == null) return;
            if (emp instanceof FullTimeEmployee && !(emp instanceof Manager)) {
                FullTimeEmployee fte = (FullTimeEmployee) emp;
                int teamSize = input.readInt("Enter Managed Team Size [1-50]: ", 1, 50);
                try {
                    Manager promoted = new Manager(fte.getFullName(), fte.getEmail(), fte.getMaskedBankAccount(),
                            fte.getDepartment(), fte.getBaseSalary(), fte.getPerkBitmask(), teamSize, 12_000.0);
                    staffList.set(staffList.indexOf(emp), promoted);
                    batchSlipVault.clear();
                    System.out.println(style(GREEN, " -> Successfully promoted to Manager: " + promoted));
                } catch (NegativeSalaryException e) {
                    System.out.println(style(RED, " -> Promotion failed: " + e.getMessage()));
                }
            } else {
                System.out.println(style(YELLOW, " -> Employee is not an eligible Full-Time Engineer for Managerial promotion."));
            }
        } else if (sub == 3) {
            int targetId = input.readInt("Enter Employee ID to remove [100-9999]: ", 100, 9999);
            boolean removed = false;
            Iterator<Employee> it = staffList.iterator();
            while (it.hasNext()) {
                Employee e = it.next();
                if (e.getEmpId() == targetId) {
                    it.remove();
                    batchSlipVault.clear();
                    removed = true;
                    System.out.println(style(GREEN, " -> Successfully removed employee #" + targetId + " (" + e.getFullName() + ")"));
                    break;
                }
            }
            if (!removed) {
                System.out.println(style(YELLOW, " -> Employee ID #" + targetId + " not found in staff roster."));
            }
        }
    }

    private Employee pickEmployee() {
        if (staffList.isEmpty()) {
            System.out.println(style(YELLOW, " -> Staff roster is empty!"));
            return null;
        }
        for (int i = 0; i < staffList.size(); i++) {
            System.out.printf(" [%d] %s (%s)%n", i + 1, staffList.get(i).getFullName(), staffList.get(i).getRoleTitle());
        }
        int idx = input.readInt("Select employee [1-" + staffList.size() + "]: ", 1, staffList.size()) - 1;
        return staffList.get(idx);
    }
}
