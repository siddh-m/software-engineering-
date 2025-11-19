# How to Run and Test the Student Grade Management System

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Initial Setup](#initial-setup)
3. [Building the Project](#building-the-project)
4. [Running Tests](#running-tests)
5. [Running the Application](#running-the-application)
6. [Testing the API](#testing-the-api)
7. [Validation (COMP0010 Requirements)](#validation-comp0010-requirements)
8. [Viewing Reports](#viewing-reports)
9. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software
1. **JDK 17 or later**
   ```bash
   # Check Java version
   java -version

   # Should show: java version "17.x.x" or higher
   ```

2. **Maven 3.9.9 or later**
   ```bash
   # Check Maven version
   mvn -version

   # Should show: Apache Maven 3.9.9 or higher
   ```

3. **Git** (for version control)
   ```bash
   git --version
   ```

### Installing Prerequisites

#### Option 1: Using SDKMAN (Recommended)
```bash
# Install SDKMAN
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install JDK 17
sdk install java 17.0.9-tem

# Install Maven 3.9.9
sdk install maven 3.9.9
```

#### Option 2: Manual Installation
- **JDK**: Download from [OpenJDK](https://openjdk.org/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Maven**: Download from [Maven Apache](https://maven.apache.org/download.cgi)

---

## Initial Setup

### 1. Clone the Repository (if not already done)
```bash
git clone <your-repository-url>
cd software-engineering-
```

### 2. Verify Project Structure
```bash
# You should see:
ls -la

# Output should include:
# - pom.xml
# - src/
# - .gitignore
# - PROJECT_SETUP.md
# - TEST_SUMMARY.md
```

### 3. Check Source Structure
```bash
# View source tree
find src -name "*.java" | head -20
```

---

## Building the Project

### Step 1: Clean Previous Builds
```bash
mvn clean
```

**What this does:** Removes the `target/` directory containing old compiled files.

### Step 2: Compile the Code
```bash
mvn compile
```

**What this does:**
- Downloads all Maven dependencies (first time only)
- Compiles Java source files in `src/main/java/`
- Creates `.class` files in `target/classes/`

**Expected output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 10.5 s
```

### Step 3: Full Build with Tests
```bash
mvn clean install
```

**What this does:**
- Cleans the project
- Compiles source code
- Runs all unit and integration tests
- Packages the application as a JAR file
- Installs it to local Maven repository

---

## Running Tests

### 1. Run All Tests
```bash
mvn test
```

**What this does:**
- Compiles test classes
- Runs all JUnit tests (52+ tests)
- Shows test results in console

**Expected output:**
```
[INFO] Tests run: 52, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### 2. Run Tests with Coverage Report
```bash
mvn clean test jacoco:report
```

**What this does:**
- Runs all tests
- Generates JaCoCo coverage report
- Creates HTML report in `target/site/jacoco/`

### 3. Run Specific Test Class
```bash
# Run only StudentTest
mvn test -Dtest=StudentTest

# Run only GradeControllerTest
mvn test -Dtest=GradeControllerTest
```

### 4. Run Tests in a Specific Package
```bash
# Run all model tests
mvn test -Dtest="uk.ac.ucl.comp0010.model.*"

# Run all repository tests
mvn test -Dtest="uk.ac.ucl.comp0010.repository.*"
```

### 5. Skip Tests (when needed)
```bash
mvn clean install -DskipTests
```

**Note:** Only skip tests when debugging build issues!

---

## Running the Application

### Method 1: Using Maven Spring Boot Plugin (Recommended)
```bash
mvn spring-boot:run
```

**What this does:**
- Compiles the code if needed
- Starts the embedded Tomcat server
- Runs the application on port 2800

**Expected output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

2025-11-18 22:00:00.000  INFO 12345 --- [main] u.a.u.comp0010.Application               : Started Application in 3.5 seconds
```

**Application is now running at:** `http://localhost:2800`

### Method 2: Running the JAR File
```bash
# First, build the JAR
mvn clean package

# Then run it
java -jar target/student-grade-management-0.0.1-SNAPSHOT.jar
```

### Method 3: In Background (Linux/Mac)
```bash
# Start in background
mvn spring-boot:run &

# Check if running
ps aux | grep spring-boot

# Stop the application
kill <process-id>
```

### Stopping the Application
- **In terminal:** Press `Ctrl + C`
- **Background process:** `kill <process-id>`

---

## Testing the API

Once the application is running, you can test it using various methods:

### 1. Using Swagger UI (Easiest Method)

**Open in browser:**
```
http://localhost:2800/swagger-ui/index.html
```

**What you'll see:**
- Interactive API documentation
- List of all endpoints
- "Try it out" buttons to test endpoints
- Request/response examples

### 2. Using H2 Database Console

**Open in browser:**
```
http://localhost:2800/h2-console
```

**Login credentials:**
- JDBC URL: `jdbc:h2:mem:test`
- Username: `sa`
- Password: (leave empty)

**What you can do:**
- View database tables
- Execute SQL queries
- See sample data

### 3. Using curl (Command Line)

#### View All Students
```bash
curl http://localhost:2800/students
```

#### View All Modules
```bash
curl http://localhost:2800/modules
```

#### View All Grades
```bash
curl http://localhost:2800/grades
```

#### Add a New Grade (POST Request)
```bash
curl -X POST http://localhost:2800/grades/addGrade \
  -H "Content-Type: application/json" \
  -d '{
    "student_id": "1",
    "module_code": "COMP0010",
    "score": "95"
  }'
```

**Expected response:**
```json
{
  "id": 6,
  "score": 95,
  "student": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "username": "jdoe",
    "email": "john.doe@ucl.ac.uk"
  },
  "module": {
    "code": "COMP0010",
    "name": "Software Engineering",
    "mnc": true
  }
}
```

#### View a Specific Student
```bash
curl http://localhost:2800/students/1
```

#### View a Specific Module
```bash
curl http://localhost:2800/modules/COMP0010
```

### 4. Using Postman or Insomnia (GUI Tools)

**Setup:**
1. Download [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/)
2. Create a new request

**Test POST /grades/addGrade:**
- Method: `POST`
- URL: `http://localhost:2800/grades/addGrade`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
  ```json
  {
    "student_id": "1",
    "module_code": "COMP0010",
    "score": "88"
  }
  ```

### 5. Using Browser (GET Requests Only)

**View all students:**
```
http://localhost:2800/students
```

**View all modules:**
```
http://localhost:2800/modules
```

**View all grades:**
```
http://localhost:2800/grades
```

---

## Validation (COMP0010 Requirements)

### The Complete Validation Command

This is the command that will be used to validate your submission:

```bash
mvn compile test checkstyle:check spotbugs:check verify site
```

**What this does:**
1. `compile` - Compiles the source code
2. `test` - Runs all tests
3. `checkstyle:check` - Validates Google Java Style
4. `spotbugs:check` - Checks for common bugs (High severity)
5. `verify` - Verifies 90% test coverage with JaCoCo
6. `site` - Generates project documentation site

**Expected output (if all pass):**
```
[INFO] --- maven-compiler-plugin:3.11.0:compile (default-compile) ---
[INFO] BUILD SUCCESS
[INFO]
[INFO] --- maven-surefire-plugin:3.0.0:test (default-test) ---
[INFO] Tests run: 52, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO]
[INFO] --- maven-checkstyle-plugin:3.4.0:check (default) ---
[INFO] BUILD SUCCESS
[INFO]
[INFO] --- spotbugs-maven-plugin:4.8.6.0:check (default) ---
[INFO] BUILD SUCCESS
[INFO]
[INFO] --- jacoco-maven-plugin:0.8.12:check (jacoco-check) ---
[INFO] All coverage checks have been met.
[INFO] BUILD SUCCESS
[INFO]
[INFO] --- maven-site-plugin:3.20.0:site (default-site) ---
[INFO] BUILD SUCCESS
```

### Individual Validation Steps

#### 1. Check Code Style (Checkstyle)
```bash
mvn checkstyle:check
```

**What it validates:**
- 2-space indentation (no tabs)
- Naming conventions (camelCase, PascalCase, SCREAMING_SNAKE_CASE)
- Google Java Style compliance
- Javadoc presence

**If errors occur:**
```bash
# View detailed report
cat target/checkstyle-result.xml
```

#### 2. Check for Bugs (Spotbugs)
```bash
mvn spotbugs:check
```

**What it validates:**
- Common coding mistakes
- Potential bugs
- Performance issues
- Security vulnerabilities (High severity only)

**If errors occur:**
```bash
# View GUI report
mvn spotbugs:gui
```

#### 3. Check Test Coverage (JaCoCo)
```bash
mvn verify
```

**What it validates:**
- Minimum 90% line coverage
- Excludes Application.java from coverage

**If coverage is below 90%:**
```bash
# Generate coverage report
mvn test jacoco:report

# Open the report
# On Mac/Linux:
open target/site/jacoco/index.html

# On Windows:
start target/site/jacoco/index.html

# On WSL:
explorer.exe target/site/jacoco/index.html
```

---

## Viewing Reports

### 1. JaCoCo Coverage Report
```bash
# Generate report
mvn test jacoco:report

# Location
open target/site/jacoco/index.html
```

**What you'll see:**
- Overall coverage percentage
- Coverage by package
- Coverage by class
- Line-by-line coverage (green = covered, red = not covered)

### 2. Checkstyle Report
```bash
# Generate report
mvn checkstyle:checkstyle

# Location
open target/site/checkstyle.html
```

**What you'll see:**
- List of style violations
- Severity levels
- File and line numbers

### 3. Spotbugs Report
```bash
# Generate report
mvn spotbugs:spotbugs

# Location
open target/site/spotbugs.html
```

**What you'll see:**
- Potential bugs found
- Bug categories
- Confidence levels

### 4. Project Site (All Reports)
```bash
# Generate full site
mvn site

# Location
open target/site/index.html
```

**What you'll see:**
- Project information
- All reports in one place
- Javadoc documentation
- Test results
- Coverage reports
- Static analysis reports

---

## Troubleshooting

### Problem 1: "mvn: command not found"

**Solution:**
```bash
# Check if Maven is installed
which mvn

# If not found, install Maven
sdk install maven 3.9.9

# Or add Maven to PATH
export PATH=/path/to/maven/bin:$PATH
```

### Problem 2: "JAVA_HOME not set"

**Solution:**
```bash
# Find Java installation
which java

# Set JAVA_HOME
export JAVA_HOME=/path/to/jdk-17

# Add to ~/.bashrc or ~/.zshrc to make permanent
echo 'export JAVA_HOME=/path/to/jdk-17' >> ~/.bashrc
```

### Problem 3: Port 2800 Already in Use

**Solution:**
```bash
# Find process using port 2800
lsof -i :2800

# Kill the process
kill -9 <PID>

# Or change port in application.properties
# Edit: src/main/resources/application.properties
# Change: server.port=2800 to server.port=8080
```

### Problem 4: Tests Failing

**Solution:**
```bash
# Run tests with detailed output
mvn test -X

# Run specific failing test
mvn test -Dtest=FailingTestClass

# Check test logs
cat target/surefire-reports/*.txt
```

### Problem 5: Checkstyle Violations

**Common violations and fixes:**

**Indentation errors:**
```bash
# Use spaces, not tabs
# Configure your IDE to use 2 spaces for indentation
```

**Missing Javadoc:**
```java
// Add Javadoc to all public classes and methods
/**
 * Description of the class.
 */
public class MyClass {
  /**
   * Description of the method.
   *
   * @param param description
   * @return description
   */
  public int myMethod(int param) {
    return param * 2;
  }
}
```

### Problem 6: Coverage Below 90%

**Solution:**
```bash
# View coverage report
open target/site/jacoco/index.html

# Identify uncovered lines (shown in red)
# Add tests for those lines

# Re-run tests
mvn clean test jacoco:report
```

### Problem 7: Dependencies Not Downloading

**Solution:**
```bash
# Clear local Maven repository
rm -rf ~/.m2/repository

# Force re-download
mvn clean install -U

# Or manually download dependencies
mvn dependency:resolve
```

### Problem 8: Application Won't Start

**Check logs for errors:**
```bash
# Run with debug logging
mvn spring-boot:run -Ddebug

# Check application logs
tail -f logs/spring-boot-logger.log
```

---

## Quick Reference Commands

### Development Workflow
```bash
# 1. Pull latest changes
git pull origin main

# 2. Build project
mvn clean install

# 3. Run tests
mvn test

# 4. Run application
mvn spring-boot:run

# 5. Validate (before committing)
mvn compile test checkstyle:check spotbugs:check verify site

# 6. Commit and push
git add .
git commit -m "Your message"
git push
```

### Common Maven Commands
```bash
mvn clean                    # Clean build artifacts
mvn compile                  # Compile source code
mvn test                     # Run tests
mvn package                  # Create JAR file
mvn install                  # Install to local repository
mvn spring-boot:run          # Run Spring Boot app
mvn checkstyle:check         # Check code style
mvn spotbugs:check           # Check for bugs
mvn verify                   # Run all validations
mvn site                     # Generate documentation
mvn clean install -DskipTests  # Build without tests
```

### Testing Commands
```bash
mvn test                              # Run all tests
mvn test -Dtest=StudentTest           # Run one test class
mvn test -Dtest=StudentTest#testGetId # Run one test method
mvn clean test jacoco:report          # Run tests with coverage
mvn verify                            # Verify 90% coverage
```

### Debugging Commands
```bash
mvn test -X                  # Run tests with debug output
mvn spring-boot:run -Ddebug  # Run app with debug logging
mvn dependency:tree          # Show dependency tree
mvn help:effective-pom       # Show effective POM
```

---

## Summary

**To build and test your project:**
```bash
# Complete validation (as required for submission)
mvn compile test checkstyle:check spotbugs:check verify site
```

**To run the application:**
```bash
mvn spring-boot:run
```

**To test the API:**
- Swagger UI: `http://localhost:2800/swagger-ui/index.html`
- H2 Console: `http://localhost:2800/h2-console`
- REST endpoints: `http://localhost:2800/students`, `/modules`, `/grades`

**To view reports:**
- Coverage: `target/site/jacoco/index.html`
- All reports: `target/site/index.html`

Good luck with your COMP0010 project! 🚀
