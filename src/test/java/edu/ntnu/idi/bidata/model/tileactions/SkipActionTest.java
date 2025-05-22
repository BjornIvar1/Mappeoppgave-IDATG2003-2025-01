package edu.ntnu.idi.bidata.model.tileactions;

import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.entity.Tile;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkipActionTest {
  SkipAction skipAction;
  BoardGame boardGame;
  Board board;
  Player player;
  Tile tile1;
  Tile tile2;
  Tile tile3;


  @BeforeAll
  static void setUpBeforeClass() {
    System.out.println("Setting up before all tests");
  }

  @BeforeEach
  void setup() throws NullOrBlankException {
    skipAction = new SkipAction("You are in skip");
    boardGame = new BoardGame();
    tile1 = new Tile(1, 1, 0);
    tile2 = new Tile(2, 2, 0);
    tile3 = new Tile(3, 3, 0);

    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    board = new Board(1, 3);
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);
    boardGame.createBoard(board);

    player = new Player("John", "RED", boardGame, 0);
    player.placeOnTile(tile1);
  }

  @Test
  void performTest() {
    skipAction.perform(player);
    assertTrue(player.isPlayerIsSkipped());
  }

  @Test
  void performNegativeTest() {
    assertFalse(player.isPlayerIsSkipped());
  }

  @Test
  void getDescriptionPositiveTest() {
    skipAction.setDescription("You are in skip!");
    assertEquals("You are in skip!", skipAction.getDescription());
  }

  @Test
  void setDescriptionNullTest() {
    assertThrows(IllegalArgumentException.class, () -> skipAction.setDescription(null));
  }

  @Test
  void setDescriptionEmptyTest() {
    assertThrows(IllegalArgumentException.class, () -> skipAction.setDescription(""));
  }

  @Test
  void getColorTest() {
    Color expectedColor = Color.web("#dfdfdf");
    assertEquals(expectedColor, skipAction.getColor());
  }
  
}