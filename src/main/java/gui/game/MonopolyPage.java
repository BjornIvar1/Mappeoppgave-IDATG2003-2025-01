package gui.game;


import engine.BoardGame;
import gui.BaseGamePage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import kontroller.ControllerMonopoly;

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
 * @version 0.2.0
 */
public class MonopolyPage extends BaseGamePage {
  private BoardGame boardGameForMonopoly;
  private final BorderPane mainLayout;
  private Label gameInformation;

  private static final String BOARD_FILE_PATH =
      "src/main/resources/board/monopolyBoard.json";
  private static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";

  public MonopolyPage(ControllerMonopoly controllerMonopoly) {
    initializeGameMPY();
    GridPane board = createBoard();
    HBox controlPanel = createControlPanel();
    mainLayout = new BorderPane();
    mainLayout.setTop(createMenuBar());
    mainLayout.setCenter(board);
    mainLayout.setBottom(createControlPanel());

    BorderPane.setAlignment(controlPanel, Pos.CENTER);
    setAlignment(Pos.CENTER);

    getChildren().add(mainLayout);
  }

  private void initializeGameMPY() {
    boardGameForMonopoly = initializeBoardGame(BOARD_FILE_PATH, PLAYER_FILE_PATH);
  }

  private GridPane createBoard() {
    return createBoard(boardGameForMonopoly);
  }

  private HBox createControlPanel() {
    HBox controlPanel = new HBox();
    controlPanel.setAlignment(Pos.CENTER);
    controlPanel.setSpacing(10);

    Button startGameButton = new Button("Start Game");
    startGameButton.setOnAction(event -> {
      initializeGameMPY();
      updateBoard(mainLayout, boardGameForMonopoly);
    });

    gameInformation = new Label("Last rolled: ---");
    Label playerInformation = new Label(displayPlayers(boardGameForMonopoly));


    controlPanel.getChildren().addAll(startGameButton, gameInformation, playerInformation);
    return controlPanel;
  }


}

