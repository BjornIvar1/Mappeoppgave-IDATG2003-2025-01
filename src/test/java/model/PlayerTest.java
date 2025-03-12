package model;

import static org.junit.jupiter.api.Assertions.*;

import engine.BoardGame;
import org.junit.jupiter.api.*;

class PlayerTest {
  Player player1;
  Tile tile1;
  Tile tile2;

  @BeforeEach
  void setUp() {
    player1 = new Player("John", new BoardGame());
    tile1 = new Tile(2);
    tile2 = new Tile(3);

    tile1.setNextTile(tile2);
  }

  @Test
  void setNamePositiveTest(){
    assertEquals("John", player1.getName());
  }

  @Test
  void setNameNegativeTest(){
    assertThrows(IllegalArgumentException.class, () -> player1.setName(" "));
    assertThrows(IllegalArgumentException.class, () -> player1.setName(null));
  }

  @Test
  void placeOnTilePositiveTest () {
    player1.placeOnTile(tile1);
    assertEquals(2, player1.getCurrentTile().getTileId());
  }

  @Test
  void movePositiveTest () {
    player1.placeOnTile(tile1);
    player1.move(1);
    assertEquals(3, player1.getCurrentTile().getTileId());
  }

}