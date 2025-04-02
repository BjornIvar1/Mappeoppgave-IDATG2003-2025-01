package model;

import engine.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setUp() {
    tile1 = new Tile(0, 0, 0);
    tile2 = new Tile(1, 1, 0);
    tile3 = new Tile(2, 2, 0);
  }

  @Test
  void testSetNextTileCorrectly() {
    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    assertEquals(tile2, tile1.getNextTile());
    assertEquals(tile2, tile1.getNextTile());
    assertNull(tile3.getNextTile());
  }

  @Test
  void getCorrectTileId() {
    assertEquals(0, tile1.getTileId());
    assertEquals(1, tile2.getTileId());
    assertEquals(2, tile3.getTileId());
  }

  @Test
  void setIncorrectTileId() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> tile1.setTileId(-1));
    assertEquals("model.Tile ID must be a positive integer.", exception.getMessage());
  }

  @Test
  void setLandAction_Test() {
    TileAction action = new LadderAction(1,"Ladder");
    tile1.setLandAction(action);
    assertEquals(action, tile1.getLandAction());
  }

  @Test
  void setXcoordinatePositive_Test() {
    tile1.setXcoordinate(1);
    assertEquals(1, tile1.getXcoordinate());
  }

  @Test
  void setXcoordinateNegative_Test() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> tile1.setXcoordinate(-1));
    assertEquals("x-coordinate must be a positive number.", exception.getMessage());
  }

  @Test
  void setYcoordinatePositive_Test() {
    tile1.setYcoordinate(1);
    assertEquals(1, tile1.getYcoordinate());
  }

  @Test
  void setYcoordinateNegative_Test() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> tile1.setYcoordinate(-1));
    assertEquals("y-coordinate must be a positive number.", exception.getMessage());
  }
}