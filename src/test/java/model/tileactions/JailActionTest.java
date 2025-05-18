package model.tileactions;

import engine.BoardGame;
import javafx.scene.paint.Color;
import model.Board;
import model.Player;
import model.Tile;
import model.exception.NullOrBlankColorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class JailActionTest {
  JailAction jailAction;
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
  void setup() throws NullOrBlankColorException {
    jailAction = new JailAction("You are in jail");
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
    jailAction.perform(player);
    assertTrue(player.isPlayerInJail());
  }

  @Test
  void performNegativeTest() {
    assertFalse(player.isPlayerInJail());
  }

  @Test
  void getDescriptionPositiveTest() {
    jailAction.setDescription("You are in jail!");
    assertEquals("You are in jail!", jailAction.getDescription());
  }

  @Test
  void setDescriptionNullTest() {
    assertThrows(IllegalArgumentException.class, () -> jailAction.setDescription(null));
  }

  @Test
  void setDescriptionEmptyTest() {
    assertThrows(IllegalArgumentException.class, () -> jailAction.setDescription(""));
  }

  @Test
  void getColorTest() {
    Color expectedColor = Color.web("#dfdfdf");
    assertEquals(expectedColor, jailAction.getColor());
  }
  
}