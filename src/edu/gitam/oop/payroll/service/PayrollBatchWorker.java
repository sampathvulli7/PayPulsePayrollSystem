package edu.gitam.oop.payroll.service;

import edu.gitam.oop.payroll.model.Employee;
import edu.gitam.oop.payroll.model.PaySlip;

import java.util.List;
import java.util.Vector;

/**
 * Syllabus Mapping:
 * - Unit 5: Concurrency (Multithreading) using Runnable, Thread.sleep(), and synchronized block
 * - Unit 3: Vector class (Thread-safe collection shared across multiple worker threads)
 */
public class PayrollBatchWorker implements Runnable {

    private final String workerName;
    private final List<Employee> assignedSlice;
    private final Vector<PaySlip> sharedSlipVault; // Vector is synchronized/thread-safe!
    private final int baseSequenceNumber;

    public PayrollBatchWorker(String workerName, List<Employee> assignedSlice,
                              Vector<PaySlip> sharedSlipVault, int baseSequenceNumber) {
        this.workerName = workerName;
        this.assignedSlice = assignedSlice;
        this.sharedSlipVault = sharedSlipVault;
        this.baseSequenceNumber = baseSequenceNumber;
    }

    @Override
    public void run() {
        int counter = 0;
        for (Employee emp : assignedSlice) {
            try {
                // Simulate heavy tax audit & bank gateway verification (180ms per employee)
                Thread.sleep(180);
                int slipNo = baseSequenceNumber + (++counter);
                PaySlip slip = PaySlip.generateFor(emp, slipNo);

                // Synchronized block ensures atomic logging alongside Vector insertion
                synchronized (sharedSlipVault) {
                    sharedSlipVault.addElement(slip); // Vector-specific method addElement()
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(workerName + " was interrupted!");
                break;
            }
        }
    }
}
