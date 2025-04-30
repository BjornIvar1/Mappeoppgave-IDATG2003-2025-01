package gui.game;

import controller.ControllerSnakesAndLadders;
import engine.BoardGame;
import gui.BaseGamePage;
import gui.factory.ButtonFactory;
import java.util.logging.Logger;
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
import model.exception.TileNotFoundException;
import model.tileactions.TileAction;
import utils.Constants;
import utils.MessageDisplay;

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
 * @version 0.3.1
 */
public class SnakesAndLaddersPage extends BaseGamePage {
  private BoardGame boardGameSnakesAndL;
  private BorderPane mainLayout;
  private Label gameInformation;
  private final ControllerSnakesAndLadders controllerSnakesAndLadders;

  /**
   * Constructor for the SnakesAndLaddersPage class.
   *
   * @param controllerSnakesAndLadders the controller for the Snakes and Ladders game.
   */
  public SnakesAndLaddersPage(ControllerSnakesAndLadders controllerSnakesAndLadders) {
    this.controllerSnakesAndLadders = controllerSnakesAndLadders;
    initializeGame();
    GridPane board = createBoard();
    HBox controlPanel = createControlPanel();

    mainLayout = getBorderPane(board, controlPanel);

    BorderPane.setAlignment(board, Pos.CENTER);
    BorderPane.setAlignment(controlPanel, Pos.CENTER);

    setPageContent(mainLayout);
  }

  /**
   * Creates a BoardPane with the main layout.
   *
   * <p>A BoarderPane with the button the top,
   * the board in the center and the control panel in
   * the bottom.</p>
   *
   * @param board the snakes and ladder Board.
   * @param controlPanel the control panel.
   * @return main layout of the page.
   */
  private BorderPane getBorderPane(GridPane board, HBox controlPanel) {
    mainLayout = new BorderPane();
    mainLayout.setTop(createReturnButton());
    mainLayout.setCenter(board);
    mainLayout.setBottom(controlPanel);
    return mainLayout;
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


    Button rollDice = getRollDice();
    Button startButton = getStartGameButton(rollDice);

    gameInformation = new Label(Constants.LABEL_LAST_ROLLED_BUTTON);
    Label playerInformation = new Label(displayPlayers(boardGameSnakesAndL));

    controlPanel.getChildren().addAll(startButton, rollDice, gameInformation, playerInformation);
    return controlPanel;
  }

  /**
   * Creates the button to start the game.
   *
   * @return Button to start the game.
   */
  private Button getStartGameButton(Button rollDice) {
    Button startButton = new Button(Constants.LABEL_START_GAME_BUTTON);
    startButton.setDisable(true);
    startButton.setOnAction(e -> {
      initializeGame();
      updateBoard(mainLayout);
      rollDice.setDisable(false);
      startButton.setDisable(true);
    });
    return startButton;
  }

  /**
   * Creates the button to roll the dice and play the game.
   *
   * @return Button to roll the dice.
   */
  private Button getRollDice() {
    Button rollDice = new Button(Constants.LABEL_ROLL_DICE_BUTTON);
    rollDice.setOnAction(e -> {
      try {
        boardGameSnakesAndL.play();
      } catch (TileNotFoundException ex) {
        Logger.getLogger(SnakesAndLaddersPage.class.getName())
            .warning("Tile not found: " + ex.getMessage());
      }
      Player player = boardGameSnakesAndL.getCurrentPlayer();
      int rollSum = boardGameSnakesAndL.getDice()
          .getDie(0) + boardGameSnakesAndL.getDice()
          .getDie(1);

      if (boardGameSnakesAndL.getCurrentPlayer().getCurrentTile().getTileId() == 90) {
        gameInformation.setText(MessageDisplay.winningMessage(player));
        Button startGameButton = (Button)
            ((HBox) rollDice.getParent())
                .getChildren().getFirst(); //code from GitHub Copilot.
        startGameButton.setDisable(false);
        rollDice.setDisable(true);
      } else {
        gameInformation.setText(MessageDisplay.rollDiceMessage(player, rollSum));
      }
      updateBoard(mainLayout);
    });
    return rollDice;
  }

  /**
   * Initializes the game by creating a new BoardGame,
   * and creating a board, dice and adding the players.
   */
  private void initializeGame() {
    boardGameSnakesAndL = initializeBoardGame(Constants
            .SNAKES_AND_LADDERS_BOARD_FILE_PATH,
            Constants.PLAYER_FILE_PATH);
  }

  private void updateBoard(BorderPane mainLayout) {
    GridPane boardGrid = createBoard();
    mainLayout.setCenter(boardGrid);
  }

  /**
   * Creates the board grid for the Snakes and Ladders game.
   *
   * @return GridPane representing the game board.
   */
  private GridPane createBoard() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
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
    final StackPane stack = new StackPane();
    Rectangle rect = new Rectangle(Constants
        .SNAKES_AND_LADDERS_TILE_SIZE,
        Constants.SNAKES_AND_LADDERS_TILE_SIZE);
    Color baseColor = (tileId % 2 == 0) ? Color.web(Constants.COLOR_TILE_EVEN)
        : Color.web(Constants.COLOR_TILE_ODD);

    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    TileAction landAction = boardGameSnakesAndL.getBoard().getTiles().get(tileId).getLandAction();
    if (landAction != null) {
      rect.setFill(landAction.getColor());
      text.setWrappingWidth(Constants.SNAKES_AND_LADDERS_TILE_SIZE);
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

  private Button createReturnButton() {
    return ButtonFactory.returnButtonFactory("back",
        controllerSnakesAndLadders::switchToGameSelection);
  }
}
