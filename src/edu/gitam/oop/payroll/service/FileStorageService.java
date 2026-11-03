package edu.gitam.oop.payroll.service;

import edu.gitam.oop.payroll.exception.NegativeSalaryException;
import edu.gitam.oop.payroll.model.*;
import edu.gitam.oop.payroll.model.PaySlip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static edu.gitam.oop.payroll.util.PayrollConstants.*;

/**
 * Syllabus Mapping:
 * - Unit 4: File I/O (File, FileReader, FileWriter, BufferedReader, BufferedWriter), static import
 * - Unit 5: Multiple Catch, using 'finally', using 'throws'
 */
public class FileStorageService {

    public static void exportPayrollToCsvAndTxt(List<Employee> employees, Vector<PaySlip> slips)
            throws IOException {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (slips == null || slips.size() != employees.size()) {
            slips = new Vector<>();
            int seq = 5001;
            for (Employee e : employees) {
                slips.addElement(PaySlip.generateFor(e, seq++));
            }
        }

        BufferedWriter csvWriter = null;
        BufferedWriter txtWriter = null;
        try {
            csvWriter = new BufferedWriter(new FileWriter(CSV_EXPORT_PATH));
            csvWriter.write("EmpID,FullName,Role,Department,BaseSalary,OvertimeHrs,PerkMask,GrossPay,Tax,PF,NetPay\n");

            for (Employee e : employees) {
                csvWriter.write(String.format("%d,\"%s\",\"%s\",%s,%.2f,%d,%d,%.2f,%.2f,%.2f,%.2f%n",
                        e.getEmpId(), e.getFullName(), e.getRoleTitle(), e.getDepartment(),
                        e.getBaseSalary(), e.getTotalOvertimeHours(), e.getPerkBitmask(),
                        e.calculateGrossPay(), e.calculateIncomeTax(), e.calculateProvidentFund(), e.calculateNetPay()));
            }

            txtWriter = new BufferedWriter(new FileWriter(TXT_ARCHIVE_PATH));
            for (PaySlip slip : slips) {
                txtWriter.write(PayslipFormatter.formatAsciiSlip(slip));
                txtWriter.write("\n");
            }

            exportHtmlDashboard(employees);
        } catch (SecurityException se) {
            System.err.println("File Permission Error: " + se.getMessage());
            throw se;
        } catch (IOException ioe) {
            System.err.println("Disk I/O Error while exporting files: " + ioe.getMessage());
            throw ioe;
        } finally {
            // Unit 5: Explicit 'finally' block guaranteeing resource cleanup
            if (csvWriter != null) {
                try { csvWriter.close(); } catch (IOException ignored) {}
            }
            if (txtWriter != null) {
                try { txtWriter.close(); } catch (IOException ignored) {}
            }
        }
    }

    /**
     * Synchronizes live employee roster into the interactive HTML spreadsheet dashboard using standard File I/O.
     */
    public static void exportHtmlDashboard(List<Employee> employees) {
        File file = new File(HTML_DASHBOARD_PATH);
        if (!file.exists()) return;

        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < employees.size(); i++) {
            Employee e = employees.get(i);
            json.append(String.format(
                "      { id: %d, name: \"%s\", role: \"%s\", dept: \"%s\", base: %.2f, ot: %d, perkMask: %d, gross: %.2f, tax: %.2f, pf: %.2f, net: %.2f }%s\n",
                e.getEmpId(), e.getFullName(), e.getRoleTitle(), e.getDepartment(),
                e.getBaseSalary(), e.getTotalOvertimeHours(), e.getPerkBitmask(),
                e.calculateGrossPay(), e.calculateIncomeTax(), e.calculateProvidentFund(), e.calculateNetPay(),
                (i < employees.size() - 1 ? "," : "")
            ));
        }
        json.append("    ];");

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            StringBuilder content = new StringBuilder();
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            reader = null;

            String full = content.toString();
            int startIdx = full.indexOf("const employees = [");
            int endIdx = full.indexOf("];", startIdx);
            if (startIdx != -1 && endIdx != -1) {
                String updated = full.substring(0, startIdx) + "const employees = " + json + full.substring(endIdx + 2);
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(updated);
            }
        } catch (IOException ignored) {
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException ignored) {}
            }
            if (writer != null) {
                try { writer.close(); } catch (IOException ignored) {}
            }
        }
    }

    /**
     * Unit 4 File I/O: Reads data/payroll_export.csv using BufferedReader and FileReader.
     */
    public static List<Employee> importEmployeesFromCsv() throws IOException, NegativeSalaryException {
        File file = new File(CSV_EXPORT_PATH);
        if (!file.exists()) {
            throw new IOException("CSV export file not found at " + CSV_EXPORT_PATH);
        }
        List<Employee> imported = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String header = reader.readLine(); // skip CSV header
            if (header == null) return imported;

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                List<String> cols = parseCsvLine(line);
                if (cols.size() < 7) continue;

                int empId = Integer.parseInt(cols.get(0));
                String fullName = cols.get(1);
                String role = cols.get(2);
                String department = cols.get(3);
                double baseSalary = Double.parseDouble(cols.get(4));
                int perkMask = Integer.parseInt(cols.get(6));

                String email = fullName.toLowerCase().replace(" ", ".") + "@gitam.edu";
                String bankAcc = "9876" + (10000000L + empId);

                Employee emp;
                if (role.contains("Manager")) {
                    int teamSize = 5;
                    if (role.contains("Team:")) {
                        try {
                            String teamStr = role.substring(role.indexOf("Team:") + 5).replace(")", "").trim();
                            teamSize = Integer.parseInt(teamStr);
                        } catch (Exception ignored) {}
                    }
                    emp = new Manager(fullName, email, bankAcc, department, baseSalary, perkMask, teamSize, 10_000.0);
                } else if (role.contains("Intern")) {
                    emp = new PartTimeIntern(fullName, department, 80, baseSalary > 0 ? baseSalary / 80.0 : 320.0);
                    emp.setPerkBitmask(perkMask);
                } else if (role.contains("Consultant")) {
                    emp = new ContractConsultant(fullName, email, bankAcc, department, baseSalary, perkMask, 4, 5000.0);
                } else {
                    emp = new FullTimeEmployee(fullName, email, bankAcc, department, baseSalary, perkMask, 1.0);
                }
                emp.setEmpId(empId);
                imported.add(emp);
            }
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException ignored) {}
            }
        }
        return imported;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString().trim());
        return tokens;
    }
}
