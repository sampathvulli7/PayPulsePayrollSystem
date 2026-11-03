package edu.gitam.oop.payroll.contract;

/**
 * Syllabus Mapping:
 * - Unit 4: Nested Interface (AuditLoggable.Verifiable)
 */
public interface AuditLoggable {
    String generateAuditRecord();

    // Nested Interface inside AuditLoggable
    interface Verifiable {
        boolean verifyIntegrity();
        String getSecurityToken();
    }
}
