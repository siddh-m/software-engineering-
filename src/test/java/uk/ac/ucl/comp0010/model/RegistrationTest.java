package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Registration model class.
 */
public class RegistrationTest {

  private Registration registration;
  private Student student;
  private Module module;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    registration = new Registration();
    student = new Student(1, "John", "Doe", "jdoe", "john.doe@ucl.ac.uk");
    module = new Module("COMP0010", "Software Engineering", true);
  }

  /**
   * Test default constructor.
   */
  @Test
  void testDefaultConstructor() {
    assertNotNull(registration);
  }

  /**
   * Test constructor with parameters.
   */
  @Test
  void testParameterizedConstructor() {
    Registration testRegistration = new Registration(student, module);

    assertEquals(student, testRegistration.getStudent());
    assertEquals(module, testRegistration.getModule());
  }

  /**
   * Test getId and setId methods.
   */
  @Test
  void testGetSetId() {
    registration.setId(1);
    assertEquals(1, registration.getId());
  }

  /**
   * Test getStudent and setStudent methods.
   */
  @Test
  void testGetSetStudent() {
    registration.setStudent(student);
    assertEquals(student, registration.getStudent());
    assertEquals("John", registration.getStudent().getFirstName());
  }

  /**
   * Test getModule and setModule methods.
   */
  @Test
  void testGetSetModule() {
    registration.setModule(module);
    assertEquals(module, registration.getModule());
    assertEquals("COMP0010", registration.getModule().getCode());
  }

  /**
   * Test setting all properties.
   */
  @Test
  void testSetAllProperties() {
    registration.setId(5);
    registration.setStudent(student);
    registration.setModule(module);

    assertEquals(5, registration.getId());
    assertEquals(student, registration.getStudent());
    assertEquals(module, registration.getModule());
  }
}
