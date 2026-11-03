package edu.gitam.oop.payroll;

import edu.gitam.oop.payroll.ui.PayrollRouter;
import edu.gitam.oop.payroll.ui.PayrollTheme;

/**
 * Syllabus Mapping:
 * - Unit 1: JDK entry point, Keywords, Identifiers
 * - Unit 2: Command-Line Arguments (String[] args)
 *   Usage:
 *     java edu.gitam.oop.payroll.MainApp             -> Interactive CLI mode
 *     java edu.gitam.oop.payroll.MainApp --demo      -> Automated non-interactive demo run
 *     java edu.gitam.oop.payroll.MainApp --no-color  -> Disables ANSI escape codes
 */
public class MainApp {

    public static void main(String[] args) {
        boolean autoDemoMode = false;

        // Unit 2 Topic 5: Parsing Command-Line Arguments
        for (String arg : args) {
            if ("--no-color".equalsIgnoreCase(arg)) {
                PayrollTheme.colorEnabled = false;
            } else if ("--demo".equalsIgnoreCase(arg)) {
                autoDemoMode = true;
            }
        }

        PayrollRouter router = new PayrollRouter();
        if (autoDemoMode) {
            PayrollTheme.printBanner();
            System.out.println(" [Command-Line Arg '--demo' detected] Running automated batch showcase...");
            router.runConcurrentBatchFlow();
        } else {
            router.startMenuLoop();
        }
    }
}
