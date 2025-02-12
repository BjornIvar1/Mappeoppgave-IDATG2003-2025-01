import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setUp() {
    tile1 = new Tile(1);
    tile2 = new Tile(1);
    tile3 = new Tile(1);
  }

  @Test
  void testSetNextTileCorrectly() {
    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    assertEquals(tile2, tile1.getNextTile());
    assertEquals(tile2, tile1.getNextTile());
    assertNull(tile3.getNextTile());
  }
}