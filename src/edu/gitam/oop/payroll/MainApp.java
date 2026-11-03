package edu.gitam.oop.payroll;

import edu.gitam.oop.payroll.ui.PayrollRouter;
import edu.gitam.oop.payroll.ui.PayrollTheme;

/**
 * Syllabus Mapping:
 * - Unit 1: JDK entry point, Keywords, Identifiers
 * - Unit 2: Command-Line Arguments (String[] args)
 *   Usage:
 *     java edu.gitam.oop.payroll.MainApp             -> Interactive Login & Portal Mode
 *     java edu.gitam.oop.payroll.MainApp --admin     -> Direct Manager / Admin CLI Mode
 *     java edu.gitam.oop.payroll.MainApp --demo      -> Automated non-interactive demo run
 *     java edu.gitam.oop.payroll.MainApp --no-color  -> Disables ANSI escape codes
 */
public class MainApp {

    public static void main(String[] args) {
        boolean autoDemoMode = false;
        boolean directAdminMode = false;

        // Unit 2 Topic 5: Parsing Command-Line Arguments
        for (String arg : args) {
            if ("--no-color".equalsIgnoreCase(arg)) {
                PayrollTheme.colorEnabled = false;
            } else if ("--demo".equalsIgnoreCase(arg)) {
                autoDemoMode = true;
            } else if ("--admin".equalsIgnoreCase(arg)) {
                directAdminMode = true;
            }
        }

        PayrollRouter router = new PayrollRouter();
        if (autoDemoMode) {
            PayrollTheme.printBanner();
            System.out.println(" [Command-Line Arg '--demo' detected] Running automated batch showcase...");
            router.runConcurrentBatchFlow();
        } else if (directAdminMode) {
            router.startManagerMenuLoop();
        } else {
            router.startPortalLoop();
        }
    }
}
