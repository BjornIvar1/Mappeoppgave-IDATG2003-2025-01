package model;

import static org.junit.jupiter.api.Assertions.*;

import engine.BoardGame;
import model.exception.NegativeIntegerException;
import model.exception.NullOrBlankColorException;
import model.exception.TileNotFoundException;
import org.junit.jupiter.api.*;

class PlayerTest {
  Player player1;
  Tile tile1;
  Tile tile2;

  @BeforeEach
  void setUp() throws NullOrBlankColorException {
    player1 = new Player("John", "RED", new BoardGame(), 0);
    tile1 = new Tile(2, 1, 0);
    tile2 = new Tile(3, 2, 0);

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
  void setColorPositiveTest(){
    assertEquals("RED", player1.getColor());
  }

  @Test
  void setColorNegativeTest(){
    assertThrows(NullOrBlankColorException.class, () -> player1.setColor(" "));
    assertThrows(NullOrBlankColorException.class, () -> player1.setColor(null));
  }

  @Test
  void placeOnTilePositiveTest () {
    player1.placeOnTile(tile1);
    assertEquals(2, player1.getCurrentTile().getTileId());
  }

  @Test
  void movePositiveTest () throws TileNotFoundException {
    player1.placeOnTile(tile1);
    player1.move(1);
    assertEquals(3, player1.getCurrentTile().getTileId());
  }

  @Test
  void setMoneyPositiveTest() throws NegativeIntegerException {
    player1.setBalance(100);
    assertEquals(100, player1.getBalance());
  }

  @Test
  void setMoneyNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> player1.setBalance(-100));
  }

}