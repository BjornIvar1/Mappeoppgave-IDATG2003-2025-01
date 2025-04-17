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
import model.PlayerInMonopoly;
import model.tileactions.TileAction;
import utils.MessageDisplay;

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
 * @version 0.6.0
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

  /**
   * Constructor for the MonopolyPage class.
   *
   * @param controllerMonopoly the controller for the Monopoly game.
   */
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
   * It creates tiles for each position on the board and places player pieces
   * on the corresponding tiles. The player goes around board in a clockwise
   * manner.</p>
   *
   * @return grid containing the Monopoly board layout.
   */
  private GridPane createBoard() {
    GridPane grid = new GridPane();
    int gridSize = 11; // Size of the grid (11x11 for Monopoly)
    int firstTileId = 1; // Start with tile ID 1

    //First row from left to right. Tile from 1-10.
    for (int xCoordinateRow = gridSize - 2; xCoordinateRow >= 0; xCoordinateRow--) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, xCoordinateRow, gridSize - 1);
      }
    }

    //First column from bottom to top. Tile from 11-19.
    for (int yCoordinateColumn = gridSize - 2; yCoordinateColumn >= 0; yCoordinateColumn--) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, 0, yCoordinateColumn);
      }
    }

    //Second row from right to left. Tile from 20-30.
    for (int xCoordinateRow = 1; xCoordinateRow < gridSize - 1; xCoordinateRow++) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, xCoordinateRow, 0);
      }
    }

    //Second column from top to bottom. Tile from 31-40.
    for (int yCoordinateColumn = 0; yCoordinateColumn < gridSize; yCoordinateColumn++) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, gridSize - 1, yCoordinateColumn);
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
    gameInformation = new Label("Last rolled: ---");
    Label playerInformation = new Label(displayPlayerInfoMonopoly(boardGameForMonopoly));

    controlPanel.setAlignment(Pos.CENTER);
    controlPanel.setSpacing(10);

    Button rollDice = rollDiceButton(playerInformation);
    Button startGameButton = getStartGameButton(rollDice, playerInformation);


    controlPanel.getChildren().addAll(startGameButton, rollDice, gameInformation, playerInformation);
    return controlPanel;
  }

  /**
   * Creates the button to start the game.
   *
   * <p>This method initializes the game and enables the roll dice button.</p>
   *
   * @param rollDice          The button to roll the dice.
   * @param playerInformation The label to display player information.
   * @return Button to start the game.
   */
  private Button getStartGameButton(Button rollDice, Label playerInformation) {
    Button startGameButton = new Button("Start Game");
    startGameButton.setOnAction(event -> {
      initializeGameMPY();
      updateBoard();
      rollDice.setDisable(false);
      playerInformation.setText(displayPlayerInfoMonopoly(boardGameForMonopoly));
    });
    return startGameButton;
  }

  /**
   * Creates the button to roll the dice and play the game.
   *
   * <p>This method handles the action of rolling the dice and updating the game information.</p>
   *
   * @param playerInformation The label to display player information.
   * @return Button to roll the dice.
   */
  private Button rollDiceButton(Label playerInformation) {
    Button rollDice = new Button("Roll Dice");
    rollDice.setOnAction(e -> {
      boardGameForMonopoly.play();
      PlayerInMonopoly player = boardGameForMonopoly.getCurrentPlayer();
      int rollSum = boardGameForMonopoly.getDice().getDie(0) + boardGameForMonopoly.getDice().getDie(1);

      if (player.getBalance() >= 5000000) { // Winning condition
        gameInformation.setText("Winner: " + player.getName() + "\n" + "Press Start Game to play again");
        rollDice.setDisable(true);
      } else {
        gameInformation.setText(MessageDisplay.rollDiceMessage(player, rollSum));
      }
      playerInformation.setText(displayPlayerInfoMonopoly(boardGameForMonopoly));
      updateBoard();
    });
    return rollDice;
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

