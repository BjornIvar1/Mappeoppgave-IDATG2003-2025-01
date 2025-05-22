package edu.ntnu.idi.bidata.model.tileactions;

import edu.ntnu.idi.bidata.model.engine.BoardGame;
import javafx.scene.paint.Color;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.entity.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import static org.junit.jupiter.api.Assertions.*;

class BankActionTest {
  BankAction bankAction;
  BoardGame boardGame;
  Board board;
  Map<Integer, Tile> tiles;
  Player player;
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setup() throws NullOrBlankException {
    bankAction = new BankAction(300, "you have to pay 300 $" );
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

    player = new Player("John", "RED", boardGame, 0, 1);
    player.placeOnTile(tile1);
  }

  @Test
  void setMoneyPositiveTest() {
    bankAction.setMoney(200);
    assertEquals(200, bankAction.getMoney());
  }

  @Test
  void setMoneyNegativeTest() {
    assertThrows(IllegalArgumentException.class, () -> bankAction.setMoney(0));
  }

  @Test
  void performTest() {
    int initialBalance = player.getBalance();
    bankAction.perform(player);
    assertEquals(initialBalance + 300, player.getBalance());
  }

  @Test
  void performNegativeTest() {
    int initialBalance = player.getBalance();
    bankAction.perform(player);
    assertNotEquals(initialBalance - 300, player.getBalance());
  }

  @Test
  void setDescriptionTest() {
    bankAction.setDescription("you have to pay 300 $");
    assertEquals("you have to pay 300 $", bankAction.getDescription());
  }

  @Test
  void setDescriptionNullTest() {
    assertThrows(IllegalArgumentException.class, () -> bankAction.setDescription(null));
  }

  @Test
  void setDescriptionEmptyTest() {
    assertThrows(IllegalArgumentException.class, () -> bankAction.setDescription(""));
  }

  @Test
  void getColorTest() {
    Color expectedColor = Color.web("#f5f04b");
    assertEquals(expectedColor, bankAction.getColor());
  }

}