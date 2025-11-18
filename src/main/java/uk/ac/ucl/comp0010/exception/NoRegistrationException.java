package uk.ac.ucl.comp0010.exception;

/**
 * Exception thrown when a user attempts to access grades for unregistered modules.
 * This ensures that grades can only be retrieved for modules
 * in which the student is properly registered.
 */
public class NoRegistrationException extends Exception {

  /**
   * Constructs a new NoRegistrationException with no detail message.
   */
  public NoRegistrationException() {
    super();
  }

  /**
   * Constructs a new NoRegistrationException with the specified detail message.
   *
   * @param message the detail message
   */
  public NoRegistrationException(String message) {
    super(message);
  }

  /**
   * Constructs a new NoRegistrationException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public NoRegistrationException(String message, Throwable cause) {
    super(message, cause);
  }
}
