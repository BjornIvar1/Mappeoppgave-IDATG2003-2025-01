import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
  private Map<Integer, Tile> tiles;
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setUp() {
    tiles = new HashMap<Integer, Tile>();

    tile1 = new Tile(1);
    tile2 = new Tile(2);
    tile3 = new Tile(3);

    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);
  }

  @Test
  void positiveTestToAddTile() {
    tiles.put(1, tile1); //ACT
    tiles.put(2, tile2);
    tiles.put(3, tile3);
    assertEquals(tile1, tiles.get(1), "Was successfully"); //ASSERT
    assertEquals(tile2, tiles.get(2), "Was successfully");
    assertEquals(tile3, tiles.get(3), "Was successfully");
  }

}