package engine;

import model.Board;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeAndLaddersGameTest {
  SnakeAndLaddersGame game;
  Board board;

  @BeforeEach
  void setUp() {
    game = new SnakeAndLaddersGame();
    board = game.getBoard();
  }

  @Test
  void testBoardCreation() {
    assertNotNull(board, "Board should not be null");
    assertEquals(9, board.getRows(),
        "The board should have 9 rows");
    assertEquals(10, board.getColumns(),
        "The board should have 10 rows");
  }

  @Test
  void testAmountOfTiles() {
    assertEquals(90, board.getTiles().size(),
    "The board should have 90 tiles");
  }

  @Test
  void testTileIDs() {
    for (int i = 1; i < board.getTiles().size(); i++) {
      Tile tile = board.getTile(i);
      assertNotNull(tile, "The tile should not be null");
      assertEquals(i, tile.getTileId(),
          "The tile ID should be the same, since its going in ascending order");
    }
  }

  @Test
  void testTileLinking() {
    for (int i = 1; i < board.getTiles().size(); i++) {
      Tile previousTile = board.getTile(i);
      Tile nexTile = board.getTile(i + 1);

      assertEquals(nexTile, previousTile.getNextTile());
    }
  }
}