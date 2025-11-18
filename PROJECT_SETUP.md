# Student Grade Management System - Setup Guide

## Project Structure

```
software-engineering-/
├── src/
│   ├── main/
│   │   ├── java/uk/ac/ucl/comp0010/
│   │   │   ├── model/
│   │   │   │   ├── Student.java
│   │   │   │   ├── Module.java
│   │   │   │   ├── Grade.java
│   │   │   │   └── Registration.java
│   │   │   ├── repository/
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── ModuleRepository.java
│   │   │   │   ├── GradeRepository.java
│   │   │   │   └── RegistrationRepository.java
│   │   │   ├── controller/
│   │   │   │   └── GradeController.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── RestConfiguration.java
│   │   │   ├── exception/
│   │   │   │   ├── NoGradeAvailableException.java
│   │   │   │   └── NoRegistrationException.java
│   │   │   └── Application.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test/java/uk/ac/ucl/comp0010/
│       └── (test files to be created)
├── pom.xml
├── .gitignore
├── CODING_STANDARDS_SUMMARY.md
└── CRITERIA_SUMMARY.md
```

## Prerequisites

- JDK 17 or later
- Maven 3.9.9 or later
- Git

## Building and Running

### 1. Validate the Project

Run the full validation command as required by COMP0010 Coding Standards:

```bash
mvn compile test checkstyle:check spotbugs:check verify site
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:2800`

### 3. Access API Documentation

Once the application is running, you can access:
- Swagger UI: `http://localhost:2800/swagger-ui/index.html`
- H2 Console: `http://localhost:2800/h2-console`
  - JDBC URL: `jdbc:h2:mem:test`
  - Username: `sa`
  - Password: (leave empty)

## Available REST Endpoints

### Auto-generated endpoints (Spring Data REST):

- **Students**: `http://localhost:2800/students`
- **Modules**: `http://localhost:2800/modules`
- **Grades**: `http://localhost:2800/grades`
- **Registrations**: `http://localhost:2800/registrations`

### Custom endpoint:

- **Add Grade**: `POST http://localhost:2800/grades/addGrade`
  ```json
  {
    "student_id": "1",
    "module_code": "COMP0010",
    "score": "85"
  }
  ```

## Testing with Sample Data

The application comes with pre-populated sample data:

**Students:**
- ID 1: John Doe (jdoe@ucl.ac.uk)
- ID 2: Jane Smith (jsmith@ucl.ac.uk)
- ID 3: Alice Johnson (ajohnson@ucl.ac.uk)

**Modules:**
- COMP0010: Software Engineering (MNC)
- COMP0011: Mathematics and Statistics
- COMP0012: Compilers (MNC)

## Next Steps for Development

1. **Write Tests**: Create unit tests in `src/test/java/uk/ac/ucl/comp0010/`
   - Aim for 90%+ test coverage (required)
   - Use JUnit 5

2. **Implement Additional Features**:
   - Calculate average grade per student
   - Calculate average grade per module
   - Add academic year tracking to grades
   - Validation for registration before adding grades

3. **GitHub Workflow**:
   - Create issues for each feature/task
   - Use pull requests (no direct commits to main)
   - Review each other's code
   - Keep commits under 50 lines

4. **Code Quality**:
   - Ensure no Checkstyle violations
   - Fix any Spotbugs warnings
   - Maintain 90%+ test coverage
   - Add comprehensive Javadoc comments

## Common Maven Commands

```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Check code style
mvn checkstyle:check

# Check for bugs
mvn spotbugs:check

# Verify and run all checks
mvn verify

# Generate project site with reports
mvn site

# Clean build artifacts
mvn clean

# Full clean build
mvn clean install
```

## Troubleshooting

### Checkstyle Errors
- Ensure you're using 2-space indentation (no tabs)
- Follow Google Java Style naming conventions
- Add proper Javadoc comments to all public classes and methods

### Test Coverage Below 90%
- Check JaCoCo report in `target/site/jacoco/index.html`
- Add tests for uncovered methods and branches
- Note: Application.java is excluded from coverage requirements

### Port Already in Use
- Change the port in `application.properties`:
  ```properties
  server.port=8080
  ```

## Resources

- [COMP0010 Coding Standards](CODING_STANDARDS_SUMMARY.md)
- [Project Criteria](CRITERIA_SUMMARY.md)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
