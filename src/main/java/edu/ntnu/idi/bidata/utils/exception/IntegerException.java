package edu.ntnu.idi.bidata.utils.exception;

/**
 * Exception thrown when a player's balance is negative.
 *
 * <p>This exception is used in the Player class to indicate that a player's balance
 * cannot be negative.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
 * @since 0.0.1
 */
public class IntegerException extends IllegalArgumentException {

  /**
   * Constructs a new IntegerException with the specified detail message.
   *
   * @param message the detail message
   */
  public IntegerException(String message) {
    super(message);
  }
}
