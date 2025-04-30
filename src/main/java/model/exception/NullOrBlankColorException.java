package model.exception;

/**
 * Exception thrown when a color is null or blank.
 *
 * <p>This exception is used in the Player class to indicate
 * that a color is null or blank.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.1
 * @since 0.0.1
 */
public class NullOrBlankColorException extends Exception {

  /**
   * Constructs a new NullOrBlankColor with the specified detail message.
   *
   * @param message the detail message
   */
  public NullOrBlankColorException(String message) {
    super(message);
  }
}
