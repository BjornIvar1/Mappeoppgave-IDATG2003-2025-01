package model;

import engine.BoardGame;

/**
 * An entity class for player.
 *
 * <p>This class represents the player in the game.
 * Including methods for moving the player around the board</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
 * @since 0.0.1
 */

public class Player {
  private String name;
  private Tile currentTile;
  private final BoardGame game;
  private String color;
  private int balance;

  /**
   * A constructor for the class player.
   *
   * @param name is the name of the player.
   * @param game the board game the player is playing.
   * */
  public Player(String name, String color, BoardGame game , int balance) {
    setName(name);
    setColor(color);
    this.game = game;
    this.balance = balance;
    setBalance(0);

  }

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
   * Mutator method for the name.
   *
   * @param color of the player
   * @throws IllegalArgumentException if color is null or isBlank
   */
  public void setColor(String color) {
    if (color == null || color.isBlank()) {
      throw new IllegalArgumentException("Color cannot be null or blank");
    }
    this.color = color;
  }

  /**
   * Accessor method for player name.
   *
   * @return the color of th player
   */
  public String getColor() {
    return color;
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
   * Moves the player to a specific tile.
   *
   * @param tileId The id of the tile the player should move to.
   */
  public void moveToTile(int tileId) {
    Tile destination = game.getBoard().getTileById(tileId);
    if (destination != null) {
      this.placeOnTile(destination);
    } else {
      throw new IllegalArgumentException("Tile with id " + tileId + " not found");
    }
  }

  /**
   * The current tile the player is placed on the board.
   *
   * @param tile the player is on.
   */
  public void placeOnTile(Tile tile) {
    currentTile = tile;
  }

  /**
   * Accesses the current tile the player is on.
   *
   * @return the current tile the player is on.
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Accessor method for the player's balance.
   *
   * @return the player's balance
   */
  public int getBalance() {
    return balance;
  }

  /**
   * Mutator method for the player's balance.
   *
   * @param balance the new balance of the player
   * @throws IllegalArgumentException if the balance is negative
   */
  public void setBalance(int balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance cannot be negative");
    }
    this.balance = balance;
  }
}
