package edu.gitam.oop.payroll.model;

/**
 * Syllabus Mapping:
 * - Unit 2: Returning Objects from methods, Wrapper Classes (Integer, Double), Autoboxing & Unboxing
 * - Unit 5: Member Inner Class (PaySlip.SalaryBreakdown)
 */
public class PaySlip {

    private final Integer slipId;        // Wrapper class Integer (demonstrates Autoboxing)
    private final Employee employee;
    private final String payPeriod;
    private final SalaryBreakdown breakdown;

    public PaySlip(int primitiveSlipId, Employee employee) {
        // Autoboxing: primitive int automatically boxed into Integer wrapper object
        this.slipId = primitiveSlipId;
        this.employee = employee;
        this.payPeriod = "NOV-2026";
        this.breakdown = this.new SalaryBreakdown(); // Instantiating Member Inner Class
    }

    // Factory method demonstrating "Passing objects to methods" and "Returning Objects" (Unit 2)
    public static PaySlip generateFor(Employee emp, int sequenceNum) {
        return new PaySlip(sequenceNum, emp);
    }

    // Unit 5: Member Inner Class accessing outer PaySlip's private 'employee' field
    public class SalaryBreakdown {
        private final Double grossPay;     // Wrapper Double
        private final Double taxDeducted;
        private final Double pfDeducted;
        private final Double netPay;

        public SalaryBreakdown() {
            // Autoboxing primitive double into Double wrapper fields
            this.grossPay = employee.calculateGrossPay();
            this.taxDeducted = employee.calculateIncomeTax();
            this.pfDeducted = employee.calculateProvidentFund();
            this.netPay = employee.calculateNetPay();
        }

        public double getUnboxedNetPay() {
            // Unboxing: Wrapper Double automatically converted to primitive double
            return this.netPay;
        }

        public Double getGrossPay() { return grossPay; }
        public Double getTaxDeducted() { return taxDeducted; }
        public Double getPfDeducted() { return pfDeducted; }
    }

    public Integer getSlipId() { return slipId; }
    public Employee getEmployee() { return employee; }
    public String getPayPeriod() { return payPeriod; }
    public SalaryBreakdown getBreakdown() { return breakdown; }
}
