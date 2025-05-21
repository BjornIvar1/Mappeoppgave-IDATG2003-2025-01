package model;

import model.entity.Board;
import model.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Iterator;
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

    assertEquals(tile1, board.getTileById(1));
    assertEquals(tile2, board.getTileById(2));
    assertEquals(tile3, board.getTileById(3));
  }

  @Test
  void negativeTestGetTileById() {
    assertNull(board.getTileById(4));
  }

  @Test
  void getTilesTestById() {
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    tiles.put(1, tile1);
    tiles.put(2, tile2);
    tiles.put(3, tile3);

    assertEquals(tiles, board.getTiles());
    assertEquals(3, board.getTiles().size());
  }

  @Test
  void getTileByIdById_Test() {
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    assertEquals(tile1, board.getTileById(1));
    assertEquals(tile2, board.getTileById(2));
    assertEquals(tile3, board.getTileById(3));
  }

  @Test
  void getTileByIdByIdNegativeTest() {
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    assertNull(board.getTileById(4));
  }

  @Test
  void setRowsPositive_Test() {
    board.setRows(5);
    assertEquals(5, board.getRows());
  }

  @Test
  void setRowsNegative_Test() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> board.setRows(-1));
    assertEquals("Rows must be greater than 0", exception.getMessage());
  }

  @Test
  void setColumnsPositive_Test() {
    board.setColumns(5);
    assertEquals(5, board.getColumns());
  }

  @Test
  void setColumnsNegative_Test() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> board.setColumns(-1));
    assertEquals("Columns must be greater than 0", exception.getMessage());
  }

  @Test
  void getTileByIdCountTest() {
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    assertEquals(3, board.getTileCount());
  }

  @Test
  void getTileByIdCountNegativeTest() {
    assertEquals(0, board.getTileCount());
  }

  @Test
  void getTileByIdIteratorTest() {
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);

    Iterator<Tile> iterator = board.getTileIterator();
    assertTrue(iterator.hasNext());
    assertEquals(tile1, iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(tile2, iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(tile3, iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  void getTileByIdIteratorNegativeTest() {
    Iterator<Tile> iterator = board.getTileIterator();
    assertFalse(iterator.hasNext());
  }

}