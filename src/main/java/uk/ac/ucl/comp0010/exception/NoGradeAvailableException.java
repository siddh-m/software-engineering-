package uk.ac.ucl.comp0010.exception;

/**
 * Exception thrown when no grade is available.
 * This can occur when attempting to retrieve grades that don't exist,
 * either for all modules or for a specific module.
 */
public class NoGradeAvailableException extends Exception {

  /**
   * Constructs a new NoGradeAvailableException with no detail message.
   */
  public NoGradeAvailableException() {
    super();
  }

  /**
   * Constructs a new NoGradeAvailableException with the specified detail message.
   *
   * @param message the detail message
   */
  public NoGradeAvailableException(String message) {
    super(message);
  }

  /**
   * Constructs a new NoGradeAvailableException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public NoGradeAvailableException(String message, Throwable cause) {
    super(message, cause);
  }
}
