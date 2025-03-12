package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
  Board board;
  Map<Integer, Tile> tiles;

  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setUp() {
    board = new Board(10, 10);
    tiles = new HashMap<>();

    tile1 = new Tile(1, 0, 0);
    tile2 = new Tile(2, 1, 0);
    tile3 = new Tile(3, 2, 0);
  }

  @Test
  void positiveTestToAddTile() {
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    assertEquals(tile1, board.getTile(1));
    assertEquals(tile2, board.getTile(2));
    assertEquals(tile3, board.getTile(3));
  }

  @Test
  void negativeTestGetTile() {
    assertNull(board.getTile(4));
  }
}