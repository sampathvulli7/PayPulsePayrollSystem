@echo off
rem ============================================================
rem PayPulse Quick Runner (Windows)
rem ============================================================
if not exist out mkdir out
if not exist data mkdir data
dir /s /B src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
del sources.txt
java -cp out edu.gitam.oop.payroll.MainApp %*
