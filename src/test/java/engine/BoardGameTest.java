package engine;

import model.Board;
import model.Player;
import model.PlayerInMonopoly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
  BoardGame boardGame;
  List<Player> players;
  PlayerInMonopoly player1;
  PlayerInMonopoly player2;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();
    players = new ArrayList<>();

    player1 = new PlayerInMonopoly("Player1", "RED", boardGame, 1700);
    player2 = new PlayerInMonopoly("Player2", "BLACK", boardGame, 1700);
  }

  @Test
  void addPlayerPositiveTest() {
    boardGame.addPlayer(player1);
    boardGame.addPlayer(player2);

    assertEquals(2, boardGame.getPlayers().size());
  }

  @Test
  void addPlayerNegativeTestEmptyHashMap() {
    assertNotEquals(2, boardGame.getPlayers().size());
  }

  @Test
  void addPlayerNegativeTest2SamePlayer() {
    boardGame.addPlayer(player1);
    boardGame.addPlayer(player1);
    assertNotEquals(2, boardGame.getPlayers().size());
    assertEquals(1, boardGame.getPlayers().size());
  }

  @Test
  void addPlayerNegativeTestNullPlayer() {
    boardGame.addPlayer(null);
    assertNotEquals(1, boardGame.getPlayers().size());
    assertEquals(0, boardGame.getPlayers().size());
  }

  @Test
  void createDicePositiveTest() {
    boardGame.createDice(2);
    Dice dice1 = boardGame.getDice(); // Retrieve created dice
    assertEquals(2, dice1.getNumberOfDice());
  }

  @Test
  void createDiceNegativeTest() {
    boardGame.createDice(-2);
    Dice dice1 = boardGame.getDice();
    assertEquals(0, dice1.getNumberOfDice());
  }

  @Test
  void createBoardPositiveTest() {
    boardGame.createBoard(new Board(10, 10));
    assertNotNull(boardGame.getBoard());
  }

  @Test
  void createBoardNegativeTest() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> boardGame.createBoard(null));
    assertEquals("The board can not be null.", exception.getMessage());
  }
}