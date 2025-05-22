package observer;

import model.engine.Dice;

/**
 * The interface {@code BoardGameObserver} defines the observer pattern.
 *
 * <p>It is used to notify observers when a player moves to a new tile.
 * The observer pattern is a behavioral design pattern that defines a one-to-many dependency
 * between objects so that when one object changes state, all its dependents are
 * notified and updated automatically.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.1.0
 * @since 0.1.0
 */
public interface BoardGameObserver {

  /**
   * Notifies observers when a player moves to a new tile.
   *
   * <p>This method is called when a player moves to a new tile on the board.
   * It notifies all registered observers about the player's movement.</p>
   *
   * @param name the name of the player who moved.
   * @param dice the dice rolled by the player.
   */
  void observerPlayerMoved(String name, Dice dice);

  /**
   * Notifies observers when a player is in skip.
   *
   * <p>This method is called when a player is in skip.
   * It notifies all registered observers about the player's status.</p>
   *
   * @param name the name of the player who is in skip.
   * @param isInJail true if the player is in skip, false otherwise.
   */
  void observerIsPlayerSkipped(String name, boolean isInJail);


  /**
   * Notifies observers when a player wins the game in Monopoly.
   *
   * <p>This method is called when a player wins the game.
   * It notifies all registered observers about the player's victory.</p>
   *
   * @param name the name of the player who won.
   * @param winner the total steps the player moved to win.
   */
  void observerPlayerWonInMonopoly(String name, boolean winner);

  void observerPlayerWonInSnakesAndLadders(String name, boolean winner);
}
