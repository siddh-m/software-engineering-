package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Grade model class.
 */
public class GradeTest {

  private Grade grade;
  private Student student;
  private Module module;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    grade = new Grade();
    student = new Student(1, "John", "Doe", "jdoe", "john.doe@ucl.ac.uk");
    module = new Module("COMP0010", "Software Engineering", true);
  }

  /**
   * Test default constructor.
   */
  @Test
  void testDefaultConstructor() {
    assertNotNull(grade);
  }

  /**
   * Test constructor with all parameters.
   */
  @Test
  void testParameterizedConstructor() {
    Grade testGrade = new Grade(85, student, module);

    assertEquals(85, testGrade.getScore());
    assertEquals(student, testGrade.getStudent());
    assertEquals(module, testGrade.getModule());
  }

  /**
   * Test getId and setId methods.
   */
  @Test
  void testGetSetId() {
    grade.setId(1);
    assertEquals(1, grade.getId());
  }

  /**
   * Test getScore and setScore methods.
   */
  @Test
  void testGetSetScore() {
    grade.setScore(92);
    assertEquals(92, grade.getScore());
  }

  /**
   * Test getStudent and setStudent methods.
   */
  @Test
  void testGetSetStudent() {
    grade.setStudent(student);
    assertEquals(student, grade.getStudent());
    assertEquals("John", grade.getStudent().getFirstName());
  }

  /**
   * Test getModule and setModule methods.
   */
  @Test
  void testGetSetModule() {
    grade.setModule(module);
    assertEquals(module, grade.getModule());
    assertEquals("COMP0010", grade.getModule().getCode());
  }

  /**
   * Test setting all properties.
   */
  @Test
  void testSetAllProperties() {
    grade.setId(10);
    grade.setScore(78);
    grade.setStudent(student);
    grade.setModule(module);

    assertEquals(10, grade.getId());
    assertEquals(78, grade.getScore());
    assertEquals(student, grade.getStudent());
    assertEquals(module, grade.getModule());
  }
}
