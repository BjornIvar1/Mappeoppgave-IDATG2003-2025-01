package model.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.engine.Dice;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.ntnu.idi.bidata.utils.exception.NullOrBlankException;
import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
  BoardGame boardGame;
  List<Player> players;
  Player player1;
  Player player2;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();
    players = new ArrayList<>();

    player1 = new Player("Player1", "RED", boardGame, 1700);
    player2 = new Player("Player2", "BLACK", boardGame, 1700);
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
  void createBoardPositiveTest() throws NullOrBlankException {
    boardGame.createBoard(new Board(10, 10));
    assertNotNull(boardGame.getBoard());
  }

  @Test
  void createBoardNegativeTest() {
    Exception exception = assertThrows(NullOrBlankException.class, () -> boardGame.createBoard(null));
    assertEquals("The board can not be null.", exception.getMessage());
  }

  @Test
  void getPlayerIteratorPositiveTest() {
    boardGame.addPlayer(player1);
    boardGame.addPlayer(player2);
    Iterator<Player> playerIterator = boardGame.getPlayerIterator();
    assertTrue(playerIterator.hasNext());
    assertEquals(player1, playerIterator.next());
    assertTrue(playerIterator.hasNext());
    assertEquals(player2, playerIterator.next());
    assertFalse(playerIterator.hasNext());
  }

  @Test
  void getPlayerIteratorNegativeTest() {
    Iterator<Player> playerIterator = boardGame.getPlayerIterator();
    assertFalse(playerIterator.hasNext());
  }
}