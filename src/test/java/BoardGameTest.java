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

  @BeforeEach
  void setUp() {
    boardGame = new BoardGame();

    players = new ArrayList<>();

    player1 = new Player("Player1");
    player2 = new Player("Player2");
  }

  @Test
  void addPlayerPositiveTest() {
    boardGame.addPlayer(player1);
    boardGame.addPlayer(player2);

    assertEquals(2, boardGame.getPlayers().size());
  }

  @Test
  void addPlayerNegativeTest() {
    assertNotEquals(2, boardGame.getPlayers().size());
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
    boardGame.createBoard(20); //Board with 20 tiles.
    assertEquals(20, boardGame.numberOfTiles()); //Checks if there is 20 tiles.
  }

  @Test
  void createBoardNegativeTest() {
    boardGame.createBoard(-20);
    assertEquals(0, boardGame.numberOfTiles());
  }

}