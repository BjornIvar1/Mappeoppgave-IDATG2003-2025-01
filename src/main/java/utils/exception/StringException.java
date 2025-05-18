package utils.exception;

/**
 * Exception thrown when a name is invalid.
 *
 * <p>This exception is used in the Player class to indicate that a name is null or blank.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.1
 */
public class StringException extends IllegalArgumentException {

  /**
   * Constructs a new StringException with the specified detail message.
   *
   * @param message the detail message
   */
  public StringException(String message) {
    super(message);
  }
}
