package utils.exception;

/**
 * Exception thrown for invalid amount of player fields.
 *
 * <p>This exception is thrown when a user tries to create zero users for the board game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
 * @since 0.0.1
 */
public class InvalidPlayerFields extends RuntimeException {
  /**
   * Constructs a new InvalidPlayerFields with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidPlayerFields(String message) {
    super(message);
  }
}
