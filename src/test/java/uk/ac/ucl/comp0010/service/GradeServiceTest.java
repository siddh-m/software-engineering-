package uk.ac.ucl.comp0010.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
 * Integration tests for GradeService.
 */
@SpringBootTest
@Transactional
class GradeServiceTest {

  @Autowired
  private GradeService gradeService;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ModuleRepository moduleRepository;

  @Autowired
  private GradeRepository gradeRepository;

  @Autowired
  private RegistrationRepository registrationRepository;

  private Student student1;
  private Student student2;
  private Module module1;
  private Module module2;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    // Create students
    student1 = new Student(1001, "Test", "Student1", "tstudent1", "test1@ucl.ac.uk");
    student2 = new Student(1002, "Test", "Student2", "tstudent2", "test2@ucl.ac.uk");
    studentRepository.save(student1);
    studentRepository.save(student2);

    // Create modules
    module1 = new Module("TEST101", "Test Module 1", true);
    module2 = new Module("TEST102", "Test Module 2", false);
    moduleRepository.save(module1);
    moduleRepository.save(module2);

    // Create registrations
    Registration reg1 = new Registration(student1, module1);
    Registration reg2 = new Registration(student1, module2);
    Registration reg3 = new Registration(student2, module1);
    registrationRepository.save(reg1);
    registrationRepository.save(reg2);
    registrationRepository.save(reg3);

    // Create grades
    Grade grade1 = new Grade(80, "2024-2025", student1, module1);
    Grade grade2 = new Grade(90, "2024-2025", student1, module2);
    Grade grade3 = new Grade(75, "2024-2025", student2, module1);
    gradeRepository.save(grade1);
    gradeRepository.save(grade2);
    gradeRepository.save(grade3);
  }

  /**
   * Test calculating average for student with grades.
   */
  @Test
  void testCalculateStudentAverageSuccess() throws NoGradeAvailableException {
    double average = gradeService.calculateStudentAverage(1001);
    assertEquals(85.0, average, 0.01);
  }

  /**
   * Test calculating average for student with no grades.
   */
  @Test
  void testCalculateStudentAverageNoGrades() {
    Student student3 = new Student(1003, "No", "Grades", "nogrades", "no@ucl.ac.uk");
    studentRepository.save(student3);

    assertThrows(NoGradeAvailableException.class, () -> {
      gradeService.calculateStudentAverage(1003);
    });
  }

  /**
   * Test calculating average for module with grades.
   */
  @Test
  void testCalculateModuleAverageSuccess() throws NoGradeAvailableException {
    double average = gradeService.calculateModuleAverage("TEST101");
    assertEquals(77.5, average, 0.01);
  }

  /**
   * Test calculating average for module with no grades.
   */
  @Test
  void testCalculateModuleAverageNoGrades() {
    Module module3 = new Module("TEST103", "No Grades Module", false);
    moduleRepository.save(module3);

    assertThrows(NoGradeAvailableException.class, () -> {
      gradeService.calculateModuleAverage("TEST103");
    });
  }

  /**
   * Test getting grades by student.
   */
  @Test
  void testGetGradesByStudent() {
    List<Grade> grades = gradeService.getGradesByStudent(1001);
    assertEquals(2, grades.size());
  }

  /**
   * Test getting grades by module.
   */
  @Test
  void testGetGradesByModule() {
    List<Grade> grades = gradeService.getGradesByModule("TEST101");
    assertEquals(2, grades.size());
  }

  /**
   * Test validating student registration - registered.
   */
  @Test
  void testIsStudentRegisteredTrue() {
    boolean isRegistered = gradeService.isStudentRegistered(1001, "TEST101");
    assertTrue(isRegistered);
  }

  /**
   * Test validating student registration - not registered.
   */
  @Test
  void testIsStudentRegisteredFalse() {
    boolean isRegistered = gradeService.isStudentRegistered(1002, "TEST102");
    assertFalse(isRegistered);
  }

  /**
   * Test adding grade with validation - registered student.
   */
  @Test
  void testAddGradeWithValidationSuccess() throws NoRegistrationException {
    Grade grade = gradeService.addGradeWithValidation(1002, "TEST101", 88, "2024-2025");
    assertEquals(88, grade.getScore());
    assertEquals("2024-2025", grade.getAcademicYear());
  }

  /**
   * Test adding grade with validation - not registered.
   */
  @Test
  void testAddGradeWithValidationNotRegistered() {
    assertThrows(NoRegistrationException.class, () -> {
      gradeService.addGradeWithValidation(1002, "TEST102", 88, "2024-2025");
    });
  }

  /**
   * Test deleting grade that exists.
   */
  @Test
  void testDeleteGradeExists() {
    Grade grade = new Grade(70, "2024-2025", student2, module2);
    Grade saved = gradeRepository.save(grade);

    boolean deleted = gradeService.deleteGrade(saved.getId());
    assertTrue(deleted);
  }

  /**
   * Test deleting grade that doesn't exist.
   */
  @Test
  void testDeleteGradeNotExists() {
    boolean deleted = gradeService.deleteGrade(99999);
    assertFalse(deleted);
  }

  /**
   * Test updating grade that exists.
   */
  @Test
  void testUpdateGradeExists() {
    Grade grade = new Grade(70, "2024-2025", student2, module2);
    Grade saved = gradeRepository.save(grade);

    var updated = gradeService.updateGrade(saved.getId(), 85);
    assertTrue(updated.isPresent());
    assertEquals(85, updated.get().getScore());
  }

  /**
   * Test updating grade that doesn't exist.
   */
  @Test
  void testUpdateGradeNotExists() {
    var updated = gradeService.updateGrade(99999, 85);
    assertTrue(updated.isEmpty());
  }
}
