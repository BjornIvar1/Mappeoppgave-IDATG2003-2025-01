/**
 * Class Tile represents a tile on a game board.
 * Each tile has its own ID, and a tile can perform an action on a player.
 *
 * @since 0.0.1
 * @author Bjørn
 * @version 0.0.1
 */
public class Tile {
  private Tile nextTile;
  private int tileId;
  private TileAction landAction;

  /**
   * Constructor for creating Tile with an unique ID.
   *
   * @param tileId the id for the tile
   */
  public Tile(int tileId) {
    this.tileId = tileId;
  }

  /**
   * Performs an action on a player when a player lands on the tile.
   * The action is performed by the landAction.
   *
   * @param player the player to perform an action on
   */
  public void landPlayer(Player player) {
    if (player != null) {
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
}
