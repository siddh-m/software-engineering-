package uk.ac.ucl.comp0010.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Service layer for grade-related business logic.
 * Handles grade calculations, validation, and operations.
 */
@Service
public class GradeService {

  private final GradeRepository gradeRepository;
  private final StudentRepository studentRepository;
  private final ModuleRepository moduleRepository;
  private final RegistrationRepository registrationRepository;

  /**
   * Constructor for dependency injection.
   *
   * @param gradeRepository repository for grade operations
   * @param studentRepository repository for student operations
   * @param moduleRepository repository for module operations
   * @param registrationRepository repository for registration operations
   */
  @Autowired
  public GradeService(GradeRepository gradeRepository,
      StudentRepository studentRepository,
      ModuleRepository moduleRepository,
      RegistrationRepository registrationRepository) {
    this.gradeRepository = gradeRepository;
    this.studentRepository = studentRepository;
    this.moduleRepository = moduleRepository;
    this.registrationRepository = registrationRepository;
  }

  /**
   * Calculates the average grade for a student across all modules.
   *
   * @param studentId the student ID
   * @return the average grade
   * @throws NoGradeAvailableException if no grades available for the student
   */
  public double calculateStudentAverage(Integer studentId) throws NoGradeAvailableException {
    List<Grade> grades = getGradesByStudent(studentId);
    if (grades.isEmpty()) {
      throw new NoGradeAvailableException("No grades available for student ID: " + studentId);
    }

    return grades.stream()
        .mapToInt(Grade::getScore)
        .average()
        .orElseThrow(() -> new NoGradeAvailableException(
            "Could not calculate average for student ID: " + studentId));
  }

  /**
   * Calculates the average grade for a module across all students.
   *
   * @param moduleCode the module code
   * @return the average grade
   * @throws NoGradeAvailableException if no grades available for the module
   */
  public double calculateModuleAverage(String moduleCode) throws NoGradeAvailableException {
    List<Grade> grades = getGradesByModule(moduleCode);
    if (grades.isEmpty()) {
      throw new NoGradeAvailableException("No grades available for module: " + moduleCode);
    }

    return grades.stream()
        .mapToInt(Grade::getScore)
        .average()
        .orElseThrow(() -> new NoGradeAvailableException(
            "Could not calculate average for module: " + moduleCode));
  }

  /**
   * Gets all grades for a specific student.
   *
   * @param studentId the student ID
   * @return list of grades for the student
   */
  public List<Grade> getGradesByStudent(Integer studentId) {
    return ((List<Grade>) gradeRepository.findAll()).stream()
        .filter(grade -> grade.getStudent() != null
            && grade.getStudent().getId().equals(studentId))
        .toList();
  }

  /**
   * Gets all grades for a specific module.
   *
   * @param moduleCode the module code
   * @return list of grades for the module
   */
  public List<Grade> getGradesByModule(String moduleCode) {
    return ((List<Grade>) gradeRepository.findAll()).stream()
        .filter(grade -> grade.getModule() != null
            && grade.getModule().getCode().equals(moduleCode))
        .toList();
  }

  /**
   * Validates if a student is registered for a module.
   *
   * @param studentId the student ID
   * @param moduleCode the module code
   * @return true if registered, false otherwise
   */
  public boolean isStudentRegistered(Integer studentId, String moduleCode) {
    return ((List<Registration>) registrationRepository.findAll()).stream()
        .anyMatch(reg -> reg.getStudent() != null
            && reg.getStudent().getId().equals(studentId)
            && reg.getModule() != null
            && reg.getModule().getCode().equals(moduleCode));
  }

  /**
   * Adds a grade for a student in a module with validation.
   *
   * @param studentId the student ID
   * @param moduleCode the module code
   * @param score the grade score
   * @param academicYear the academic year
   * @return the saved grade
   * @throws NoRegistrationException if student not registered for module
   */
  public Grade addGradeWithValidation(Integer studentId, String moduleCode,
      Integer score, String academicYear) throws NoRegistrationException {
    // Validate registration
    if (!isStudentRegistered(studentId, moduleCode)) {
      throw new NoRegistrationException(
          "Student " + studentId + " is not registered for module " + moduleCode);
    }

    // Get student and module
    Optional<Student> studentOpt = studentRepository.findById(studentId);
    Optional<Module> moduleOpt = moduleRepository.findById(moduleCode);

    if (studentOpt.isEmpty() || moduleOpt.isEmpty()) {
      throw new IllegalArgumentException("Student or module not found");
    }

    // Create and save grade
    Grade grade = new Grade(score, academicYear, studentOpt.get(), moduleOpt.get());
    return gradeRepository.save(grade);
  }

  /**
   * Deletes a grade by ID.
   *
   * @param gradeId the grade ID
   * @return true if deleted, false if not found
   */
  public boolean deleteGrade(Integer gradeId) {
    if (gradeRepository.existsById(gradeId)) {
      gradeRepository.deleteById(gradeId);
      return true;
    }
    return false;
  }

  /**
   * Updates a grade score.
   *
   * @param gradeId the grade ID
   * @param newScore the new score
   * @return the updated grade
   */
  public Optional<Grade> updateGrade(Integer gradeId, Integer newScore) {
    Optional<Grade> gradeOpt = gradeRepository.findById(gradeId);
    if (gradeOpt.isPresent()) {
      Grade grade = gradeOpt.get();
      grade.setScore(newScore);
      return Optional.of(gradeRepository.save(grade));
    }
    return Optional.empty();
  }
}
