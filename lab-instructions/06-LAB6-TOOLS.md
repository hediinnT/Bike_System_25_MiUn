# Lab 6: AI-Assisted Development & Quality Tools

## 🎯 Overview

Learn to work with AI coding assistants and automated quality tools to improve your testing workflow. This lab is practical and hands-on, focusing on three key areas:

1. **GitHub Copilot** - AI-powered code assistance
2. **Static Analysis Tools** - SpotBugs & PMD
3. **CI/CD Automation** - Github Actions workflows



---

## 📋 Prerequisites

✅ GitHub Copilot installed in VS Code  
✅ BikeShare project cloned and building  
✅ Maven installed and working  
✅ GitHub account with repository access  

---

## Part 1: GitHub Copilot for Testing)

### 🤖 What is GitHub Copilot?

GitHub Copilot is an AI pair programmer that helps you write code faster and better. It has three modes:

- **💬 Chat (Ask)**: Ask questions about code
- **✏️ Edits**: Request code changes
- **🤖 Agent**: Autonomous task completion

---

### Activity 1.1: Chat Mode - Understanding Code (10 minutes)

**Objective**: Use Copilot Chat to understand existing code

**Steps**:

1. **Open a test file** you find confusing eg.:
   ```
   src/test/java/com/bikeshare/lab2/UserBlackBoxTestSolution.java
   ```

2. **Select code** you don't understand (e.g., a test method)

3. **Ask Copilot** to explain:
   - Right-click → "Copilot" → "Explain This"
   - Or type in Chat: `@workspace Explain what this test does`

4. **Try these questions**:
   - "What is this test validating?"
   - "Why is Mockito used here?"
   - "What edge cases does this test cover?"
   - "Are there any missing test scenarios?"


### Activity 1.2: Edit Mode - Refactoring Tests 

**Objective**: Use Copilot to improve test code quality

**Steps**:

1. **Open a test file** with code you wrote e.g from lab 3: 
   ```
   src/test/java/com/bikeshare/lab3/
   ```

2. **Select your test method**

3. **Ask Copilot to refactor**:
   - Right-click → "Copilot" → "Start Inline Chat"
   - Type: `/fix make this test more readable`
   - Or: `Add descriptive variable names and comments`

4. **Try one or more of these refactoring requests**:
   ```
   /fix Add more descriptive assertion messages
   /fix Improve test method naming to be more descriptive
   /fix Add comments explaining the test setup
   ```

5. **Review changes** - Accept or reject each suggestion

---

### Activity 1.3: Agent Mode - Increase Coverage 

**Objective**: Use Copilot Agent to improve test coverage

**Steps**:

1. **Check current coverage**:
   ```bash
   mvn clean test jacoco:report
   ```
   Open: `target/site/jacoco/index.html`

2. **Identify low-coverage class**:
   - Look for classes with < 70% line coverage
   - Example: `BikeService.java` or `RideService.java`

3. **Ask Copilot Agent to help**:
   - Open Copilot Chat
   - Type: `@workspace /new Increase test coverage for BikeService class to 80%`
   - Or: `Generate missing tests for the rentBike method in BikeService`

4. **Review generated tests**:
   - Check if tests are correct
   - Verify they compile and run
   - See if coverage actually increased

5. **Re-run coverage**:
   ```bash
   mvn clean test jacoco:report
   ```
6. **Try a different model**: What happens if you switch from one model to another?

### 📝 Reflection Questions (Part 1) 

The following questions will help you reflect on your experience with GitHub Copilot. Discuss with your peers or write down your thoughts. It is not expected that you report these answers, but they will help you internalize what you explored.

1. **Understanding (Chat Mode)**:
   - When is Copilot Chat most helpful?
   - Did it ever give wrong or confusing explanations?

2. **Refactoring (Edit Mode)**:
   - What improvements did Copilot suggest that you wouldn't have thought of?
   - Did you reject any suggestions? Why?

3. **Test Generation (Agent Mode)**:
   - How good were the auto-generated tests?
   - What did you need to fix or improve?
   - Would you trust AI-generated tests in production?

4. **Overall AI-Assisted Development**:
   - Benefits: What did Copilot do well?
   - Limitations: What can't it do?
   - Future: How might this change software testing?

---

## Part 2: Static Analysis Tools 

### 🔍 What are Static Analysis Tools?

Tools that analyze code **without running it** to find:
- Bugs and potential errors
- Code quality issues
- Style violations
- Security vulnerabilities

---

### Activity 2.1: SpotBugs - Find Bugs 

**Objective**: Run SpotBugs and understand its findings

**What is SpotBugs?**
- Detects common programming mistakes
- Analyzes compiled bytecode (.class files)
- Categories: Correctness, Bad Practice, Performance, Security

**Steps**:

1. **Compile first** (SpotBugs needs .class files):
   ```bash
   mvn clean compile
   ```

2. **Run SpotBugs**:
   ```bash
   mvn spotbugs:spotbugs
   ```

3. **View GUI Report** (easiest):
   ```bash
   mvn spotbugs:gui
   ```

4. **Explore the GUI**:
   - See bug categories
   - Click on each bug
   - Read bug descriptions
   - View affected code

5. **Analyze findings**:
   - How many bugs found? ____
   - Severity: High ___, Medium ___, Low ___
   - Most common bug type: __________

**Common Bug Examples**:
- Null pointer dereference
- Resource leaks
- Incorrect equals() implementation
- Unused variables
- Security vulnerabilities

---

### Activity 2.2: PMD - Code Quality (15 minutes)

**Objective**: Run PMD and improve code quality

**What is PMD?**
- Detects code smells and bad practices
- Analyzes source code (.java files)
- Categories: Best Practices, Code Style, Design, Performance

**Steps**:

1. **Run PMD**:
   ```bash
   mvn pmd:pmd
   ```

2. **View HTML Report**:
   ```bash
   # Open in browser:
   target/site/pmd.html
   ```

3. **Analyze findings**:
   - Total violations: ____
   - Priority 1 (Critical): ____
   - Priority 2 (High): ____
   - Priority 3 (Medium): ____

4. **Common PMD Issues**:
   - Unused imports
   - Empty catch blocks
   - Variable naming conventions
   - Overly complex methods
   - Duplicate code

**⚠️ Note**: PMD may show warnings about Java 21. This is expected - PMD 6.55.0 has limited Java 21 support but still analyzes most code.

**Deliverable**:
- Screenshot of PMD report
- List 3 code quality issues found
- Pick 1 issue and explain: How would you fix it?

---

### 📊 Tool Comparison

| Tool | Analyzes | Finds | When to Use |
|------|----------|-------|-------------|
| **SpotBugs** | Bytecode (.class) | Bugs, errors | After compilation |
| **PMD** | Source code (.java) | Code smells, style | During development |

**Together**: Comprehensive code quality analysis! 💪

---

## Part 3: Explore GitHub Actions

### 🤖 What are GitHub Actions?

GitHub Actions are automated workflows that run on every push:
- **Continuous Integration (CI)**: Automatically build and test code
- **Continuous Deployment (CD)**: Automatically deploy applications
- **Quality Checks**: Run automated code quality analysis

This project already has 3 workflows configured! Let's explore them.

---

### Activity 3.1: Explore Existing Workflows 

**Objective**: Understand the automated CI/CD pipeline already configured in this project

**Steps**:

1. **View workflow files locally**:
   ```bash
   # List workflow files
   ls .github/workflows/
   
   # You should see:
   # - ci.yml                  (Main CI pipeline)
   # - coverage-check.yml      (Coverage analysis)
   # - manual-test.yml         (Manual testing)
   ```

2. **Open and read each workflow file**:
   - Open `.github/workflows/ci.yml` in VS Code
   - Read through the steps - what does each do?
   - Notice: JDK 21 setup, Maven cache, test execution

3. **Go to GitHub Actions tab**:
   ```
   https://github.com/YOUR-USERNAME/Bike-System-25/actions
   ```
   - See all workflow runs
   - Click on a recent run
   - Explore the logs and results

4. **Trigger a workflow manually** (if available):
   - Go to Actions tab
   - Select "Manual Testing Workflow"
   - Click "Run workflow"
   - Select "all-tests"
   - Click "Run workflow" button
   - Watch it execute!

**Discussion questions**:
- How long does the CI pipeline take to run? ______ seconds It is reasonable for a project of this size?
- What steps run in the CI workflow? 
- What happens if tests fail? 
- How is this useful for team collaboration?
- How to use this in your own projects?

---

## 📦 Final Deliverables

Submit the following to complete Lab 5:

Show screenshots and reflections in a max 2-page document.

