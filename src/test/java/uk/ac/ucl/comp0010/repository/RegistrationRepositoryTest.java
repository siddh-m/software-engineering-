package uk.ac.ucl.comp0010.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Integration tests for RegistrationRepository.
 */
@DataJpaTest
public class RegistrationRepositoryTest {

  @Autowired
  private RegistrationRepository registrationRepository;

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
    student = new Student(200, "Test", "Student", "tstudent", "test@ucl.ac.uk");
    studentRepository.save(student);

    module = new Module("TEST200", "Test Module", false);
    moduleRepository.save(module);
  }

  /**
   * Test saving a registration.
   */
  @Test
  void testSaveRegistration() {
    Registration registration = new Registration(student, module);
    Registration saved = registrationRepository.save(registration);

    assertNotNull(saved);
    assertNotNull(saved.getId());
    assertEquals(student.getId(), saved.getStudent().getId());
    assertEquals(module.getCode(), saved.getModule().getCode());
  }

  /**
   * Test finding a registration by ID.
   */
  @Test
  void testFindById() {
    Registration registration = new Registration(student, module);
    Registration saved = registrationRepository.save(registration);

    Optional<Registration> found = registrationRepository.findById(saved.getId());

    assertTrue(found.isPresent());
    assertEquals(student.getId(), found.get().getStudent().getId());
  }

  /**
   * Test deleting a registration.
   */
  @Test
  void testDeleteRegistration() {
    Registration registration = new Registration(student, module);
    Registration saved = registrationRepository.save(registration);

    registrationRepository.deleteById(saved.getId());

    Optional<Registration> found = registrationRepository.findById(saved.getId());
    assertTrue(found.isEmpty());
  }

  /**
   * Test counting registrations.
   */
  @Test
  void testCountRegistrations() {
    long initialCount = registrationRepository.count();
    Registration registration = new Registration(student, module);
    registrationRepository.save(registration);

    long newCount = registrationRepository.count();
    assertEquals(initialCount + 1, newCount);
  }
}
