# COMP0010 Coding Standards Summary

## Overview
This document describes the coding standards for COMP0010 Software Engineering. All group work must follow these standards.

---

## 1. Code Rules & Requirements

### Build System & Project Management
- **Build Tool**: Must use **Maven** to manage projects
- All dependencies, static analyzers, and configurations must be managed through Maven

### Definition of "Working Code"
Working code in this module must meet ALL of the following criteria:

1. A Maven project with correct configurations including:
   - Dependencies
   - Build plugins
   - Report plugins

2. Ability to execute the following command without errors:
   ```bash
   $ mvn compile test checkstyle:check spotbugs:check verify site
   ```

3. Code style must follow the standards described in this document

### Core Requirements
- **Root Package Name**: `uk.ac.ucl.comp0010` (mandatory for all projects)
- **JDK Version**: JDK 17 or later (must be compatible with JDK 17)
- **Testing Framework**: JUnit 5 only (JUnit 4 or lower versions NOT allowed)
- **Test Coverage**: Minimum **90%** or higher (enforced by JaCoCo)

---

## 2. Static Analyzer Requirements

### Checkstyle
- **Purpose**: Warns about violations of coding styles documented in this document
- **Configuration**: Uses Google Java Style configuration
- **Severity Level**: Warning

### Spotbugs
- **Purpose**: Warns about common developer mistakes
- **Severity Level**: High (not super sensitive)
- **Plugin Version**: 4.8.6.0

### JaCoCo (Code Coverage)
- **Purpose**: Includes test coverage information in project report
- **Version**: 0.8.12
- **Requirement**: Minimum 90% line coverage required
- **Exclusions**: `**/*Application*` classes are excluded from coverage

---

## 3. Coding Style Guidelines

### Indentation
- **NO tab characters** allowed
- **Unit of indentation**: 2 spaces

### Naming Conventions

| Element | Convention | Example |
|---------|-----------|---------|
| Methods & Variables | Lower camel case | `String helloWorld` |
| Classes | Upper camel case | `class HelloWorld` |
| Packages | Lowercase letters and digits only | `uk.ac.ucl.comp0010` |
| Constants | Screaming snake case | `private static final String HELLO_WORLD` |

### Style Guidelines
- Must have **no Checkstyle violations** with Google Java Style configuration
- Any code style issues not explicitly listed here must follow **Google Java Style** standards
- All code must pass the following command without errors:
  ```bash
  mvn checkstyle:check
  ```

---

## 4. Development Environment Recommendations

### Git Configuration
- **Repository Setup**:
  - Properly configure `.gitignore` to exclude:
    - Compiled binaries (`target/`)
    - IDE settings (`.settings/`)
    - Node modules (`node_modules/`)

- **Commit Guidelines**:
  - Each commit should include **less than 50 lines of changes**
  - If changes exceed 50 lines, split into multiple commits
  - Push commits **every week** during term time

### Terminal/Shell
- **Requirements**: Project must compile and test successfully in terminal
- **Windows Users**:
  - Command line is acceptable
  - **WSL (Windows Subsystem for Linux)** is highly recommended
- **Shell Enhancement**: Using `starship` to display diverse information is recommended

### Java Development Kit (JDK)
- **Recommended**: OpenJDK
- **Installation Methods**:
  - Direct download from OpenJDK website
  - Via SDKMAN (recommended):
    ```bash
    sdk install java 22.0.1-open
    ```
- **Multiple vendors available**: Feel free to select preferred vendor

### Maven
- **Recommended Version**: 3.9.9
- **Installation Methods**:
  - Download from Maven website
  - Via SDKMAN (recommended):
    ```bash
    sdk install maven 3.9.9
    ```

### Node.js
- **Installation Methods**:
  - Direct download from Node.js website
  - Via Node Version Manager (nvm) - recommended:
    ```bash
    nvm install lts/iron
    ```

### IDE Configuration (Eclipse)
1. Download `eclipse-java-google-style.xml`
2. Import configuration:
   - Path: `Preferences > Java > Code Style > Formatter`
3. Enable spaces for tabs:
   - Path: `Preferences > General > Editors > Text Editors`
   - Check: `Insert spaces for tabs`
4. Configure save actions:
   - Path: `Preferences > Java > Editor > Save Actions`
   - Check: `Perform the selected actions on save`
   - Tick: `Format source code` and `Format edited lines`

**Note**: For IDEs other than Eclipse, configure manually to align with the above rules.

---

## 5. Maven Configuration Details

### Plugins Configuration (pom.xml)

#### Checkstyle Plugin
- **Version**: 3.4.0
- **Configuration Location**: `google_checks.xml`
- **Console Output**: Enabled
- **Fail on Error**: true
- **Violation Severity**: warning

#### Spotbugs Plugin
- **Version**: 4.8.6.0
- **Threshold**: High

#### JaCoCo Plugin
- **Version**: 0.8.12
- **Exclusions**: `**/*Application*`
- **Coverage Requirement**: 90% line coverage (COVEREDRATIO)
- **Execution Goals**:
  - `prepare-agent` (default)
  - `report` (during test phase)
  - `check` (enforces 90% coverage)

#### Maven Site Plugin
- **Version**: 3.20.0

#### Maven Project Info Reports Plugin
- **Version**: 3.6.2

#### Maven Javadoc Plugin
- **Version**: 3.10.0
- **Doclint Configuration**: `all,-missing`

### Build Command Validation
```bash
$ mvn compile test checkstyle:check spotbugs:check verify site
```

This command ensures:
1. Code compiles successfully
2. All tests pass
3. No Checkstyle violations
4. No Spotbugs warnings (High severity)
5. Tests pass verification
6. Project site/documentation builds

---

## Summary of Key Takeaways

✓ Use Maven 3.9.9 with JDK 17+
✓ Enforce 90% test coverage with JaCoCo
✓ Run all static analyzers: Checkstyle, Spotbugs, JaCoCo
✓ Follow Google Java Style conventions
✓ Use 2-space indentation (no tabs)
✓ Proper naming: camelCase for methods/variables, PascalCase for classes, SCREAMING_SNAKE_CASE for constants
✓ Configure `.gitignore` properly
✓ Use JUnit 5 for testing
✓ Keep commits small (< 50 lines)
✓ Push weekly during term time
