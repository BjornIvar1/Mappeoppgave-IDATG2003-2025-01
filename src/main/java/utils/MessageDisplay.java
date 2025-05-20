package utils;

/**
 * This class is responsible for displaying messages in the game.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.2
 */
public class MessageDisplay {
  // Prevent instantiation
  private MessageDisplay() {}

  /**
   * Displays the message when the game is over.
   *
   * @param player the player who won
   * @return the message to be displayed
   */
  public static String winningMessage(String player) {
    return "Winner: " + player + "\n" + "Press Start Game to play again";
  }

  /**
   * Displays the message when a player rolls the dice.
   *
   * @param rollSum the sum of the rolled dice
   * @return the message to be displayed
   */
  public static String rollDiceMessage(String player, int rollSum) {
    return player + " rolled: " + rollSum;
  }
}
