package edu.gitam.oop.payroll.service;

import edu.gitam.oop.payroll.model.Employee;
import edu.gitam.oop.payroll.model.PaySlip;
import edu.gitam.oop.payroll.util.PerkBitmaskUtil;

/**
 * Syllabus Mapping:
 * - Unit 3 Topic 5: Every single StringBuffer method listed in the syllabus is purposefully used here!
 *   (constructors, length(), capacity(), ensureCapacity(), setLength(), charAt(), setCharAt(),
 *    getChars(), append(), insert(), reverse())
 */
public class PayslipFormatter {

    public static String formatAsciiSlip(PaySlip slip) {
        Employee emp = slip.getEmployee();
        PaySlip.SalaryBreakdown bd = slip.getBreakdown();

        // 1. StringBuffer constructor + capacity() + ensureCapacity()
        StringBuffer sb = new StringBuffer("PAYPULSE OFFICIAL SLIP\n");
        int initialCap = sb.capacity();
        sb.ensureCapacity(512);

        // 2. insert(): Inject "[CONFIDENTIAL] " right at index 0
        sb.insert(0, "[CONFIDENTIAL] ");

        // 3. charAt() and setCharAt(): Privacy-mask the bank account number in a StringBuffer
        StringBuffer maskedAcc = new StringBuffer(emp.getMaskedBankAccount());
        for (int i = 0; i < maskedAcc.length() - 4; i++) {
            if (maskedAcc.charAt(i) != '-') {
                maskedAcc.setCharAt(i, '*'); // Masks digits with '*'
            }
        }

        // 4. getChars(): Extract 3-character department code into a char[] array
        StringBuffer deptBuf = new StringBuffer(emp.getDepartment().replaceAll("[^A-Za-z]", "").toUpperCase() + "XXX");
        char[] deptCode = new char[3];
        deptBuf.getChars(0, 3, deptCode, 0);

        // 5. reverse() + setLength(): Create a cheeky reversed anti-tamper security watermark!
        StringBuffer watermark = new StringBuffer(emp.getSecurityToken());
        watermark.reverse(); // Reverses "EMP101-74200" -> "00247-101PME"
        if (watermark.length() > 14) {
            watermark.setLength(14); // Truncates using setLength() if too long
        }

        // 6. append(): Build the visual receipt box
        sb.append("==============================================================\n");
        sb.append(" Slip ID    : #").append(slip.getSlipId())
          .append(" | Period: ").append(slip.getPayPeriod())
          .append(" | DeptCode: ").append(new String(deptCode)).append("\n");
        sb.append(" Employee   : ").append(emp.getFullName()).append(" (ID: ").append(emp.getEmpId()).append(")\n");
        sb.append(" Designation: ").append(emp.getRoleTitle()).append("\n");
        sb.append(" Bank A/C   : ").append(maskedAcc).append("\n");
        sb.append(" Perks      : ").append(PerkBitmaskUtil.describeActivePerks(emp.getPerkBitmask())).append("\n");
        sb.append("--------------------------------------------------------------\n");
        sb.append(String.format(" Gross Earnings     : ₹%,10.2f\n", bd.getGrossPay()));
        sb.append(String.format(" Provident Fund (PF): -₹%,9.2f\n", bd.getPfDeducted()));
        sb.append(String.format(" Income Tax (TDS)   : -₹%,9.2f\n", bd.getTaxDeducted()));
        if (emp.getLossOfPayDeduction() > 0) {
            sb.append(String.format(" Loss of Pay (LOP)  : -₹%,9.2f\n", emp.getLossOfPayDeduction()));
        }
        sb.append("--------------------------------------------------------------\n");
        sb.append(String.format(" NET TAKE-HOME PAY  : ₹%,10.2f\n", bd.getUnboxedNetPay()));
        sb.append("--------------------------------------------------------------\n");
        sb.append(" Watermark Hash     : ").append(watermark)
          .append(" (BufCap: ").append(initialCap).append("->").append(sb.capacity()).append(")\n");
        sb.append("==============================================================\n");

        return sb.toString();
    }
}
