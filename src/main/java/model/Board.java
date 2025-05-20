package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import utils.exception.IntegerException;

/**
 * Class that represents the board.
 *
 * <p>The board is a collection of {@link Tile} that creates the
 * board. There is a possibility to
 * add tiles and fetch tiles.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.0
 * @since 0.0.1
 */
public class Board {
  private final Map<Integer, Tile> tiles;
  private int rows;
  private int columns;

  /**
   * Constructs a new model.Board object and creates a HashMap for the tiles,
   * as an empty Hashmap to store the tiles for the board.
   */
  public Board(int rows, int columns) {
    tiles = new HashMap<>();
    setRows(rows);
    setColumns(columns);
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

  /**
   * Returns the {@link HashMap} of the {@link #tiles}.
   *
   * @return the HashMap of the tiles
   */
  public Map<Integer, Tile> getTiles() {
    return tiles;
  }

  /**
   * Returns an iterator for the tiles.
   *
   * @return an iterator for the tiles
   */
  public Iterator<Tile> getTileIterator() {
    return tiles.values().iterator();
  }

  /**
   * Returns the number of tiles on the board.
   *
   * @return the number of tiles on the board
   */
  public int getTileCount() {
    return tiles.size();
  }

  /**
   * Returns the tile using the id.
   *
   * @param id the id of the tile
   * @return the tile
   */
  public Tile getTileById(int id) {
    return tiles.get(id);
  }

  /**
   * Sets the rows of the board.
   *
   * @param rows the rows of the board
   * @throws IntegerException if the rows are less than 0
   */
  public void setRows(int rows) throws IntegerException {
    if (rows <= 0) {
      throw new IntegerException("Rows must be greater than 0");
    }
    this.rows = rows;
  }

  /**
   * Sets the columns of the board.
   *
   * @param columns the columns of the board
   * @throws IntegerException if the columns are less than 0
   */
  public void setColumns(int columns) throws IntegerException {
    if (columns <= 0) {
      throw new IntegerException("Columns must be greater than 0");
    }
    this.columns = columns;
  }

  /**
   * Returns the column of the board.
   *
   * @return the column
   */
  public int getColumns() {
    return columns;
  }

  /**
   * Returns the row of the board.
   *
   * @return the row
   */
  public int getRows() {
    return rows;
  }
}
