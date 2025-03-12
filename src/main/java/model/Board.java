package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents the board.
 *
 * <p>The board is a collection of {@link Tile} that creates the
 * board. There is a possibility to
 * add tiles and fetch tiles.</p>
 *
 * @since 0.0.1
 * @author Arpit
 * @version 0.1.0
 */
public class Board {
  private final Map<Integer, Tile> tiles;

  /**
   * Constructs a new model.Board object and creates a HashMap for the tiles,
   * as an empty Hashmap to store the tiles for the board.
   */
  public Board() {
    tiles = new HashMap<>();
  }

  /**
   * Add tiles to the board.
   *
   * <p>Adds the tile to the board.
   * gives a new key value by checking the quantity of tiles.
   * </p>
   *
   * @param tile the tile that will be added on the board.
   */
  public void addTile(Tile tile) {
    tiles.put(tile.getTileId(), tile);
  }

  /**
   * Uses the tileID to find and return the tile from the board.
   *
   * @param tileId the ID of the tile we need to find.
   * @return the tile we need from the board.
   */
  public Tile getTile(int tileId) {
    return tiles.get(tileId);
  }

  public Map<Integer, Tile> getTiles() {
    return tiles;
  }

  public int getTileCount() {
    return tiles.size();
  }
}
