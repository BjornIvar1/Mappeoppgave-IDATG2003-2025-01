package gui.game;


import engine.BoardGame;
import gui.BaseGamePage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import kontroller.ControllerMonopoly;
import model.tileactions.TileAction;

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
 * @version 0.3.0
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
    GridPane grid = new GridPane();
    int gridSize = 11; // Size of the grid (11x11 for Monopoly)
    int tileId = 1; // Start with tile ID 1

    // Place tiles on the top.
    for (int x = 0; x < gridSize; x++) {
      if (tileId <= 40) {
        StackPane tile = createTile(tileId++);
        grid.add(tile, x, 0);
      }
    }

    // Place tiles on the right side.
    for (int y = 1; y < gridSize; y++) {
      if (tileId <= 40) {
        StackPane tile = createTile(tileId++);
        grid.add(tile, gridSize - 1, y);
      }
    }

    // Place tiles on the bottom.
    for (int x = gridSize - 2; x >= 0; x--) {
      if (tileId <= 40) {
        StackPane tile = createTile(tileId++);
        grid.add(tile, x, gridSize - 1);
      }
    }

    // Place tiles along the left right side.
    for (int y = gridSize - 2; y > 0; y--) {
      if (tileId <= 40) {
        StackPane tile = createTile(tileId++);
        grid.add(tile, 0, y);
      }
    }

    return grid;
  }

  private HBox createControlPanel() {
    HBox controlPanel = new HBox();
    controlPanel.setAlignment(Pos.CENTER);
    controlPanel.setSpacing(10);

    Button startGameButton = new Button("Start Game");
    startGameButton.setOnAction(event -> {
      initializeGameMPY();
      updateBoard();
    });

    gameInformation = new Label("Last rolled: ---");
    Label playerInformation = new Label(displayPlayers(boardGameForMonopoly));


    controlPanel.getChildren().addAll(startGameButton, gameInformation, playerInformation);
    return controlPanel;
  }

  /**
   * Updates the board display by creating a new board grid and replacing the old one.
   */
  private void updateBoard() {
    GridPane boardGrid = createBoard();
    mainLayout.setCenter(boardGrid);
  }

  private StackPane createTile(int tileId) {
    StackPane stack = new StackPane();
    Rectangle rect = new Rectangle(60, 60);
    Color baseColor = getColor(tileId);

    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));


    TileAction landAction = boardGameForMonopoly.getBoard().getTiles().get(tileId).getLandAction();
    if (landAction != null) {
      rect.setFill(landAction.getColor());
      text.setWrappingWidth(60);
      text.setText(landAction.getDescription());
    }

    stack.getChildren().addAll(rect, text);
    placePlayerOnTile(tileId, stack);

    return stack;
  }

  private void placePlayerOnTile(int tileId, StackPane stack) {
    boardGameForMonopoly.getPlayers().stream()
        .filter(player -> player.getCurrentTile().getTileId() == tileId)
        .forEach(player -> {
          Circle playerCircle = createPlayer(player.getColor());
          stack.getChildren().add(playerCircle);
        });
  }


}

