package edu.ntnu.idi.bidata.model.entity;

import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.utils.exception.IntegerException;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import edu.ntnu.idi.bidata.utils.exception.StringException;

import java.util.logging.Logger;

/**
 * Represents a player in the board game.
 *
 * <p>This class represents the player in the game.
 * Including methods for moving the player around the board</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.2
 * @since 0.0.1
 */

public class Player {
  private final BoardGame game;
  private final int currentTileId;
  private String name;
  private String color;
  private int balance;
  private boolean inSkipped;
  private Tile currentTile;

  /**
   * A constructor for the {@code Player}.
   *
   * <p>Sets by default the balance to 0, and the starting tile to 1</p>
   *
   * @param name is the name of the player.
   * @param game the board game the player is playing.
   * @param color the color of the player.
   */
  public Player(String name, String color, BoardGame game) {
    setName(name);
    setColor(color);
    setBalance(0);
    this.game = game;
    this.currentTileId = 1;
  }

  /**
   * Constructor for the {@code Player} class.
   *
   * <p>This constructor is used to create a player with a specific tile id,
   * and the player's balance is set to the given value.
   * It is used when loading a saved game.</p>
   *
   *
   * @param name is the name of the player.
   * @param game the board game the player is playing.
   * @param color the color of the player.
   * @param balance the balance of the player.
   * @param currentTileId the id of the tile the player is on.
   */
  public Player(String name, String color, BoardGame game, int balance, int currentTileId) {
    setName(name);
    setColor(color);
    setBalance(balance);
    this.game = game;
    this.currentTileId = currentTileId;
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
    try {
      currentTile.landPlayer(this);
    } catch (NullOrBlankException e) {
      Logger.getLogger("Error: " + e.getMessage());
    }
  }

  /**
   * Moves the player to a specific tile using its tile ID.
   *
   * @param tileId the ID of the destination tile
   * @throws NullOrBlankException if the tile with the given ID is not found
   */
  public void moveToTile(int tileId) throws NullOrBlankException {
    Tile destination = game.getBoard().getTileById(tileId);
    if (destination != null) {
      this.placeOnTile(destination);
    } else {
      throw new NullOrBlankException("Tile with id " + tileId + " not found");
    }
  }

  /**
   * The current tile the player is placed on the board.
   *
   * @param tile the {@link Tile} to place the player on
   */
  public void placeOnTile(Tile tile) {
    currentTile = tile;
  }

  /**
   * Accesses the current tile the player is on.
   *
   * @return the current {@link Tile}
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
   * @throws IntegerException if the balance is negative
   */
  public void setBalance(int balance) throws IntegerException {
    if (balance < 0) {
      throw new IntegerException("Balance cannot be negative");
    }
    this.balance = balance;
  }

  /**
   * Mutator method for the player's inJail status.
   *
   * @param inSkipped the new inJail status of the player
   *
   */
  public void setInSkipped(boolean inSkipped) {
    this.inSkipped = inSkipped;
  }

  /**
   * Accessor method for the player's inJail status.
   *
   * @return true if the player is in skip, false otherwise
   */
  public boolean isPlayerIsSkipped() {
    return inSkipped;
  }

  /**
   * Accessor method for the player's current tile id.
   *
   * @return the current tile ID
   */
  public int getCurrentTileId() {
    return currentTileId;
  }

  /**
   * Mutator method for the name.
   *
   * @param name the name of a player
   * @throws StringException if name is {@code null} or blank
   */
  public void setName(String name) throws StringException {
    if (name == null || name.isBlank()) {
      throw new StringException("Name cannot be null or blank");
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
   * @throws StringException if color is {@code null} or isBlank
   */
  public void setColor(String color) throws StringException {
    if (color == null || color.isBlank()) {
      throw new StringException("Color cannot be null or blank");
    }
    this.color = color;
  }
}
