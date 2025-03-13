package model;

import engine.BoardGame;

/**
 * An entity class for player.
 *
 * <p>This class represents the player in the game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */

public class Player {
  private String name;
  private Tile currentTile;

  /**
   * A constructor for the class player.
   *
   * @param name is the name of the player.
   * @param game the board game the player is playing.
   * */
  public Player(String name, BoardGame game) {
    setName(name);
  }

  //getter and setter
  /**
   * Mutator method for the name.
   *
   * @param name of the player
   * @throws IllegalArgumentException if name is null or isBlank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    this.name = name;
  }

  /**
   * Accessor method for player name.
   *
   * @return the player name
   */
  public String getName() {
    return name;
  }

  /**
   * Moves the player on the board,
   * by using a loop to move the player onto the next tile.
   *
   * @param steps The amount of steps to the player.
   */
  public void move(int steps) {
    for (int i = 0; i < steps; i++) {
      if (currentTile.getNextTile() != null) {
        currentTile = currentTile.getNextTile();
      }
    }
    currentTile.landPlayer(this);
  }

  /**
   * The current tile the player is placed on the board.
   *
   * @param tile the player is on.
   */
  public void placeOnTile(Tile tile) {
    currentTile = tile;
  }

  public Tile getCurrentTile() {
    return currentTile;
  }
}
