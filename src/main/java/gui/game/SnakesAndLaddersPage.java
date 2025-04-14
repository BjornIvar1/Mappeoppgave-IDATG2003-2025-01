package gui.game;

import controller.ControllerSnakesAndLadders;
import engine.BoardGame;
import gui.BaseGamePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Player;
import model.tileactions.TileAction;

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
  private static final int TILE_SIZE = 60;
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

    Button rollDice = getRollDice();

    gameInformation = new Label("Last rolled: ---");
    Label playerInformation = new Label(displayPlayers(boardGameSnakesAndL));

    controlPanel.getChildren().addAll(startButton, rollDice, gameInformation, playerInformation);
    return controlPanel;
  }

  /**
   * Creates the button to roll the dice and play the game.
   *
   * @return Button to roll the dice.
   */
  private Button getRollDice() {
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
   * Creates the board grid for the Snakes and Ladders game.
   *
   * @return GridPane representing the game board.
   */
  private GridPane createBoard() {
    GridPane grid = new GridPane();
    int tileNumber = 90;

    for (int y = 0; y < boardGameSnakesAndL.getBoard().getRows(); y++) {
      if (y % 2 != 0) {
        for (int x = 0; x < boardGameSnakesAndL.getBoard().getColumns(); x++) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      } else {
        for (int x = boardGameSnakesAndL.getBoard().getColumns() - 1; x >= 0; x--) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      }
    }

    return grid;
  }

  /**
   * Creates a tile for the game board.
   *
   * @param tileId the ID of the tile.
   * @return StackPane representing the tile.
   */
  private StackPane createTile(int tileId) {
    StackPane stack = new StackPane();
    Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
    Color baseColor = (tileId % 2 == 0) ? Color.web("#32bff5") : Color.web("#bbd962");

    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    TileAction landAction = boardGameSnakesAndL.getBoard().getTiles().get(tileId).getLandAction();
    if (landAction != null) {
      rect.setFill(landAction.getColor());
      text.setWrappingWidth(TILE_SIZE);
      text.setText(landAction.getDescription());
    }

    stack.getChildren().addAll(rect, text);
    placePlayerOnTile(tileId, stack);

    return stack;
  }

  /**
   * Places the player on the specified tile.
   *
   * @param tileId the ID of the tile
   * @param stack the StackPane representing the tile
   */
  private void placePlayerOnTile(int tileId, StackPane stack) {
    boardGameSnakesAndL.getPlayers().stream()
        .filter(player -> player.getCurrentTile().getTileId() == tileId)
        .forEach(player -> {
          Circle playerCircle = createPlayer(player.getColor());
          stack.getChildren().add(playerCircle);
        });
  }
}
