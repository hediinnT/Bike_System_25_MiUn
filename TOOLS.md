# 🛠️ Testing & Quality Tools Guide

[![Build Status](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/ci.yml?branch=master&label=CI%20Build&logo=github)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/ci.yml)
[![Coverage Check](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/coverage-check.yml?branch=master&label=Coverage&logo=codecov)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/coverage-check.yml)
[![Security Check](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/dependency-security.yml?branch=master&label=Security&logo=security)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/dependency-security.yml)
[![Test Report](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/test-report.yml?branch=master&label=Tests&logo=github-actions)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/test-report.yml)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-red?logo=apache-maven)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5.10.0-green?logo=junit5)](https://junit.org/junit5/)
[![License](https://img.shields.io/badge/license-MIT-blue)](LICENSE)

## Overview

This document describes the testing and quality assurance tools configured in this project.

---

## 📊 Tool Categories

1. **Test Execution**: JUnit 5, Mockito
2. **Code Coverage**: JaCoCo
3. **Mutation Testing**: PIT (Pitest)
4. **Static Analysis**: SpotBugs, Checkstyle, PMD
5. **CI/CD**: GitHub Actions
6. **Build Tool**: Maven
7. **Behavioral Analysis**: CodeScene

---

## 🧪 1. Test Execution Frameworks

### JUnit 5 (Jupiter)
**Version**: 5.10.0  
**Purpose**: Core testing framework for unit and integration tests

**Usage**:
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BikeServiceTest

# Run specific test method
mvn test -Dtest=BikeServiceTest#testRentBike
```

**Example**:
```java
@Test
void shouldCreateBike() {
    Bike bike = new Bike("BIKE001", BikeType.STANDARD);
    assertEquals("BIKE001", bike.getId());
}
```

---

### Mockito
**Version**: 5.5.0  
**Purpose**: Mocking framework for isolating units under test

**Example**:
```java
@Mock
private BankIDService bankIDService;

@InjectMocks
private User user;

@Test
void shouldAuthenticateUser() {
    when(bankIDService.authenticate("199001012384")).thenReturn(true);
    boolean result = user.authenticate("199001012384");
    assertTrue(result);
}
```

---

## 📈 2. Code Coverage Analysis

### JaCoCo (Java Code Coverage)

![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.10-green?logo=java)
![Coverage](https://img.shields.io/badge/coverage-report-blue?logo=codecov)

**Version**: 0.8.10  
**Purpose**: Measure code coverage metrics

**Metrics**:
- Line Coverage: % of executable lines executed
- Branch Coverage: % of decision branches taken
- Method Coverage: % of methods invoked
- Class Coverage: % of classes loaded

**Usage**:
```bash
# Generate coverage report
mvn clean test jacoco:report

# View report
# Open: target/site/jacoco/index.html
```

**Report Colors**:
- 🟢 **Green**: Code is covered by tests
- 🟡 **Yellow**: Partial branch coverage
- 🔴 **Red**: Code not covered by tests

---

## 🧬 3. Mutation Testing

### PIT (Pitest)
**Version**: 1.15.8  
**Purpose**: Evaluate test quality by introducing bugs (mutations)

**How It Works**:
1. Introduces small changes (mutations) to your code
2. Runs your tests against mutated code
3. Tests should FAIL on mutated code (kill the mutation)
4. If tests still PASS, the mutation "survived" = weak test

**Usage**:
```bash
# Run mutation analysis
mvn clean test org.pitest:pitest-maven:mutationCoverage

# View results
# Open: target/pit-reports/index.html
```

**Key Metrics**:
- **Line Coverage**: What code was executed
- **Mutation Coverage**: What mutations were killed
- **Mutation Score**: % of mutations killed

**Report Interpretation**:
- 🟢 **Green Line**: Mutation killed (good test)
- 🔴 **Red Line**: Mutation survived (weak test)
- ⚫ **Gray Line**: No coverage (code not executed)

---

## 🔍 4. Static Analysis & Code Quality

### SpotBugs
**Version**: 4.8.3  
**Purpose**: Static analysis to detect potential bugs

**Bug Categories**:
- Bad practice
- Correctness issues
- Malicious code vulnerability
- Performance problems
- Dodgy code patterns
- Security vulnerabilities

**Usage**:
```bash
# Step 1: Compile the project (REQUIRED)
mvn clean compile

# Step 2: Run SpotBugs analysis
mvn spotbugs:spotbugs

# Step 3: View results in GUI
mvn spotbugs:gui

# Alternative: View HTML report
# Open: target/site/spotbugs.html
```

**Important**: SpotBugs analyzes bytecode, so compilation is required first.

---

### Checkstyle
**Version**: 3.3.1  
**Purpose**: Enforce coding standards and style consistency

**What It Checks**:
- Code style (indentation, whitespace, naming)
- Code structure (class design, method length)
- Best practices (Javadoc, unused imports, magic numbers)
- Formatting (line length, brackets, spacing)

**Usage**:
```bash
# Run Checkstyle analysis
mvn checkstyle:checkstyle

# View report
# Open: target/site/checkstyle.html
```

**Rule Set**: Google Java Style Guide (`google_checks.xml`)

---

### PMD (Programming Mistake Detector)
**Version**: 3.21.2 (PMD Core: 6.55.0)  
**Purpose**: Source code analyzer for detecting code quality issues

**⚠️ Note**: PMD 6.55.0 has limited support for Java 21 features. Configured with `targetJdk=17` as workaround.

**What It Detects**:
- Code smells (duplicate code, dead code)
- Design issues (God classes, tight coupling)
- Performance issues (inefficient operations)
- Best practices (unused variables, empty catch blocks)
- Error-prone code (null pointer risks, resource leaks)

**Usage**:
```bash
# Run PMD analysis
mvn pmd:pmd

# Run Copy-Paste Detector
mvn pmd:cpd

# View reports
# Open: target/site/pmd.html
# Open: target/site/cpd.html
```

**Rule Set**: Java Quickstart (`quickstart.xml`)

---

### Static Analysis Tools Comparison

| Tool | Purpose | Analyzes |
|------|---------|----------|
| **SpotBugs** | Find bugs | Bytecode (.class files) |
| **PMD** | Code quality | Source code (.java files) |
| **Checkstyle** | Code style | Source code (.java files) |

**Together**: These provide comprehensive quality analysis.

---

## 🤖 5. Continuous Integration

### GitHub Actions

[![Build](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/ci.yml?branch=master&label=CI%20Pipeline&logo=github-actions)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/ci.yml)
[![Coverage Check](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/coverage-check.yml?branch=master&label=Coverage&logo=codecov)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/coverage-check.yml)
[![Security](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/dependency-security.yml?branch=master&label=Security&logo=github-actions)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/dependency-security.yml)
[![Test Report](https://img.shields.io/github/actions/workflow/status/sergiorico/Bike_System_25_MiUn/test-report.yml?branch=master&label=Test%20Report&logo=github-actions)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/test-report.yml)
[![Manual Tests](https://img.shields.io/badge/manual%20tests-available-blue?logo=github-actions)](https://github.com/sergiorico/Bike_System_25_MiUn/actions/workflows/manual-test.yml)

**Purpose**: Automated testing and quality checks

**Workflows**:

#### 1. CI Pipeline (`ci.yml`)
**Triggers**: Push to main/develop, Pull Requests

**Actions**:
- Checkout code
- Setup JDK 21
- Cache Maven dependencies
- Compile project
- Run tests
- Generate JaCoCo coverage report
- Upload test results as artifacts

**Key Feature**: `continue-on-error: true` allows tests to fail without breaking the build.

---

#### 2. Coverage Check (`coverage-check.yml`)
**Triggers**: Push to main, Pull Requests

**Actions**:
- Run tests with JaCoCo
- Extract line and branch coverage percentages
- Display coverage summary in logs

**Sample Output**:
```
📊 Coverage Summary
Line Coverage: 73.5%
Branch Coverage: 65.2%
```

---

#### 3. Manual Testing (`manual-test.yml`)
**Triggers**: Manual dispatch (workflow_dispatch)

**Options**: Run all tests or specific lab tests (lab1-lab6)

**How to Use**:
1. Go to GitHub → Actions tab
2. Select "Manual Testing Workflow"
3. Click "Run workflow"
4. Choose test level
5. Click "Run workflow" button

---

#### 4. Test Report (`test-report.yml`)
**Triggers**: Push to master/main, Pull Requests

**Actions**:
- Run Maven tests
- Generate detailed test reports with dorny/test-reporter
- Display test summary with test-summary action
- Shows pass/fail/skip counts with visual indicators

**Benefits**:
- ✅ Beautiful test reports in GitHub Actions UI
- 📊 Test trend analysis
- 🔍 Detailed failure information

---

#### 5. Dependency Security Check (`dependency-security.yml`)
**Triggers**: Push to master/main, Pull Requests, Weekly schedule (Mondays 9 AM)

**Actions**:
- Run OWASP Dependency Check
- Scan for known CVEs in dependencies
- Generate security report
- Upload report as artifact

**What It Checks**:
- Known vulnerabilities (CVEs)
- Dependency security issues
- Fails build on CVSS >= 7 (High severity)

---

#### 6. Quality Badges (`quality-badges.yml`)
**Triggers**: Push to master/main

**Actions**:
- Generate JaCoCo coverage report
- Create coverage badge SVG
- Auto-commit badge to `.github/badges/` directory
- Display line and branch coverage percentages

**Result**: Dynamic badges showing current coverage percentages


## 🏗️ 6. Build & Dependency Management

### Apache Maven
**Version**: 3.8+  
**Purpose**: Project build, dependency management, test execution

**Build Lifecycle**:
```bash
mvn clean      # Remove target/ directory
mvn compile    # Compile source code
mvn test       # Run unit tests
mvn package    # Create JAR file
mvn verify     # Run integration tests
mvn install    # Install to local repository
```

**Key Plugins**:
- **Surefire**: Unit test runner
- **Failsafe**: Integration test runner
- **JaCoCo**: Coverage reporting
- **Pitest**: Mutation testing
- **SpotBugs**: Bug detection
- **Checkstyle**: Style checking
- **PMD**: Code quality analysis

---

## 📊 7. Behavioral Analysis

### CodeScene

![CodeScene](https://img.shields.io/badge/CodeScene-Behavioral%20Analysis-purple?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOCAYAAAAfSC3RAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVCiRY/wPBAxAAGKDhAECBgYGBgaQOEgMxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvE/g8EAAD//w==)
![Code Health](https://img.shields.io/badge/code%20health-tracking-green?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOCAYAAAAfSC3RAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVCiRY/wPBAxAAGKDhAECBgYGBgaQOEgMxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvE/g8EAAD//w==)
![Hotspots](https://img.shields.io/badge/hotspots-analysis-orange?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOCAYAAAAfSC3RAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVCiRY/wPBAxAAGKDhAECBgYGBgaQOEgMxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvEBrFBbBAbxAaxQWwQG8QGsUFsEBvE/g8EAAD//w==)

**Purpose**: Behavioral code analysis and technical debt management

**What It Does**:
- Analyzes code evolution over time
- Identifies hotspots (high complexity + high change frequency)
- Measures code complexity trends
- Detects technical debt accumulation
- Prioritizes refactoring efforts

**Key Metrics**:

**1. Code Health Score (0-10)**:
- Function complexity
- Code duplication
- Large methods/classes
- Code churn

**2. Hotspots Analysis**:
- Files with high complexity AND high change frequency
- Priority areas for testing

**3. X-Ray Analysis**:
- Function-level complexity
- Identify specific functions to test/refactor

---

### Setup Options

#### Option 1: CodeScene Cloud (Recommended)
1. Go to [codescene.io](https://codescene.io)
2. Sign up (free for academic use)
3. Connect to GitHub repository
4. Configure analysis settings
5. Run initial analysis

#### Option 2: CodeScene CLI (Local)
```bash
# Run analysis
codescene analyze --repository . --output-dir ./codescene-reports

# View results
start ./codescene-reports/index.html
```



## 📦 Complete Tool Stack

### Installed & Configured
1. **JUnit 5** (5.10.0) - Test framework
2. **Mockito** (5.5.0) - Mocking framework
3. **JaCoCo** (0.8.10) - Coverage analysis
4. **PIT** (1.15.8) - Mutation testing
5. **SpotBugs** (4.8.3) - Static analysis
6. **Checkstyle** (3.3.1) - Code style
7. **PMD** (3.21.2) - Code quality
8. **Maven** (3.8+) - Build tool
9. **GitHub Actions** - CI/CD pipeline
10. **CodeScene** - Behavioral analysis

---

## 🚀 Quick Start

### First Time Setup
```bash
# Verify tools
java -version    # Should be 21
mvn -version     # Should be 3.8+

# Compile project
mvn clean compile

# Run tests
mvn test

# Generate coverage
mvn test jacoco:report
start target/site/jacoco/index.html

# Run mutation testing
mvn clean test org.pitest:pitest-maven:mutationCoverage
start target/pit-reports/index.html

# Run static analysis
mvn clean compile spotbugs:spotbugs spotbugs:gui
mvn checkstyle:checkstyle
mvn pmd:pmd
```

### Daily Workflow
```bash
# 1. Write tests
# 2. Run tests locally
mvn test

# 3. Check coverage
mvn test jacoco:report

# 4. Commit and push
git add .
git commit -m "Add tests"
git push

# 5. GitHub Actions runs automatically
```

---

## 📚 Documentation Links

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [PIT Mutation Testing](https://pitest.org/)
- [SpotBugs Manual](https://spotbugs.readthedocs.io/)
- [Checkstyle Documentation](https://checkstyle.sourceforge.io/)
- [PMD Documentation](https://pmd.github.io/)
- [CodeScene Documentation](https://codescene.io/docs/)
- [Maven Guide](https://maven.apache.org/guides/)
- [GitHub Actions](https://docs.github.com/en/actions)

---

## 🔧 Troubleshooting

**Tests not running**:
```bash
mvn clean test -X  # Debug mode
```

**Coverage report empty**:
```bash
mvn clean test jacoco:report  # Clean first
```

**SpotBugs no files found**:
```bash
mvn clean compile  # Compile first
mvn spotbugs:spotbugs
```

**PMD Java 21 warnings**:
- Expected behavior (PMD 6.55.0 has limited Java 21 support)
- Configured with targetJdk=17 as workaround
- Does not prevent analysis of most code

---

**Happy Testing! 🚀**
