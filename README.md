# BikeShare Testing Project 🚲

[![Made with ❤️ from Mid Sweden University](https://img.shields.io/badge/Made%20with-❤️%20from%20Mid%20Sweden%20University-red.svg)]()

## 🧪 **Test Repository**

A comprehensive Java project designed for teaching Software Testing concepts using a **Shared Bike System** domain. This project provides progressive testing challenges across different labs, from basic terminology to sustainability and ethics in testing.

## 🎯 Project Overview

This project simulates a real-world bike sharing system where users can:

- Register and manage accounts
- Rent bikes from stations
- Complete rides with automatic pricing
- Manage payments and memberships
- Handle bike maintenance and operations

The codebase is specifically designed to demonstrate various testing concepts, patterns, and challenges that students will encounter in professional software development.

## 🎓 For Absolute Beginners

**Never written a test before?** Start here:

1. **📖 [Beginner's Guide](docs/BEGINNERS_GUIDE.md)** - Complete introduction to testing
2. **🧪 [Your First Test Tutorial](FIRST_TEST_TUTORIAL.md)** - Step-by-step first test in 10 minutes
3. **📚 [Testing Glossary](TESTING_GLOSSARY.md)** - Simple explanations of testing terms
4. **👶 [BeginnerFirstTest.java](src/test/java/com/bikeshare/lab1/BeginnerFirstTest.java)** - Super simple tests to start with

**Quick Start for Beginners:**

```bash
# 1. Make sure you have Java 21 and Maven installed
java -version    # Should show Java 21
mvn -version     # Should show Maven 3.8+

# 2. Clone and setup project
git clone <repository-url>
cd bikeshare-testing-project
mvn clean compile

# 3. Run your first test
mvn test -Dtest=BeginnerFirstTest

# 4. Open BeginnerFirstTest.java and follow the tutorials!
```

## 📚 Getting Started with Labs

This project is organized into progressive labs that build upon each other. **For detailed lab instructions, metrics, and coverage targets**, see the [lab-instructions/](lab-instructions/) directory.

Start with [Lab Overview](lab-instructions/00-GENERAL-INSTRUCTIONS.md) for complete structure and assessment criteria.

## 🛠️ Technical Setup

### Prerequisites

- **Java 21+**: Runtime environment (LTS version)
- **Maven 3.8+**: Build tool and dependency management
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code with Java extensions
- **Git**: Version control

### Testing & Quality Tools

This project uses a comprehensive suite of professional testing tools:

- **Test Frameworks**: JUnit 5, Mockito, AssertJ
- **Coverage Analysis**: JaCoCo (line/branch coverage), PIT (mutation testing)
- **Static Analysis**: SpotBugs, Checkstyle, PMD
- **Behavioral Analysis**: CodeScene
- **CI/CD**: GitHub Actions

📚 **See [TOOLS.md](TOOLS.md) for complete documentation** of all tools, commands, and integration guides!

### Quick Setup

```bash
# 1. Verify Java 21 is installed
java -version

# 2. Verify Maven 3.8+ is installed  
mvn -version

# 3. Clean and compile
mvn clean compile

# 4. Run tests
mvn test

# 5. Start web server (optional)
mvn exec:java -Dexec.mainClass="com.bikeshare.web.SimpleBikeShareWebServer"
```

### Project Structure

```
├── docs/                    # Documentation files
├── lab-instructions/        # Lab exercises and instructions  
├── src/
│   ├── main/java/com/bikeshare/
│   │   ├── model/          # Domain entities (Bike, User, Station, Ride)
│   │   ├── service/        # Business logic services
│   │   ├── repository/     # Data access interfaces
│   │   ├── web/            # Web interface components
│   │   └── demo/           # Demo and example classes
│   └── test/java/com/bikeshare/
│       ├── lab1/           # Lab 1: Fundamentals tests
│       ├── lab2/           # Lab 2: Black box tests
│       ├── lab3/           # Lab 3: Structural tests
│       ├── lab4/           # Lab 4: Regression tests
│       ├── lab5/           # Lab 5: Tools and automation tests
│       ├── lab6/           # Lab 6: Sustainability tests
│       ├── model/          # Model unit tests
│       └── service/        # Service unit tests
├── target/                 # Compiled classes and reports
└── pom.xml                 # Maven configuration
```

### Dependencies

- **JUnit 5**: Core testing framework
- **Mockito**: Mocking framework for unit tests
- **JaCoCo**: Code coverage analysis

## 🚀 Getting Started

### 1. Clone and Setup

```bash
git clone <repository-url>
cd bikeshare-testing-project
mvn clean compile
```

### 2. Run Basic Tests

```bash
# Run all tests
mvn test

# Generate coverage report
mvn test jacoco:report
```

### 3. View Coverage Reports

Open `target/site/jacoco/index.html` in your browser to see coverage results.

## 🎓 Learning Objectives

By completing this project, students will:

1. **Master Testing Fundamentals**: Understand different types of testing and when to apply them
2. **Apply Black Box Techniques**: Use systematic approaches to test design without internal knowledge
3. **Achieve High Coverage**: Understand and achieve meaningful code coverage with white-box testing
4. **Implement Regression Testing**: Build robust test suites that prevent bugs and maintain quality
5. **Use Modern Testing Tools**: Work with industry-standard testing and CI/CD tools
6. **Consider Sustainability**: Understand environmental and ethical aspects of testing practices

## 🔧 Advanced Challenges

### Custom Challenges

Create your own testing scenarios:

1. **Complex Integration Tests**: Test complete user journeys across multiple services
2. **Edge Case Discovery**: Find and test unusual edge cases in the business logic
3. **Performance Benchmarking**: Establish performance baselines and regression tests
4. **Error Simulation**: Test system behavior under various failure conditions
5. **Data-Driven Testing**: Create comprehensive test data sets for thorough validation

### Extension Ideas

- Add REST API layer and test it
- Implement real database integration tests
- Create mobile app simulation tests
- Add payment gateway integration tests
- Implement real-time notification testing

## 📖 Resources

### Documentation

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)

## 🤝 Contributing

Students and instructors are encouraged to:

- Add new test cases and scenarios
- Improve existing code and tests
- Create additional documentation
- Share testing strategies and patterns

## 📄 License

This project is designed for educational purposes. Feel free to use and modify for teaching and learning.

---

**Happy Testing! 🧪**

Remember: Good tests are not just about finding bugs—they're about building confidence in your code and enabling fearless refactoring and feature development.
