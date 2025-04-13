package gui.game;

import engine.BoardGame;
import gui.BaseGamePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import kontroller.ControllerSnakesAndLadders;
import model.Player;

/**
 * Represents the Snakes and Ladders game page in the GUI.
 *
 * <p>This class is responsible for displaying the game board and handling user interactions</p>
 *
 * <p>The user is introduced to two buttons start game and roll dice</p>
 * <ul>
 *   <li>Start Game: Initializes a new game</li>
 *   <li>Roll Dice: Plays the game by rolling the dice</li>
 * </ul>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.3
 */
public class SnakesAndLaddersPage extends BaseGamePage {

  private static final String BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLaddersBoard.json";
  private static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";

  private BoardGame boardGameSnakesAndL;
  private final BorderPane mainLayout;
  private Label gameInformation;

  /**
   * Constructor for the SnakesAndLaddersPage class.
   *
   * @param controllerSnakesAndLadders the controller for the Snakes and Ladders game.
   */
  public SnakesAndLaddersPage(ControllerSnakesAndLadders controllerSnakesAndLadders) {
    initializeGame();
    GridPane board = createBoard();
    HBox controlPanel = createControlPanel();

    mainLayout = new BorderPane();
    mainLayout.setTop(createMenuBar());
    mainLayout.setCenter(board);
    mainLayout.setBottom(controlPanel);

    BorderPane.setAlignment(controlPanel, Pos.CENTER);
    setAlignment(Pos.CENTER);

    getChildren().add(mainLayout);
  }

  /**
   * Creates the control panel with buttons to start the game and roll the dice.
   *
   * @return HBox containing the control panel with buttons.
   */
  private HBox createControlPanel() {
    HBox controlPanel = new HBox();
    controlPanel.setPadding(new Insets(10));
    controlPanel.setSpacing(10);
    controlPanel.setAlignment(Pos.CENTER);

    Button startButton = new Button("Start Game");
    startButton.setOnAction(e -> {
      initializeGame();
      updateBoard(mainLayout, boardGameSnakesAndL);
    });

    Button rollDice = getButton();

    gameInformation = new Label("Last rolled: ---");
    Label playerInformation = new Label(displayPlayers(boardGameSnakesAndL));

    controlPanel.getChildren().addAll(startButton, rollDice, gameInformation, playerInformation);
    return controlPanel;
  }

  private Button getButton() {
    Button rollDice = new Button("Roll Dice");
    rollDice.setOnAction(e -> {
      boardGameSnakesAndL.play();
      Player player = boardGameSnakesAndL.getCurrentPlayer();
      int rollSum = boardGameSnakesAndL.getDice().getDie(0) + boardGameSnakesAndL.getDice().getDie(1);
      if (boardGameSnakesAndL.getCurrentPlayer().getCurrentTile().getTileId() == 90) {
        gameInformation.setText("Winner: " + player.getName() + "\n" + "Press Start Game to play again");
      } else {
        gameInformation.setText(player.getName() + " rolled: " + rollSum);
      }
      updateBoard(mainLayout, boardGameSnakesAndL);
    });
    return rollDice;
  }

  /**
   * Initializes the game by creating a new BoardGame,
   * and creating a board, dice and adding the players.
   */
  protected void initializeGame() {
    boardGameSnakesAndL = initializeBoardGame(BOARD_FILE_PATH, PLAYER_FILE_PATH);
  }

  /**
   * Creates a tile for the game board.
   *
   * <p>This method creates a tile for the game board
   * by initializing {@code createBoard}</p>
   *
   * @return a StackPane representing the tile
   */
  private GridPane createBoard() {
    return createBoard(boardGameSnakesAndL);
  }
}
