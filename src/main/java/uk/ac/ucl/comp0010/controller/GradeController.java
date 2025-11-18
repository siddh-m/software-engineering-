package uk.ac.ucl.comp0010.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * REST controller for managing grade operations.
 * Provides endpoints for adding and managing student grades.
 */
@RestController
@RequestMapping("/grades")
public class GradeController {

  private final StudentRepository studentRepository;
  private final ModuleRepository moduleRepository;
  private final GradeRepository gradeRepository;

  /**
   * Constructor for dependency injection.
   *
   * @param studentRepository repository for student operations
   * @param moduleRepository repository for module operations
   * @param gradeRepository repository for grade operations
   */
  @Autowired
  public GradeController(StudentRepository studentRepository,
      ModuleRepository moduleRepository,
      GradeRepository gradeRepository) {
    this.studentRepository = studentRepository;
    this.moduleRepository = moduleRepository;
    this.gradeRepository = gradeRepository;
  }

  /**
   * Adds a new grade for a student in a specific module.
   *
   * @param params map containing student_id, module_code, and score
   * @return ResponseEntity containing the saved Grade object or error status
   */
  @PostMapping(value = "/addGrade")
  public ResponseEntity<Grade> addGrade(@RequestBody Map<String, String> params) {
    try {
      // Extract parameters
      String studentIdStr = params.get("student_id");
      String moduleCode = params.get("module_code");
      String scoreStr = params.get("score");

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

      // Save the Grade object
      Grade savedGrade = gradeRepository.save(grade);

      // Return the saved Grade object
      return ResponseEntity.ok(savedGrade);

    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
