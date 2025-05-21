package observer;

/**
 * The interface {@code BoardGameObserver} defines the observer pattern.
 *
 * <p>It is used to notify observers when a player moves to a new tile.
 * The observer pattern is a behavioral design pattern that defines a one-to-many dependency
 * between objects so that when one object changes state, all its dependents are
 * notified and updated automatically.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.1
 * @since 0.0.1
 */
public interface BoardGameObserver {

  /**
   * Notifies observers when a player moves to a new tile.
   *
   * <p>This method is called when a player moves to a new tile on the board.
   * It notifies all registered observers about the player's movement.</p>
   *
   * @param tileId the ID of the tile where the player has moved
   */
  void observerPlayerMoved(int tileId);
}
