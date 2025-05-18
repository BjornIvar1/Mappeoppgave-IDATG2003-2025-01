package model;

import utils.exception.IntegerException;
import model.tileactions.TileAction;
import utils.exception.NullOrBlankException;

/**
 * Class model.Tile represents a tile on a game board.
 * Each tile has its own ID, and a tile can perform an action on a player.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.4
 * @since 0.0.1
 */
public class Tile {
  private Tile nextTile;
  private int tileId;
  private TileAction landAction;
  private int xCoordinate;
  private int yCoordinate;

  /**
   * Constructor for creating model.Tile with a unique ID.
   *
   * @param tileId the id for the tile
   */
  public Tile(int tileId, int xCoordinate, int yCoordinate) {
    setTileId(tileId);
    setXCoordinate(xCoordinate);
    setYCoordinate(yCoordinate);
  }

  /**
   * Performs an action on a player when a player lands on the tile.
   * The action is performed by the landAction.
   *
   * @param player the player to perform an action on
   */
  public void landPlayer(Player player) throws NullOrBlankException {
    if (player != null && landAction != null) {
      landAction.perform(player);
    }
  }

  /**
   * Sets the next tile on the game board.
   *
   * @param nextTile the next tile on the board
   */
  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  /**
   * Sets the tileId of a tile.
   *
   * @param tileId the id of the tile
   * @throws IntegerException if the tileId is negative
   */
  public void setTileId(int tileId) {
    if (tileId < 0) {
      throw new IntegerException("model.Tile ID must be a positive integer.");
    }
    this.tileId = tileId;
  }

  /**
   * Sets the action to be performed when a player lands on the tile.
   *
   * @param action the action to be performed
   */
  public void setLandAction(TileAction action) {
    this.landAction = action;
  }

  /**
   * Sets the x-coordinate of the tile.
   *
   * @param xCoordinate the x-coordinate of the tile
   * @throws IntegerException if the x-coordinate is negative
   */
  public void setXCoordinate(int xCoordinate) throws IntegerException {
    if (xCoordinate < 0) {
      throw new IntegerException("x-coordinate must be a positive number.");
    }
    this.xCoordinate = xCoordinate;
  }

  /**
   * Sets the y-coordinate of the tile.
   *
   * @param yCoordinate the y-coordinate of the tile
   * @throws IntegerException if the y-coordinate is negative
   */
  public void setYCoordinate(int yCoordinate) throws IntegerException {
    if (yCoordinate < 0) {
      throw new IntegerException("y-coordinate must be a positive number.");
    }
    this.yCoordinate = yCoordinate;
  }

  /**
   * Returns the next tile.
   *
   * @return the next tile on the board
   */
  public Tile getNextTile() {
    return nextTile;
  }

  /**
   * Returns the tileID of the tile.
   *
   * @return the tileID
   */
  public int getTileId() {
    return tileId;
  }

  /**
   * Returns the action to be performed when a player lands on the tile.
   *
   * @return the action to be performed
   */
  public TileAction getLandAction() {
    return landAction;
  }

  /**
   * Returns the x-coordinate of the tile.
   *
   * @return the xCoordinate
   */
  public int getXCoordinate() {
    return xCoordinate;
  }

  /**
   * Returns the y-coordinate of the tile.
   *
   * @return the yCoordinate
   */
  public int getYCoordinate() {
    return yCoordinate;
  }
}
