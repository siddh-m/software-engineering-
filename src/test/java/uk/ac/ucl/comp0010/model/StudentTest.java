package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Student model class.
 */
public class StudentTest {

  private Student student;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    student = new Student();
  }

  /**
   * Test default constructor.
   */
  @Test
  void testDefaultConstructor() {
    assertNotNull(student);
  }

  /**
   * Test constructor with all parameters.
   */
  @Test
  void testParameterizedConstructor() {
    Student testStudent = new Student(1, "John", "Doe", "jdoe", "john.doe@ucl.ac.uk");

    assertEquals(1, testStudent.getId());
    assertEquals("John", testStudent.getFirstName());
    assertEquals("Doe", testStudent.getLastName());
    assertEquals("jdoe", testStudent.getUsername());
    assertEquals("john.doe@ucl.ac.uk", testStudent.getEmail());
  }

  /**
   * Test getId and setId methods.
   */
  @Test
  void testGetSetId() {
    student.setId(100);
    assertEquals(100, student.getId());
  }

  /**
   * Test getFirstName and setFirstName methods.
   */
  @Test
  void testGetSetFirstName() {
    student.setFirstName("Alice");
    assertEquals("Alice", student.getFirstName());
  }

  /**
   * Test getLastName and setLastName methods.
   */
  @Test
  void testGetSetLastName() {
    student.setLastName("Smith");
    assertEquals("Smith", student.getLastName());
  }

  /**
   * Test getUsername and setUsername methods.
   */
  @Test
  void testGetSetUsername() {
    student.setUsername("asmith");
    assertEquals("asmith", student.getUsername());
  }

  /**
   * Test getEmail and setEmail methods.
   */
  @Test
  void testGetSetEmail() {
    student.setEmail("alice.smith@ucl.ac.uk");
    assertEquals("alice.smith@ucl.ac.uk", student.getEmail());
  }
}
