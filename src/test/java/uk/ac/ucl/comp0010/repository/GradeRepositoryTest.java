package uk.ac.ucl.comp0010.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Integration tests for GradeRepository.
 */
@DataJpaTest
class GradeRepositoryTest {

  @Autowired
  private GradeRepository gradeRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ModuleRepository moduleRepository;

  private Student student;
  private Module module;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    student = new Student(100, "Test", "Student", "tstudent", "test@ucl.ac.uk");
    studentRepository.save(student);

    module = new Module("TEST100", "Test Module", true);
    moduleRepository.save(module);
  }

  /**
   * Test saving a grade.
   */
  @Test
  void testSaveGrade() {
    Grade grade = new Grade(85, student, module);
    Grade saved = gradeRepository.save(grade);

    assertNotNull(saved);
    assertNotNull(saved.getId());
    assertEquals(85, saved.getScore());
    assertEquals(student.getId(), saved.getStudent().getId());
  }

  /**
   * Test finding a grade by ID.
   */
  @Test
  void testFindById() {
    Grade grade = new Grade(92, student, module);
    Grade saved = gradeRepository.save(grade);

    Optional<Grade> found = gradeRepository.findById(saved.getId());

    assertTrue(found.isPresent());
    assertEquals(92, found.get().getScore());
  }

  /**
   * Test deleting a grade.
   */
  @Test
  void testDeleteGrade() {
    Grade grade = new Grade(78, student, module);
    Grade saved = gradeRepository.save(grade);

    gradeRepository.deleteById(saved.getId());

    Optional<Grade> found = gradeRepository.findById(saved.getId());
    assertTrue(found.isEmpty());
  }

  /**
   * Test counting grades.
   */
  @Test
  void testCountGrades() {
    long initialCount = gradeRepository.count();
    Grade grade = new Grade(88, student, module);
    gradeRepository.save(grade);

    long newCount = gradeRepository.count();
    assertEquals(initialCount + 1, newCount);
  }
}
