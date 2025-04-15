package model.tileactions;

import engine.BoardGame;
import model.Board;
import model.PlayerInMonopoly;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BankActionTest {
  BankAction bankAction;
  BoardGame boardGame;
  Board board;
  Map<Integer, Tile> tiles;
  PlayerInMonopoly player;
  Tile tile1;
  Tile tile2;
  Tile tile3;

  @BeforeEach
  void setup() {
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

    player = new PlayerInMonopoly("John", "RED", boardGame, 0);
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

}