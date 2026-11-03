#!/usr/bin/env zsh
set -e

echo "=== Running Non-Interactive Test Suite for PayPulse CLI ==="

echo "--> [1/12] Test Option [1] (Roster & Polymorphism)..."
printf "1\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [2/12] Test Option [2] (Hire Full-Time Employee)..."
printf "2\n1\nKiran Kumar\nCloudOps\n65000\n1\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [3/12] Test Option [2] (NegativeSalaryException)..."
printf "2\n1\nBad Input\nQA\n-5000\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [4/12] Test Option [3] (Bitwise Perk Toggle)..."
printf "3\n1\n2\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [5/12] Test Option [4] (OvertimeBurnoutException Rethrow)..."
printf "4\n1\n1\n5\n12\n12\n12\n12\n12\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [6/12] Test Option [5] (Varargs Spot Bonus Bundle)..."
printf "5\n2\n3\n1500\n2500\n1000\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [7/12] Test Option [7] (Lambda & Anonymous Inner Sort)..."
printf "7\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [8/12] Test Option [8] (CSV & TXT Disk Export)..."
printf "8\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [9/12] Test Option [9] (CSV Import & Reload Staff Roster)..."
printf "8\n9\n1\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [10/12] Test Option [10] (Search, Promote & Remove Employee)..."
printf "10\n1\nDiya\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
printf "10\n2\n2\n8\n1\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
printf "10\n3\n103\n1\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --admin --no-color > /dev/null
echo "    [OK]"

echo "--> [11/12] Test Manager Portal Login & Password Setup..."
printf "1\nadmin\nsecret123\n1\n0\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --no-color > /dev/null
echo "    [OK]"

echo "--> [12/12] Test Employee Self-Service Login & Personal Payslip (Intern #104)..."
printf "2\n104\ninternpass\n1\n2\n3\n0\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --no-color > /dev/null
echo "    [OK]"

echo "✅ ALL MENU & PORTAL FLOW TESTS PASSED SUCCESSFULLY!"
