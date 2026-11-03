package edu.gitam.oop.payroll.util;

import static edu.gitam.oop.payroll.util.PayrollConstants.*;

/**
 * Syllabus Mapping:
 * - Unit 1: Bitwise Operators (&, |, ^, ~, <<), Operator Precedence
 * Clever Implementation: Stores 4 distinct employee perks inside a single 32-bit int!
 */
public class PerkBitmaskUtil {

    // Bitwise OR (|) turns a specific perk bit ON
    public static int grantPerk(int currentMask, int perkBit) {
        return currentMask | perkBit;
    }

    // Bitwise AND (&) with Bitwise NOT (~) turns a specific perk bit OFF
    public static int revokePerk(int currentMask, int perkBit) {
        return currentMask & (~perkBit);
    }

    // Bitwise XOR (^) flips a perk bit (ON -> OFF, OFF -> ON)
    public static int togglePerk(int currentMask, int perkBit) {
        return currentMask ^ perkBit;
    }

    // Bitwise AND (&) checks if a specific bit is non-zero
    public static boolean hasPerk(int currentMask, int perkBit) {
        return (currentMask & perkBit) != 0;
    }

    // Calculates total monetary value of active perks; uses Left Shift (<<) for executive tier
    public static double calculatePerkAllowance(int currentMask, boolean isExecutiveTier) {
        int total = 0;
        if (hasPerk(currentMask, PERK_FREE_COFFEE)) total += 500;
        if (hasPerk(currentMask, PERK_GYM_PASS))    total += 1200;
        if (hasPerk(currentMask, PERK_WFH_WIFI))    total += 1500;
        if (hasPerk(currentMask, PERK_NIGHT_CAB))   total += 2000;

        // Cheeky Bitwise Left Shift: Executive tier doubles the allowance (total << 1 == total * 2)
        if (isExecutiveTier && total > 0) {
            total = total << 1;
        }
        return (double) total; // Explicit widening cast
    }

    public static String describeActivePerks(int currentMask) {
        if (currentMask == PERK_NONE) return "None (0b0000)";
        StringBuilder sb = new StringBuilder();
        if (hasPerk(currentMask, PERK_FREE_COFFEE)) sb.append("[Coffee] ");
        if (hasPerk(currentMask, PERK_GYM_PASS))    sb.append("[Gym] ");
        if (hasPerk(currentMask, PERK_WFH_WIFI))    sb.append("[WFH-WiFi] ");
        if (hasPerk(currentMask, PERK_NIGHT_CAB))   sb.append("[NightCab] ");
        sb.append(String.format("(Mask: %4s)", Integer.toBinaryString(currentMask)).replace(' ', '0'));
        return sb.toString();
    }
}
