package edu.ntnu.idi.bidata.utils.exception;

/**
 * Exception thrown when a tile is not found.
 *
 * <p>This exception is used in the Game class to indicate
 * that a tile with a specified ID does not exist.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.1
 * @since 0.0.2
 */
public class NullOrBlankException extends Exception {

  /**
   * Constructs a new TileNotFound with the specified detail message.
   *
   * @param message the detail message
   */
  public NullOrBlankException(String message) {
    super(message);
  }
}
