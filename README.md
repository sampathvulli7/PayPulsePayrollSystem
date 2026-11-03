# PayPulse — Employee Payroll Management System

[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](#prerequisites)
[![Architecture](https://img.shields.io/badge/Design-Pure%20OOP-orange.svg)](#core-concepts--usage)

A modular, zero-dependency Java payroll processing engine featuring role-based authentication, multi-threaded batch processing, bitwise perk encoding, and an interactive HTML audit ledger.

---

## 1. Overview

PayPulse simulates a modern enterprise compensation platform. It models diverse employment structures—including salaried engineers, engineering managers, hourly interns, and milestone-based contractors—calculating deductions, income tax slabs, provident fund contributions, and custom benefits.

### Key Capabilities
- **Role-Based Access Control**: Secure portal separation between Payroll Managers (full administrative control) and Employees/Interns (isolated self-service access).
- **Polymorphic Pay Engine**: Dynamically calculates pay structures across different employee contracts.
- **Concurrent Batch Processor**: Spawns concurrent worker threads synchronized over a thread-safe slip vault.
- **Bitwise Perk Ledger**: Encodes multiple employee perks into a compact integer bitmask.
- **Data Export & Sync**: Exports structured CSV spreadsheets, ASCII payslips, and a responsive single-page HTML audit ledger.

---

## 2. Core Concepts & Usage

The application is structured to demonstrate fundamental object-oriented and systems programming patterns in clear, real-world contexts:

| Concept | Where Used | Real-World Purpose |
| :--- | :--- | :--- |
| **Encapsulation & Access Control** | [`Employee.java`](src/edu/gitam/oop/payroll/model/Employee.java), [`PayrollRouter.java`](src/edu/gitam/oop/payroll/ui/PayrollRouter.java) | Encapsulates passwords and private state; uses `instanceof Manager` to enforce portal access boundaries. |
| **Inheritance & Polymorphism** | [`Person.java`](src/edu/gitam/oop/payroll/model/Person.java) & subclasses | Multi-level hierarchy (`Person` $\rightarrow$ `Employee` $\rightarrow$ `FullTimeEmployee` $\rightarrow$ `Manager`) with runtime method dispatch for gross and net pay. |
| **Bitwise Operators** | [`PerkBitmaskUtil.java`](src/edu/gitam/oop/payroll/util/PerkBitmaskUtil.java) | Packs employee perks (Coffee, Gym, WiFi, Cab) into a single integer mask using `&`, `|`, `^`, `~`, and left-shift (`<<`) for executive multipliers. |
| **Jagged 2D Arrays** | [`Employee.java`](src/edu/gitam/oop/payroll/model/Employee.java) | Models non-uniform work weeks where each week can have a different number of logged work days. |
| **Method Overloading & Varargs** | [`PayrollCalculator.java`](src/edu/gitam/oop/payroll/service/PayrollCalculator.java) | Supports flat bonuses, percentage appraisals, and variable micro-bonuses (`double...`) through overloaded signatures. |
| **String Manipulation (`StringBuffer`)** | [`PayslipFormatter.java`](src/edu/gitam/oop/payroll/service/PayslipFormatter.java) | Performs mutable string manipulation (account masking via `setCharAt`, code slicing via `getChars`, reversing watermarks). |
| **Interfaces & Nested Contracts** | [`contract/`](src/edu/gitam/oop/payroll/contract/) | Decouples payroll behaviors via `Payable`, `TaxablePayable`, and nested `AuditLoggable.Verifiable`. |
| **Custom Exception Hierarchy** | [`exception/`](src/edu/gitam/oop/payroll/exception/) | Domain-specific checked exceptions (`NegativeSalaryException`, `OvertimeBurnoutException`, `ExcessiveLeaveException`) extending `PayrollException`. |
| **Concurrency & Synchronization** | [`PayrollBatchWorker.java`](src/edu/gitam/oop/payroll/service/PayrollBatchWorker.java) | Concurrent worker threads (`Runnable`) safely appending payslips to a synchronized `Vector<PaySlip>`. |
| **Lambdas & Inner Classes** | [`PayrollCalculator.java`](src/edu/gitam/oop/payroll/service/PayrollCalculator.java), [`PaySlip.java`](src/edu/gitam/oop/payroll/model/PaySlip.java) | Uses lambda predicates for filtering/sorting and a Member Inner Class (`SalaryBreakdown`) for slip details. |
| **File I/O Streams** | [`FileStorageService.java`](src/edu/gitam/oop/payroll/service/FileStorageService.java) | Reads and writes CSV, text files, and syncs data to an interactive HTML ledger using `BufferedReader` and `BufferedWriter`. |

---

## 3. Project Architecture

```
PayPulsePayrollSystem/
├── data/
│   ├── payroll_dashboard.html   # Standalone interactive browser dashboard
│   ├── payroll_export.csv       # Standard 11-column CSV export
│   └── payslips_archive.txt     # ASCII-formatted generated payslips
├── src/edu/gitam/oop/payroll/
│   ├── MainApp.java             # Entry point & command-line argument parser
│   ├── contract/                # Interfaces (Payable, TaxablePayable, AuditLoggable)
│   ├── exception/               # Custom exception hierarchy
│   ├── model/                   # Domain entities (Person, Employee, Manager, etc.)
│   ├── service/                 # Services (Calculator, Formatter, BatchWorker, Storage)
│   ├── ui/                      # Interactive CLI menu router & input helpers
│   └── util/                    # Bitmask helpers and constant literals
├── tools/
│   ├── verify.sh                # Compiler linting & construct audit script
│   └── test_flows.sh            # Automated non-interactive test suite
├── paypulse.jar                 # Standalone pre-compiled executable JAR
├── run                          # Unix quick launcher
├── run.sh                       # Shell launch script
└── run.bat                      # Windows launch script
```

---

## 4. How to Run

### Prerequisites
- Java JDK 17 or higher (tested up to JDK 25).
- No external libraries or build tools (Maven/Gradle) required.

### Quick Start (One Command)

**On macOS / Linux:**
```bash
./run
```
*(or `./run.sh`)*

**On Windows:**
```bat
run.bat
```

### Run with Options
```bash
# Direct Manager CLI mode (bypasses portal for automation/admin)
./run --admin

# Automated non-interactive batch demo
./run --demo

# Disable ANSI terminal colors
./run --no-color
```

### Run via Standalone Executable JAR
```bash
# Interactive portal & login
java -jar paypulse.jar

# Direct Manager mode
java -jar paypulse.jar --admin

# Automated batch showcase
java -jar paypulse.jar --demo
```

### Manual Compilation
```bash
mkdir -p out data
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out edu.gitam.oop.payroll.MainApp
```

---

## 5. CLI Navigation & Role-Based Portals

When launched interactively, PayPulse greets the user with a role-based login portal:

### [1] Payroll Manager / HR Admin Portal (Full Access)
Allows managers to authenticate (prompting password creation on first setup) to access the complete administrative suite:
1. **View Staff Roster**: Displays active employees, their roles, pay structures, and audit records via polymorphic references.
2. **Hire New Employee**: Guides onboarding for Full-Time Engineers, Managers, Interns, or Consultants.
3. **Configure Perks**: Interactively toggle Coffee, Gym, WiFi, and Cab allowances using bitwise masks.
4. **Log Weekly Overtime**: Updates specific weeks in the employee's jagged attendance matrix.
5. **Award Spot Bonus**: Demonstrates overloaded bonus calculation (flat, percentage, or varargs micro-awards).
6. **Run Payroll Batch**: Spawns multi-threaded background workers with live terminal progress rendering.
7. **Filter & Sort Staff**: Ranks staff by net pay or filters high earners using lambda expressions.
8. **Export Payslips to Disk**: Exports active records to CSV, ASCII archive, and updates the HTML dashboard.
9. **Import Roster from CSV**: Parses and reconstructs polymorphic employee objects from an external CSV file.
10. **Manage Employees**: Search staff by name/department, promote eligible engineers to managers, or remove employees by ID.

### [2] Employee Self-Service Portal (Personal Access Only)
Employees, interns, and contractors log in using their Employee ID or Name (setting their personal password on first login). They can only view their own records:
1. **View My Payslip**: Displays an itemized salary breakdown (Base, OT, Perks, PF, TDS Tax, Net Take-Home).
2. **View My Benefits & Perks**: Shows active enrolled perks and their calculated monthly value.
3. **View My Overtime & Attendance**: Shows total overtime hours logged and individual sprint week breakdowns.

---

## 6. Verification & Automated Testing

The project includes built-in verification scripts:

```bash
# Lint check and compilation audit
./tools/verify.sh

# Run end-to-end automated test suite across all 12 menu & portal workflows
./tools/test_flows.sh
```
