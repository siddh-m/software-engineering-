package uk.ac.ucl.comp0010.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;
import uk.ac.ucl.comp0010.service.GradeService;

/**
 * REST controller for managing grade operations.
 * Provides endpoints for adding, retrieving, updating, and calculating grades.
 */
@RestController
@RequestMapping("/grades")
public class GradeController {

  private final StudentRepository studentRepository;
  private final ModuleRepository moduleRepository;
  private final GradeRepository gradeRepository;
  private final GradeService gradeService;

  /**
   * Constructor for dependency injection.
   *
   * @param studentRepository repository for student operations
   * @param moduleRepository repository for module operations
   * @param gradeRepository repository for grade operations
   * @param gradeService service for grade business logic
   */
  @Autowired
  public GradeController(StudentRepository studentRepository,
      ModuleRepository moduleRepository,
      GradeRepository gradeRepository,
      GradeService gradeService) {
    this.studentRepository = studentRepository;
    this.moduleRepository = moduleRepository;
    this.gradeRepository = gradeRepository;
    this.gradeService = gradeService;
  }

  /**
   * Adds a new grade for a student in a specific module.
   *
   * @param params map containing student_id, module_code, score, and optionally academic_year
   * @return ResponseEntity containing the saved Grade object or error status
   */
  @PostMapping(value = "/addGrade")
  public ResponseEntity<Grade> addGrade(@RequestBody Map<String, String> params) {
    try {
      // Extract parameters
      String studentIdStr = params.get("student_id");
      String moduleCode = params.get("module_code");
      String scoreStr = params.get("score");
      String academicYear = params.get("academic_year");

      // Validate parameters
      if (studentIdStr == null || moduleCode == null || scoreStr == null) {
        return ResponseEntity.badRequest().build();
      }

      Integer studentId = Integer.parseInt(studentIdStr);
      Integer score = Integer.parseInt(scoreStr);

      // Find the student by using student_id
      Optional<Student> studentOpt = studentRepository.findById(studentId);
      if (studentOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      Student student = studentOpt.get();

      // Find the module by using the module_code
      Optional<Module> moduleOpt = moduleRepository.findById(moduleCode);
      if (moduleOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      Module module = moduleOpt.get();

      // Create a Grade object and set all values
      Grade grade = new Grade();
      grade.setScore(score);
      grade.setStudent(student);
      grade.setModule(module);
      if (academicYear != null) {
        grade.setAcademicYear(academicYear);
      }

      // Save the Grade object
      Grade savedGrade = gradeRepository.save(grade);

      // Return the saved Grade object
      return ResponseEntity.ok(savedGrade);

    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Adds a grade with validation (checks if student is registered).
   *
   * @param params map containing student_id, module_code, score, and academic_year
   * @return ResponseEntity containing the saved Grade object or error status
   */
  @PostMapping(value = "/addGradeValidated")
  public ResponseEntity<?> addGradeValidated(@RequestBody Map<String, String> params) {
    try {
      String studentIdStr = params.get("student_id");
      String moduleCode = params.get("module_code");
      String scoreStr = params.get("score");
      String academicYear = params.get("academic_year");

      if (studentIdStr == null || moduleCode == null || scoreStr == null) {
        return ResponseEntity.badRequest().body("Missing required parameters");
      }

      Integer studentId = Integer.parseInt(studentIdStr);
      Integer score = Integer.parseInt(scoreStr);

      Grade grade = gradeService.addGradeWithValidation(studentId, moduleCode,
          score, academicYear);
      return ResponseEntity.ok(grade);

    } catch (NoRegistrationException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("Invalid number format");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  /**
   * Gets all grades for a specific student.
   *
   * @param studentId the student ID
   * @return list of grades for the student
   */
  @GetMapping("/student/{studentId}")
  public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Integer studentId) {
    List<Grade> grades = gradeService.getGradesByStudent(studentId);
    return ResponseEntity.ok(grades);
  }

  /**
   * Gets all grades for a specific module.
   *
   * @param moduleCode the module code
   * @return list of grades for the module
   */
  @GetMapping("/module/{moduleCode}")
  public ResponseEntity<List<Grade>> getGradesByModule(@PathVariable String moduleCode) {
    List<Grade> grades = gradeService.getGradesByModule(moduleCode);
    return ResponseEntity.ok(grades);
  }

  /**
   * Calculates and returns the average grade for a student.
   *
   * @param studentId the student ID
   * @return map containing the average grade
   */
  @GetMapping("/student/{studentId}/average")
  public ResponseEntity<?> getStudentAverage(@PathVariable Integer studentId) {
    try {
      double average = gradeService.calculateStudentAverage(studentId);
      Map<String, Object> response = new HashMap<>();
      response.put("student_id", studentId);
      response.put("average", average);
      return ResponseEntity.ok(response);
    } catch (NoGradeAvailableException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  /**
   * Calculates and returns the average grade for a module.
   *
   * @param moduleCode the module code
   * @return map containing the average grade
   */
  @GetMapping("/module/{moduleCode}/average")
  public ResponseEntity<?> getModuleAverage(@PathVariable String moduleCode) {
    try {
      double average = gradeService.calculateModuleAverage(moduleCode);
      Map<String, Object> response = new HashMap<>();
      response.put("module_code", moduleCode);
      response.put("average", average);
      return ResponseEntity.ok(response);
    } catch (NoGradeAvailableException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  /**
   * Deletes a grade by ID.
   *
   * @param gradeId the grade ID
   * @return ResponseEntity with status
   */
  @DeleteMapping("/{gradeId}")
  public ResponseEntity<Void> deleteGrade(@PathVariable Integer gradeId) {
    boolean deleted = gradeService.deleteGrade(gradeId);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Updates a grade score.
   *
   * @param gradeId the grade ID
   * @param params map containing new score
   * @return ResponseEntity containing updated grade or error status
   */
  @PutMapping("/{gradeId}")
  public ResponseEntity<?> updateGrade(@PathVariable Integer gradeId,
      @RequestBody Map<String, String> params) {
    try {
      String scoreStr = params.get("score");
      if (scoreStr == null) {
        return ResponseEntity.badRequest().body("Score is required");
      }

      Integer newScore = Integer.parseInt(scoreStr);
      Optional<Grade> updatedGrade = gradeService.updateGrade(gradeId, newScore);

      if (updatedGrade.isPresent()) {
        return ResponseEntity.ok(updatedGrade.get());
      }
      return ResponseEntity.notFound().build();

    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("Invalid score format");
    }
  }
}
