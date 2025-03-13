package model;

import engine.BoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LadderActionTest {
  LadderAction ladderAction;
  Player player;
  Tile tile1;
  Tile tile2;
  Tile tile3;
  Tile tile4;
  Tile tile5;

  @BeforeEach
  void setUp() {
    ladderAction = new LadderAction(3, "you have to move 3 tiles.");
    tile1 = new Tile(1, 1, 1);
    tile2 = new Tile(2, 2, 1);
    tile3 = new Tile(3, 3, 1);
    tile4 = new Tile(4, 4, 1);
    tile5 = new Tile(5, 5, 1);

    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);
    tile3.setNextTile(tile4);
    tile4.setNextTile(tile5);

    player = new Player("John", new BoardGame());
    player.placeOnTile(tile1);
  }

  @Test
  void setDestinationPositiveTest() {
    ladderAction.setDestinationTile(3);
    assertEquals(3, ladderAction.getDestinationTile());
  }

  @Test
  void setDestinationNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> ladderAction.setDestinationTile(-3),
        "can not be lower then 1");
    assertThrows(IllegalArgumentException.class, () -> ladderAction.setDestinationTile(100),
        "Can not be over 10");
  }

  @Test
  void setDescriptionPositiveTest() {
    ladderAction.setDescription("You have to move 3 tiles.");
    assertEquals("You have to move 3 tiles.", ladderAction.getDescription());
  }

  @Test
  void setDescriptionNegativeTestEmpty() {
    assertThrows(IllegalArgumentException.class, () -> ladderAction.setDescription(""));
  }

  @Test
  void setDescriptionNullTest() {
    assertThrows(IllegalArgumentException.class, () -> ladderAction.setDescription(null) );
  }

  @Test
  void performPositiveTest() {
    ladderAction.perform(player);
    assertEquals(4, player.getCurrentTile().getTileId());
  }

  @Test
  void performNegativeTest() {
    ladderAction.perform(player);
    assertNotEquals(1, player.getCurrentTile().getTileId(),
        "Player has moved to tile 4, not 1");
  }

}