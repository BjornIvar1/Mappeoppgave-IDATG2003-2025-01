package model;

import engine.BoardGame;
import model.exception.InvalidNameException;
import model.exception.NegativeIntegerException;
import model.exception.NullOrBlankColorException;
import model.exception.TileNotFoundException;

/**
 * An entity class for player.
 *
 * <p>This class represents the player in the game.
 * Including methods for moving the player around the board</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.0
 * @since 0.0.1
 */

public class Player {
  private String name;
  private Tile currentTile;
  private final BoardGame game;
  private String color;
  private int balance;
  private boolean inJail;

  /**
   * A constructor for the class player for players in the
   * monopoly game.
   *
   * @param name is the name of the player.
   * @param game the board game the player is playing.
   * @param color the color of the player.
   * @param balance the balance of the player.
   * @throws NullOrBlankColorException if name or color is null or blank
   */
  public Player(String name, String color, BoardGame game, int balance)
      throws NullOrBlankColorException {
    setName(name);
    setColor(color);
    this.game = game;
    this.balance = balance;
    setBalance(0);

  }

  /**
   * A constructor for the class player for players in the
   * snakes and ladders game.
   *
   * @param name is the name of the player.
   * @param game the board game the player is playing.
   * @param color the color of the player.
   * @throws NullOrBlankColorException if name or color is null or blank
   */
  public Player(String name, String color, BoardGame game)
      throws NullOrBlankColorException {
    setName(name);
    setColor(color);
    this.game = game;
  }

  /**
   * Mutator method for the name.
   *
   * @param name of the player
   * @throws InvalidNameException if name is null or isBlank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new InvalidNameException("Name cannot be null or blank");
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
   * @throws NullOrBlankColorException if color is null or isBlank
   */
  public void setColor(String color) throws NullOrBlankColorException {
    if (color == null || color.isBlank()) {
      throw new NullOrBlankColorException("Color cannot be null or blank");
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
  public void move(int steps) throws TileNotFoundException {
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
   * @throws TileNotFoundException if the tile with the given id is not found
   */
  public void moveToTile(int tileId) throws TileNotFoundException {
    Tile destination = game.getBoard().getTileById(tileId);
    if (destination != null) {
      this.placeOnTile(destination);
    } else {
      throw new TileNotFoundException("Tile with id " + tileId + " not found");
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
   * @throws NegativeIntegerException if the balance is negative
   */
  public void setBalance(int balance) throws NegativeIntegerException {
    if (balance < 0) {
      throw new NegativeIntegerException("Balance cannot be negative");
    }
    this.balance = balance;
  }

  /**
   * Mutator method for the player's inJail status.
   *
   * @param inJail the new inJail status of the player
   *
   */
  public void setInJail(boolean inJail) {
    this.inJail = inJail;
  }

  /**
   * Accessor method for the player's inJail status.
   *
   * @return true if the player is in jail, false otherwise
   */
  public boolean isPlayerInJail() {
    return inJail;
  }

}
