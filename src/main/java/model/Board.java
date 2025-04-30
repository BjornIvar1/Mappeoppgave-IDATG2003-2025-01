package model;

import java.util.HashMap;
import java.util.Map;
import model.exception.NegativeIntegerException;

/**
 * Class that represents the board.
 *
 * <p>The board is a collection of {@link Tile} that creates the
 * board. There is a possibility to
 * add tiles and fetch tiles.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.2
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
   * @throws NegativeIntegerException if the rows are less than 0
   */
  public void setRows(int rows) throws NegativeIntegerException {
    if (rows <= 0) {
      throw new NegativeIntegerException("Rows must be greater than 0");
    }
    this.rows = rows;
  }

  /**
   * Sets the columns of the board.
   *
   * @param columns the columns of the board
   * @throws NegativeIntegerException if the columns are less than 0
   */
  public void setColumns(int columns) throws NegativeIntegerException {
    if (columns <= 0) {
      throw new NegativeIntegerException("Columns must be greater than 0");
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
