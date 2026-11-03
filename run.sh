#!/usr/bin/env bash
# ============================================================
# PayPulse Quick Runner
# ============================================================
set -e
mkdir -p out data
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out edu.gitam.oop.payroll.MainApp "$@"
