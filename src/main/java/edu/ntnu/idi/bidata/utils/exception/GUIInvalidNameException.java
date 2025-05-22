package edu.ntnu.idi.bidata.utils.exception;


/**
 * Exception thrown for invalid player name fields.
 *
 * <p>This exception is thrown when a player name field is invalid or empty.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
 * @since 0.0.1
 */
public class GUIInvalidNameException extends RuntimeException {
  /**
   * Constructs a new InvalidNameField with the specified detail message.
   *
   * @param message the detail message
   */
  public GUIInvalidNameException(String message) {
    super(message);
  }
}