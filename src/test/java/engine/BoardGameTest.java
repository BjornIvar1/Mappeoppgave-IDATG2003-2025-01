package engine;

import model.Board;
import model.Player;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
  BoardGame boardGame;
  List<Player> players;
  Player player1;
  Player player2;
  Board board;
  Tile nextTile;

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();

    players = new ArrayList<>();

    player1 = new Player("Player1", boardGame);
    player2 = new Player("Player2", boardGame);
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
    boardGame.createBoard(20); //model.Board with 20 tiles.
    assertEquals(20, boardGame.numberOfTiles()); //Checks if there is 20 tiles.
  }

  @Test
  void createBoardNegativeTest() {
    boardGame.createBoard(-20);
    assertEquals(0, boardGame.numberOfTiles()); //Since number of tile is negative.
  }

  @Test
  void checkTilesPositiveTest() {
    int numberOfTiles = 5;
    boardGame.createBoard(numberOfTiles);
    board = boardGame.getBoard();

    Tile currentTile = board.getTile(0); // First tile

    for (int i = 1; i < numberOfTiles; i++) { // Start at 1 to check links correctly
      nextTile = currentTile.getNextTile(); // Retries the next tile

      //Ensures if the next tile is linked/connected to the next. recommended by OpenAI 2024
      assertNotNull(nextTile, "model.Tile " + (i - 1) + " should connect with tile " + i);
      //Ensures that the tile has correct index.
      assertEquals(i, nextTile.getTileId(), "model.Tile " + i + " should have correct index.");

      currentTile = nextTile; // Move to the next tile
    }
  }

  @Test
  void checkTilesNegativeTest() {
    int numberOfTiles = 5;
    boardGame.createBoard(numberOfTiles);
    board = boardGame.getBoard();
    Tile currentTile = board.getTile(0);

    for (int i = 1; i < numberOfTiles; i++) {
      nextTile = currentTile.getNextTile();
      currentTile = nextTile;
    }
    // Make sure no tile is after the last tile.
    assertNull(nextTile.getNextTile(), "Last tile should not link to another tile.");
  }






}