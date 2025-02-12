import java.util.Map;

/**
 * Class that represents the board.
 *
 *<p> The board is a collection of {@link Tile} that creates the
 * board. There is a possibility to
 * add tiles and fetch tiles.
 *</p>
 *
 * @since 0.0.1
 * @author Arpit
 * @version 0.1.0
 */
public class Board {
  private Map<Integer, Tile> tiles;

  /**
   * Add tiles to the board.
   *
   * <p> Adds the tile to the board.
   * gives a new key value by checking the quantity of tiles.
   * </p>
   * @param tile the tile that will be added on the board.
   */
  public void addTile(Tile tile) {
    tiles.put(tiles.size()+1, tile);
  }

  /**
   * Finds and return the tile from the board.
   *
   * @param tile the tile we need to find.
   * @return the tile we need from the board.
   */
  public Tile getTile(int tile) {
    return tiles.get(tile);
  }

}
