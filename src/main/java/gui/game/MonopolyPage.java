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
 * @version 0.4.1
 */
public class MonopolyPage extends BaseGamePage {
  private BoardGame boardGameForMonopoly;
  private final BorderPane mainLayout;
  private Label gameInformation;

  private static final String BOARD_FILE_PATH =
      "src/main/resources/board/monopolyBoard.json";
  private static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";
  //TODO create constants class for the file paths

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

  /**
   * Initializes the Monopoly game by loading the board configuration and player data
   * from the specified file paths.
   *
   * <p>This method sets up the `boardGameForMonopoly` object by reading the board
   * layout from a JSON file and player information from a CSV file.</p>
   */
  private void initializeGameMPY() {
    boardGameForMonopoly = initializeBoardGame(BOARD_FILE_PATH, PLAYER_FILE_PATH);
  }

  /**
   * Creates the game board for Monopoly.
   *
   * <p>This method generates a grid layout representing the Monopoly board.
   * It places tiles in a clockwise manner starting from the top-left corner,
   * ensuring that all 40 tiles are added to the grid in the correct order.</p>
   *
   * @return A `GridPane` containing the Monopoly board layout.
   */
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

  /**
   * Creates the control panel for the game.
   *
   * <p>This method creates a horizontal box layout containing buttons and labels
   * for controlling the game. It includes a button to start the game and a label
   * to display game information.</p>
   *
   * @return A HBox containing the control panel elements.
   */
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


    controlPanel.getChildren().addAll(startGameButton, getButton(), gameInformation, playerInformation);
    return controlPanel;
  }

  /**
   * Updates the board display by creating a new board grid and replacing the old one.
   */
  private void updateBoard() {
    GridPane boardGrid = createBoard();
    mainLayout.setCenter(boardGrid);
  }

  /**
   * Creates a tile for the Monopoly board.
   *
   * <p>This method generates a StackPane containing a rectangle representing the tile,
   * along with a text label displaying the tile ID. It also handles placing player pieces
   * on the tile if they are present.</p>
   *
   * @param tileId The ID of the tile to be created.
   * @return A StackPane containing the tile representation.
   */
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

  /**
   * Creates a button to roll the dice.
   *
   * <p>This method creates a button that, when clicked, rolls the dice and updates
   * the game information label with the result.</p>
   *
   * @return A Button for rolling the dice.
   */
  private Button getButton() {
    Button rollDice = new Button("Roll Dice");
    rollDice.setOnAction(e -> {
      playGame(boardGameForMonopoly, gameInformation);
      updateBoard();
    });
    return rollDice;
  }

  /**
   * Places player pieces on the specified tile.
   *
   * <p>This method checks if any players are currently on the specified tile and
   * adds their corresponding game pieces to the StackPane representing that tile.</p>
   *
   * @param tileId The ID of the tile where players should be placed.
   * @param stack  The StackPane representing the tile.
   */
  private void placePlayerOnTile(int tileId, StackPane stack) {
    boardGameForMonopoly.getPlayers().stream()
        .filter(player -> player.getCurrentTile().getTileId() == tileId)
        .forEach(player -> {
          Circle playerCircle = createPlayer(player.getColor());
          stack.getChildren().add(playerCircle);
        });
  }


}

