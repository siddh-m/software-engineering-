# COMP0010 Coursework - Project Criteria and Assessment

## Project Overview

### Project Title
**Student Grade Management Program (50% of grade)**

### Scenario
- A senior developer who led projects for your team recently left the company (COMP0010 Software)
- The senior developer was the only one working on a customer project for **Awesome University** (fictional)
- Customer wants: **Web-based student grade management program** built with Spring Boot (backend) and React (frontend)
- Customer did NOT specify required features but will evaluate the first version in **December 2025**
- Senior developer left behind:
  - Class diagram with basic design
  - Working frontend implementations (TypeScript/JavaScript options)
  - Spring Boot project configurations
- **Your task**: Develop a minimum viable product (MVP) and demonstrate it to the customer

---

## 1. Project Requirements

### Mandatory Requirements

#### Code Standards
- **MUST** follow COMP0010 Coding Standards
- Code will be validated using:
  ```bash
  $ mvn compile test checkstyle:check spotbugs:check verify site
  ```

#### Repository Management
- Must create a team in **GitHub Classroom**
- All work must be done within the GitHub repository
- Using your own repository = **NO grade** will be awarded

#### Development Methodology
- **TDD (Test-Driven Development)** process required
- All main code changes must come with corresponding tests

#### Issue & Task Management
- All tasks must be specified in GitHub issues
- Issues must be updated continuously (comments, status changes)
- Example tasks:
  - Compute average grade for each module
  - Compute average grade for each student
  - Record academic year in the grade

#### Code Review Process
- All commits must be reviewed via **pull requests**
- **NO direct commits to main branch** allowed
- Pull request reviews facilitate team collaboration

#### Feature Implementation
- Team should brainstorm and implement customer-desired features
- Examples (not limited to):
  - Average grade calculation per module
  - Average grade calculation per student
  - Academic year tracking in grades

---

## 2. Basic Design Specification

### Package Structure
- Model classes located in: `uk.ac.ucl.comp0010.model`
- Exception classes: Can be in separate package

### Class Diagram Notes
- Getters & Setters are **omitted** in the diagram
- Fields omitted if relationship between classes is shown
- `mnc` field in Module stands for **mandatory non-condonable**

### Exception Classes

#### NoGradeAvailableException
- Thrown when:
  - No grade available at all, OR
  - No grade available for a specific module

#### NoRegistrationException
- Thrown when user attempts to access grades for unregistered modules

### Important Design Notes
- Give **meaningful names** to method parameters (diagrams show single characters due to space limitations)
- Design allows for extension beyond basic requirements

---

## 3. Spring Boot Implementation Details

### Project Initialization
- Initialize new Maven project using the provided Spring Boot link
- Can customize `artifactId`, `name`, and `description`
- **Package name must be**: `uk.ac.ucl.comp0010`

### Maven Build Configuration
- Set up Maven build plugins based on COMP0010 Coding Standards
- Set up reporting plugins from Coding Standards

### Spring Data Repository
- Repository classes must extend: `org.springframework.data.repository.CrudRepository`
- Use `spring-boot-starter-data-rest` to expose repository classes over REST

### REST API Requirements

#### GradeController
Must implement POST endpoint: `/grades/addGrade`

**Method Signature**:
```java
@PostMapping(value = "/grades/addGrade")
public ResponseEntity<Grade> addGrade(@RequestBody Map<String, String> params) {
  // Find the student by using student_id
  // Find the module by using the module_code
  // Create a Grade object and set all values
  // Save the Grade object.
  // Return the saved Grade object.
}
```

**Implementation Steps**:
1. Extract `student_id` from params
2. Extract `module_code` from params
3. Find corresponding Student and Module entities
4. Create Grade object with all values
5. Save to database
6. Return saved Grade object

### Database Configuration

#### Database Choice
- Use **H2 in-memory database**
- No data persistence required for now

#### Schema (PostgreSQL Syntax)
```sql
DROP TABLE IF EXISTS grade CASCADE;
DROP TABLE IF EXISTS registration CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS module CASCADE;

CREATE TABLE student(
  id INT PRIMARY KEY,
  firstName VARCHAR(30),
  lastName VARCHAR(30),
  username VARCHAR(30),
  email VARCHAR(50)
);

CREATE TABLE module(
  code VARCHAR(10) PRIMARY KEY,
  name VARCHAR(100),
  mnc BOOLEAN
);

CREATE TABLE grade(
  id SERIAL PRIMARY KEY,
  score INT,
  student_id INT,
  module_code VARCHAR(10),
  FOREIGN KEY (student_id)
    REFERENCES student (id),
  FOREIGN KEY (module_code)
    REFERENCES module (code)
);

CREATE TABLE registration(
  id SERIAL PRIMARY KEY,
  student_id INT,
  module_code VARCHAR(10),
  FOREIGN KEY (student_id)
    REFERENCES student (id),
  FOREIGN KEY (module_code)
    REFERENCES module (code)
);
```

#### Application Properties Configuration
```properties
spring.application.name=CW2
server.port=2800
spring.datasource.url=jdbc:h2:mem:test;MODE=PostgreSQL;
spring.datasource.driver-class-name=org.h2.Driver
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
```

### API Documentation (Swagger/OpenAPI)
- Add dependency to `pom.xml`:
  ```xml
  <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.2</version>
  </dependency>
  ```
- Access API documentation at: `http://localhost:2800/swagger-ui/index.html`
- Available while running: `mvn spring-boot:run`

### CORS Configuration
Required configuration class:
```java
package uk.ac.ucl.comp0010.config;

import static org.springframework.security.config.Customizer.withDefaults;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable()).cors(withDefaults());
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));
    config.setAllowCredentials(false);
    config.applyPermitDefaultValues();

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
```

**Note**: This class has Checkstyle issues that you must address yourself.

### Expose Entity IDs Configuration
Required configuration class:
```java
package uk.ac.ucl.comp0010.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {
    config.exposeIdsFor(Student.class);
    config.exposeIdsFor(Module.class);
    config.exposeIdsFor(Grade.class);
  }
}
```

**Purpose**: By default, Spring does not reveal object identifiers over REST. This configuration ensures IDs are included in responses.

### Spring Security Dependency (if using SecurityConfig)
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

## 4. Frontend Setup

### Available Implementations
Two implementations provided with no behavioral difference:
- **JavaScript version** (available)
- **TypeScript version** (available)

Team can choose either - no difference in functionality.

### Setup Instructions

#### Build Frontend
```bash
npm ci
# OR if npm ci fails:
npm install
```

#### Run Frontend
```bash
npm run dev
```

**Access Point**: `http://localhost:5173`

### Running Backend
```bash
# In backend folder
mvn spring-boot:run
```

**Result**: Frontend will work with backend once both are running

---

## 5. Code Validation

### Validation Command
All code will be validated using:
```bash
$ mvn compile test checkstyle:check spotbugs:check verify site
```

### Markers' Testing Procedure
- Markers will configure project with **recommended pom.xml** from Coding Standards
- Will run the same validation command above
- If you use different configuration, **you are responsible** for validating against model configuration before submission

---

## 6. Grading & Assessment Criteria

### Overall Grading Structure
- **Group Project Grade**: 0-100 marks
- **Individual Grade**: Based on group grade + individual contribution factors

### Individual Contribution Calculation

If all three members contributed sufficiently:
- Each member receives **100%** of group grade

**Three Contribution Factors**:

#### 1. General Contribution (40% weight)
Based on contribution form submitted after project completion.

**Score Ranges**:

| Score | Label | Description |
|-------|-------|-------------|
| 110-120 | Essential | Only you could do hard parts: robot design, coding challenges, excellent management |
| 100-109 | Good | Nice working with you; results much better because of you |
| 60-99 | Adequate | Did simple coding; project better for your contribution |
| 40-59 | Poor | Poor communication, meeting attendance, or coding; still added to project |
| 0-40 | Missing | Project would be as good/better without you; minimal contribution |

**Calculation**: `General Contribution = (Score / 100) × 0.4`

#### 2. Discussion Contribution (40% weight max)
Comments on issues and pull requests counted.

**Calculation**: `Discussion Contribution = min(# of your comments / # of total comments, 0.4)`

#### 3. Code Contribution (40% weight max)
Merged pull requests as proportion of total.

**Calculation**: `Code Contribution = min(# of your merged PRs / # of total merged PRs, 0.4)`

### Final Individual Grade
```
Individual Grade = round(min(1.0, (General + Discussion + Code)) × Group Score)
```

**Example**:
- Group score: 80
- General contribution score: 70 (Adequate)
  - General contribution: 70/100 × 0.4 = 0.28
- Discussion contribution: 0.35
- Code contribution: 0.3
- **Final Grade**: `round(min(1.0, (0.28 + 0.35 + 0.3)) × 80) = round(0.93 × 80) = 74`

---

## 7. Marking Criteria Details

### Total Marks: 100

#### 1. Maven Configuration (10 marks)

| Criteria | Marks |
|----------|-------|
| Correct Maven initialization via provided link + recommended pom.xml | ~10 marks |
| Correct Spring Boot dependencies setup | 4 marks |
| Checkstyle, Spotbugs, and JaCoCo configuration | 6 marks |

#### 2. Code Functionality (10 marks)

| Criteria | Marks |
|----------|-------|
| No Compilation Errors | 2 marks |
| No Test Failures | 2 marks |
| No Spotbugs errors (High threshold) | 2 marks |
| No Checkstyle errors | 4 marks |

#### 3. Test Coverage (10 marks)

| Coverage Level | Marks |
|----------------|-------|
| Test Failure | 0 marks |
| <59% | 4 marks |
| 60%-89% | 6 marks |
| 90%-99% | 8 marks |
| 100% | 10 marks |

#### 4. GitHub Usage (40 marks)

| Description | Marks |
|-------------|-------|
| Only few team interactions observed | 0-10 marks |
| Almost no team dynamics; all commits directly merged | 11-20 marks |
| Not enough GitHub interactions but project managed | 21-30 marks |
| Group members actively interacted on issues and PRs | 31-40 marks |

#### 5. Javadoc Documentation (10 marks)

- Must have no Checkstyle javadoc errors
- Javadoc comments should provide sufficient information:
  - Class descriptions
  - Public method descriptions
- Most students get most available marks for reasonable javadoc

#### 6. Implementation (20 marks)

| Description | Marks |
|-------------|-------|
| Nothing implemented | 0 marks |
| Basic design only, doesn't work properly | 0-5 marks |
| Basic design implemented, works well | 6-10 marks |
| Basic design + additional features | 11-15 marks |
| Impressive extensions beyond basic design | 16-20 marks |

---

## 8. Key Deliverables Checklist

- [ ] Maven project with correct configuration
- [ ] Spring Boot dependencies properly set up
- [ ] Static analyzers configured (Checkstyle, Spotbugs, JaCoCo)
- [ ] All model classes in `uk.ac.ucl.comp0010.model` package
- [ ] Exception classes properly implemented
- [ ] GradeController with POST `/grades/addGrade` endpoint
- [ ] Database schema and H2 configuration
- [ ] CORS configuration class
- [ ] RestConfiguration for exposing entity IDs
- [ ] Frontend (JavaScript or TypeScript) integrated
- [ ] 90%+ test coverage achieved
- [ ] All code passes Maven validation command
- [ ] GitHub Classroom repository with team collaboration
- [ ] Issues created for all tasks
- [ ] All commits reviewed via pull requests
- [ ] No direct commits to main branch
- [ ] Javadoc documentation complete
- [ ] Group contribution form submitted
- [ ] Additional features beyond basic design (for higher marks)

---

## 9. Resources & References

### Official Resources
- **Full Stack Development with Spring Boot 3 and React**
  - Accessible online via UCL account
  - Login process: Sign in at top right → Enter UCL email → Sign in with SSO

### Acknowledgment
This project is adapted from the original code by DongGyun Han (COMP0010 2024).

---

## Summary of Key Points

✓ Follow COMP0010 Coding Standards strictly
✓ Use GitHub Classroom for team collaboration
✓ Implement using Spring Boot + React/TypeScript
✓ 90%+ test coverage required
✓ All code must pass Maven validation
✓ TDD approach mandatory
✓ GitHub issues for task management
✓ Pull request reviews required (no direct commits)
✓ Minimum viable product with potential for extensions
✓ Individual grades based on group performance × personal contribution
✓ Deadline: December 2025
