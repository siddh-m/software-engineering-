package uk.ac.ucl.comp0010.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for NoGradeAvailableException.
 */
public class NoGradeAvailableExceptionTest {

  /**
   * Test default constructor.
   */
  @Test
  void testDefaultConstructor() {
    NoGradeAvailableException exception = new NoGradeAvailableException();
    assertNotNull(exception);
    assertNull(exception.getMessage());
  }

  /**
   * Test constructor with message.
   */
  @Test
  void testConstructorWithMessage() {
    String message = "No grades available for this student";
    NoGradeAvailableException exception = new NoGradeAvailableException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  /**
   * Test constructor with message and cause.
   */
  @Test
  void testConstructorWithMessageAndCause() {
    String message = "No grades available";
    Throwable cause = new RuntimeException("Database error");
    NoGradeAvailableException exception = new NoGradeAvailableException(message, cause);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
