package edu.ntnu.idi.bidata.model.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.ntnu.idi.bidata.model.entity.Board;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.entity.Tile;
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

    player1 = new Player("Player1", "RED", boardGame, 1700,1);
    player2 = new Player("Player2", "BLACK", boardGame, 1700,1);
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

  @Test
  void play_NoPlayers_ThrowsException() {
    BoardGame game = new BoardGame(); // assuming no players added
    assertThrows(NullOrBlankException.class, game::play);
  }

  @Test
  void testPlay() throws NullOrBlankException {
    BoardGame game = new BoardGame();
    Board board = new Board(10, 10);
    Tile tile1 = new Tile(1, 1, 0);
    Tile tile2 = new Tile(1, 2, 0);
    board.addTile(tile1);
    board.addTile(tile2);
    game.createBoard(board);

    Player player1 = new Player("Player1", "RED", game, 1700, 1);
    Player player2 = new Player("Player2", "BLACK", game, 1700, 1);

    game.addPlayer(player1);
    game.addPlayer(player2);

    player1.placeOnTile(tile1);

    game.createDice(1);

    game.play();

    player1.setInSkipped(true);
    assertTrue(player1.isPlayerIsSkipped());

    game.play();
    assertFalse(player1.isPlayerIsSkipped());
  }
}