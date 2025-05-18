package model.tileactions;

import engine.BoardGame;
import model.Board;
import model.Player;
import model.Tile;
import utils.exception.NullOrBlankException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LadderActionTest {
  LadderAction ladderAction;
  BoardGame boardGame;
  Board board;
  Map<Integer, Tile> tiles;
  Player player;
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setUp() throws NullOrBlankException {
    ladderAction = new LadderAction(3, "you have to move 3 tiles.");
    boardGame = new BoardGame();
    board = new Board(1, 3);
    tiles = new HashMap<>();
    tile1 = new Tile(1, 1, 0);
    tile2 = new Tile(2, 2, 0);
    tile3 = new Tile(3, 3, 0);

    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);
    boardGame.createBoard(board);

    player = new Player("John", "RED", boardGame, 0);
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
  void performPositiveTest() throws NullOrBlankException {
    ladderAction.perform(player);
    assertEquals(3, player.getCurrentTile().getTileId());
  }

  @Test
  void performNegativeTest() throws NullOrBlankException {
    ladderAction.perform(player);
    assertNotEquals(1, player.getCurrentTile().getTileId(),
        "Player has moved to tile 3, not 1");
  }

  @Test
  void getColor() {
    assertEquals("0xf5f04bff", ladderAction.getColor().toString());
  }
}