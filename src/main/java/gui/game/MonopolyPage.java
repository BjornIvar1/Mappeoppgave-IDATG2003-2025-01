package gui.game;


import engine.BoardGame;
import gui.BaseGamePage;
import javafx.scene.layout.BorderPane;

/**
 * Represents the Monopoly game page in the GUI.
 *
 * <p>This class is responsible for displaying the game board and handling user interactions</p>
 *
 * <p>The user is introduced to two buttons start game and roll dice</p>
 * <ul>
 *   <li>Start Game: Initializes a new game</li>
 * </ul>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.1
 */
public class MonopolyPage extends BaseGamePage {
  BoardGame boardGameForMonopoly;
  private final BorderPane mainLayout;

  private static final String BOARD_FILE_PATH =
      "src/main/resources/board/MonopolyBoard.json";
  private static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";

  public MonopolyPage() {
    mainLayout = new BorderPane();

  }

  protected void initializeGame() {
    boardGameForMonopoly = initializeBoardGame(BOARD_FILE_PATH, PLAYER_FILE_PATH);
  }


}
