package model;

/**
 * Class model.Tile represents a tile on a game board.
 * Each tile has its own ID, and a tile can perform an action on a player.
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public class Tile {
  private Tile nextTile;
  private int tileId;
  private TileAction landAction;
  private int xcoordinate;
  private int ycoordinate;

  /**
   * Constructor for creating model.Tile with a unique ID.
   *
   * @param tileId the id for the tile
   */
  public Tile(int tileId, int xcoordinate, int ycoordinate) {
    setTileId(tileId);
    setXcoordinate(xcoordinate);
    setYcoordinate(ycoordinate);
  }

  /**
   * Performs an action on a player when a player lands on the tile.
   * The action is performed by the landAction.
   *
   * @param player the player to perform an action on
   */
  public void landPlayer(Player player) {
    if (player != null && landAction != null) {
      landAction.perform(player);
    }
  }

  /**
   * Leaveplayer...
   * method for a player leaving the game?
   * method for player when no more tiles = winning the game?
   *
   * @param player the player to leave...
   */
  public void leavePlayer(Player player) {

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
   */
  public void setTileId(int tileId) {
    if (tileId < 0) {
      throw new IllegalArgumentException("model.Tile ID must be a positive integer.");
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

  public void setXcoordinate(int xcoordinate) {
    if (xcoordinate < 0) {
      throw new IllegalArgumentException("x-coordinate must be a positive number.");
    }
    this.xcoordinate = xcoordinate;
  }

  public void setYcoordinate(int ycoordinate) {
    if (ycoordinate < 0) {
      throw new IllegalArgumentException("y-coordinate must be a positive number.");
    }
    this.ycoordinate = ycoordinate;
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
  public int getXcoordinate() {
    return xcoordinate;
  }

  /**
   * Returns the y-coordinate of the tile.
   *
   * @return the yCoordinate
   */
  public int getYcoordinate() {
    return ycoordinate;
  }
}
