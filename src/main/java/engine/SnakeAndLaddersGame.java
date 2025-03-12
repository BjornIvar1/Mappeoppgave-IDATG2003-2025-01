package engine;

import model.Tile;

/**
 * The class {@code engine.SnakeAndLaddersGame} represents the board game Snakes and Ladders.
 *
 * <p>The class creates a 9x10 board, and arrange the tiles going left to right.</p>
 *
 * <p>The class extends {@link BoardGame} inheriting the class functionalities</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public class SnakeAndLaddersGame extends BoardGame {
  private static final int ROWS = 9;
  private static  final int COLUMNS = 10;

  /**
   * A constructor the {@code engine.SnakeAndLaddersGame} class.
   */
  public SnakeAndLaddersGame() {
    super();
    createBoard(ROWS, COLUMNS);
    createGameBoard();
  }

  /**
   * Creates and arranges the tiles on the board.
   *
   * <p>The tiles are placed row by row.
   * Even rows are filled from left to right, while odd rows are filled right to left.
   * </p>
   */
  public void createGameBoard() {
    Tile previousTile = null;
    int tileId = 1;

    for (int y = 0; y < ROWS; y++) {
      if (y % 2 == 0) {
        for (int x = 0; x < COLUMNS; x++) {
          previousTile = addTile(tileId, x, y, previousTile);
          tileId++;
        }
      } else {
        for (int x = COLUMNS - 1; x >= 0; x--) {
          previousTile = addTile(tileId, x, y, previousTile);
          tileId++;
        }
      }
    }
  }

  /**
   * Adds a new tile on the board and links it to the previous tile.
   *
   * @param tileId the id of the tile added
   * @param x the x-coordinate of the tile
   * @param y the y-coordinate of the tile
   * @param previousTile the previous tile to link to the new tile
   * @return the new tile
   */
  public Tile addTile(int tileId, int x, int y, Tile previousTile) {
    Tile tile = new Tile(tileId, x, y);
    getBoard().addTile(tile);

    if (previousTile != null) {
      previousTile.setNextTile(tile);
    }
    return tile;
  }
}
