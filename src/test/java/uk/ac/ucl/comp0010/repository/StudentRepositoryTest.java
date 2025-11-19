package uk.ac.ucl.comp0010.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Integration tests for StudentRepository.
 */
@DataJpaTest
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  /**
   * Test saving a student.
   */
  @Test
  void testSaveStudent() {
    Student student = new Student(999, "Test", "User", "tuser", "test@ucl.ac.uk");
    Student saved = studentRepository.save(student);

    assertNotNull(saved);
    assertEquals(999, saved.getId());
    assertEquals("Test", saved.getFirstName());
  }

  /**
   * Test finding a student by ID.
   */
  @Test
  void testFindById() {
    Student student = new Student(888, "Alice", "Brown", "abrown", "alice@ucl.ac.uk");
    studentRepository.save(student);

    Optional<Student> found = studentRepository.findById(888);

    assertTrue(found.isPresent());
    assertEquals("Alice", found.get().getFirstName());
  }

  /**
   * Test deleting a student.
   */
  @Test
  void testDeleteStudent() {
    Student student = new Student(777, "Bob", "Green", "bgreen", "bob@ucl.ac.uk");
    studentRepository.save(student);

    studentRepository.deleteById(777);

    Optional<Student> found = studentRepository.findById(777);
    assertTrue(found.isEmpty());
  }

  /**
   * Test counting students.
   */
  @Test
  void testCountStudents() {
    long initialCount = studentRepository.count();
    Student student = new Student(666, "Charlie", "White", "cwhite", "charlie@ucl.ac.uk");
    studentRepository.save(student);

    long newCount = studentRepository.count();
    assertEquals(initialCount + 1, newCount);
  }
}
