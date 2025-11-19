package uk.ac.ucl.comp0010.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for NoRegistrationException.
 */
public class NoRegistrationExceptionTest {

  /**
   * Test default constructor.
   */
  @Test
  void testDefaultConstructor() {
    NoRegistrationException exception = new NoRegistrationException();
    assertNotNull(exception);
    assertNull(exception.getMessage());
  }

  /**
   * Test constructor with message.
   */
  @Test
  void testConstructorWithMessage() {
    String message = "Student not registered for this module";
    NoRegistrationException exception = new NoRegistrationException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  /**
   * Test constructor with message and cause.
   */
  @Test
  void testConstructorWithMessageAndCause() {
    String message = "No registration found";
    Throwable cause = new RuntimeException("Database error");
    NoRegistrationException exception = new NoRegistrationException(message, cause);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
