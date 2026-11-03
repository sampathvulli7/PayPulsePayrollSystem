#!/usr/bin/env zsh
set -e

echo "==> [1/4] Cleaning & compiling with strict JDK 25 lint checks..."
rm -rf out
mkdir -p out data
javac -Xlint:all,-removal -Werror -encoding UTF-8 -d out $(find src -name "*.java")
echo "    [OK] Zero compilation errors and zero warnings!"

echo "==> [2/4] Auditing mandatory 24CSEN1011 syllabus constructs..."
grep -q "StringBuffer" src/edu/gitam/oop/payroll/service/PayslipFormatter.java || { echo "FAIL: StringBuffer missing!"; exit 1; }
grep -q "Vector<PaySlip>" src/edu/gitam/oop/payroll/service/PayrollBatchWorker.java || { echo "FAIL: Vector missing!"; exit 1; }
grep -q "int\[\]\[\] weeklyOvertimeHours" src/edu/gitam/oop/payroll/model/Employee.java || { echo "FAIL: Jagged 2D array missing!"; exit 1; }
grep -q "BufferedReader" src/edu/gitam/oop/payroll/ui/InputHelper.java || { echo "FAIL: BufferedReader missing!"; exit 1; }
echo "    [OK] All core syllabus constructs verified intact!"

echo "==> [3/4] Running non-interactive multithreaded batch demo..."
java -cp out edu.gitam.oop.payroll.MainApp --demo --no-color > /tmp/paypulse_demo.log
grep -q "NET TAKE-HOME PAY" /tmp/paypulse_demo.log && echo "    [OK] Multithreaded batch & StringBuffer slips generated!"

echo "==> [4/4] Validating CSV export for CSVLint..."
printf "8\n0\n" | java -cp out edu.gitam.oop.payroll.MainApp --no-color > /dev/null
awk -F',' 'NR==1 {cols=NF} NR>1 && NF!=cols {print "FAIL: CSV row " NR " has " NF " cols (expected " cols ")"; exit 1}' data/payroll_export.csv
echo "    [OK] data/payroll_export.csv passed column count validation!"
echo "✅ ALL CHECKS PASSED!"

