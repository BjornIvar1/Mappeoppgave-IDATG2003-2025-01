package edu.ntnu.idi.bidata.model.entity;

import static org.junit.jupiter.api.Assertions.*;
import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.utils.exception.IntegerException;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import edu.ntnu.idi.bidata.utils.exception.StringException;
import org.junit.jupiter.api.*;

class PlayerTest {
  Player player1;
  Tile tile1;
  Tile tile2;

  @BeforeEach
  void setUp() {
    player1 = new Player("John", "RED", new BoardGame());
    tile1 = new Tile(2, 1, 0);
    tile2 = new Tile(3, 2, 0);

    tile1.setNextTile(tile2);
  }

  @Test
  void constructorPositiveTest() {
    assertEquals("John", player1.getName());
    assertEquals("RED", player1.getColor());
    assertEquals(0, player1.getBalance());
    assertEquals(1, player1.getCurrentTileId());
  }

  @Test
  void constructor2PositiveTest() {
    player1 = new Player("John", "RED", new BoardGame(), 100, 50);
    assertEquals("John", player1.getName());
    assertEquals("RED", player1.getColor());
    assertEquals(100, player1.getBalance());
    assertEquals(50, player1.getCurrentTileId());
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
    assertThrows(StringException.class, () -> player1.setColor(" "));
    assertThrows(StringException.class, () -> player1.setColor(null));
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

  @Test
  void setMoneyPositiveTest() throws IntegerException {
    player1.setBalance(100);
    assertEquals(100, player1.getBalance());
  }

  @Test
  void setMoneyNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> player1.setBalance(-100));
  }

  @Test
  void testPlayerSippedTrueTest() {
    player1.setInSkipped(true);
    assertTrue(player1.isPlayerIsSkipped());
  }

  @Test
  void testPlayerSippedFalseTest() {
    player1.setInSkipped(false);
    assertFalse(player1.isPlayerIsSkipped());
  }

  @Test
  void testGetCurrentTileId() {
    assertEquals(1, player1.getCurrentTileId());
  }
}