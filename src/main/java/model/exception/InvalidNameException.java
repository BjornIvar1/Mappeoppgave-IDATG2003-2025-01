package model.exception;

/**
 * Exception thrown when a name is invalid.
 *
 * <p>This exception is used in the Player class to indicate that a name is null or blank.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.1
 */
public class InvalidNameException extends IllegalArgumentException {

  /**
   * Constructs a new InvalidNameException with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidNameException(String message) {
    super(message);
  }

  //TODO: Change name to String and apply to all classes
}
