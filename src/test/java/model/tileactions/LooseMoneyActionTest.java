package model.tileactions;

import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.tileactions.LooseMoneyAction;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.utils.exception.IntegerException;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.entity.Tile;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LooseMoneyActionTest {
  LooseMoneyAction action;
  BoardGame boardGame;
  Board board;
  Player player;
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setUp() throws NullOrBlankException {
    action = new LooseMoneyAction(300, "you have to pay 300 $");
    boardGame = new BoardGame();
    board = new Board(1, 3);
    tile1 = new Tile(1, 1, 0);
    tile2 = new Tile(2, 2, 0);
    tile3 = new Tile(3, 3, 0);

    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);
    boardGame.createBoard(board);

    player = new Player("John", "RED", boardGame, 5000);
    player.placeOnTile(tile1);
  }

  @Test
  void setLooseMoneyPositiveTest() {
    action.setMoney(200);
    assertEquals(200, action.getLooseMoney());
  }

  @Test
  void setLooseMoneyNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> action.setMoney(0));
  }

  @Test
  void performPositiveTest() throws IntegerException {
    player.setBalance(5000);
    int initialBalance = player.getBalance();
    action.perform(player);
    assertEquals(initialBalance - action.getLooseMoney(), player.getBalance());
  }

  @Test
  void performZeroBalanceTest() throws IntegerException {
    player.setBalance(0);
    action.perform(player);
    assertEquals(0, player.getBalance());
  }

  @Test
  void performNegativeTest() throws IntegerException {
    int initialBalance = player.getBalance();
    player.setBalance(200);
    action.perform(player);
    assertNotEquals(initialBalance - action.getLooseMoney(), player.getBalance());
  }

  @Test
  void setDescriptionTest() {
    action.setDescription("New description");
    assertEquals("New description", action.getDescription());
  }

  @Test
  void setDescriptionNullTest() {
    assertThrows(IllegalArgumentException.class, () -> action.setDescription(null));
  }

  @Test
  void setDescriptionEmptyTest() {
    assertThrows(IllegalArgumentException.class, () -> action.setDescription(""));
  }

  @Test
  void getColorTest() {
    Color expectedColor = Color.web("#e5626a");
    assertEquals(expectedColor, action.getColor());
  }
}