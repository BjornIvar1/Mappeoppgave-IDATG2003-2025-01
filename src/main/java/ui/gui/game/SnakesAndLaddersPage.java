package ui.gui.game;

import java.util.Iterator;
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
import observer.BoardGameObserver;
import ui.controller.ControllerSnakesAndLadders;
import ui.factory.ButtonFactory;
import ui.gui.BaseGamePage;
import utils.Constants;
import utils.MessageDisplay;

/**
 * Represents the Snakes and Ladders game page in the GUI.
 *
 * <p>This class is responsible for displaying the game board and handling user interactions,
 * such as rolling dice, saving a game, or restarting the game.</p>
 *
 * <ul>
 *   <li>Start Game: Initializes a new game</li>
 *   <li>Control panel: Displays the three buttons the user can interact with,
 *   and information about the game</li>
 *   <li>Board: Displays the game board with player pieces</li>
 *   <li>Return: Returns back to the {@link ui.gui.menu.GameSelection}/li>
 * </ul>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.5.1
 */
public class SnakesAndLaddersPage extends BaseGamePage implements BoardGameObserver {
  private final ControllerSnakesAndLadders controller;
  private BorderPane mainLayout;
  private Label gameInformation;
  private Button startGameButton;
  private Button rollDiceButton;

  /**
   * Constructor for the SnakesAndLaddersPage class.
   *
   * @param controller the {@link ControllerSnakesAndLadders} which manges the game logic.
   */
  public SnakesAndLaddersPage(ControllerSnakesAndLadders controller) {
    this.controller = controller;
    controller.initializeGame();
    GridPane board = createBoard();
    HBox controlPanel = createControlPanel();

    mainLayout = getBorderPane(board, controlPanel);

    BorderPane.setAlignment(board, Pos.CENTER);
    BorderPane.setAlignment(controlPanel, Pos.CENTER);

    setPageContent(mainLayout);
  }

  /**
   * Constructs the main layout of the Snakes and Ladders game page using {@link BorderPane}.
   *
   * <p>The layout is structured as follows:</p>
   * <ul>
   *   <li>Top: Contains the return button to go back to the game selection menu</li>
   *   <li>Center: Contains the game board</li>
   *   <li>Bottom: Contains the control panel with buttons and game information</li>
   *</ul>
   *
   * @param board the snakes and ladder Board.
   * @param controlPanel the control panel.
   * @return main layout of the page.
   */
  private BorderPane getBorderPane(GridPane board, HBox controlPanel) {
    mainLayout = new BorderPane();
    mainLayout.setTop(createTopHbox());
    mainLayout.setCenter(board);
    mainLayout.setBottom(controlPanel);
    return mainLayout;
  }

  /**
   * Creates the control panel with buttons to play the game and game information.
   *
   * <p>The control panel includes:</p>
   * <ul>
   *   <li>Start Game: Initializes a new game</li>
   *   <li>Roll Dice: Rolls the dice and updates the game state</li>
   *   <li>Game Information: Displays the last rolled dice and player information</li>
   *   <li>Save Game: Saves the current game state</li>
   * </ul>
   *
   * @return HBox containing the control panel with buttons.
   */
  private HBox createControlPanel() {
    HBox controlPanel = new HBox();
    controlPanel.setPadding(new Insets(10));
    controlPanel.setSpacing(10);
    controlPanel.setAlignment(Pos.CENTER);


    rollDiceButton = getRollDiceButton();
    Button saveGame = getSaveGameButton();
    startGameButton = getStartGameButton(rollDiceButton);

    gameInformation = new Label(Constants.LABEL_LAST_ROLLED_BUTTON);
    Label playerInformation = new Label(displayPlayers(controller.getGame()));

    controlPanel.getChildren().addAll(startGameButton,
        rollDiceButton,
        gameInformation,
        playerInformation,
        saveGame);
    return controlPanel;
  }

  /**
   * Creates the button to roll the dice and play the game.
   *
   * <p>Uses the controller to roll the dice and move the current player.
   * Also checks the winning condition and updates the UI accordingly</p>
   *
   * @return Button to roll the dice.
   */
  private Button getRollDiceButton() {
    rollDiceButton = new Button(Constants.LABEL_ROLL_DICE_BUTTON);
    rollDiceButton.setDisable(false);
    rollDiceButton.setOnAction(e -> {
      String playerName = controller.getGame().getCurrentPlayer().getName();
      controller.rollDice();

      if (controller.winningCondition()) {
        gameInformation.setText(MessageDisplay.winningMessage(playerName));
        rollDiceButton.setDisable(true);
        startGameButton.setDisable(false);
      } else {
        observerPlayerMoved(playerName, controller.getDieSum());
      }
      updateBoard();
    });
    return rollDiceButton;
  }

  /**
   * Creates the button to save the game.
   *
   * <p>Uses the controller to save the current game to a file</p>
   *
   * @return Button to save the game.
   */
  private Button getSaveGameButton() {
    Button saveGame = new Button(Constants.LABEL_SAVE_GAME_BUTTON);
    saveGame.setOnAction(e -> controller.saveGame());
    return saveGame;
  }

  /**
   * Creates the button to start the game.
   *
   * <p>Uses the controller to initialize a new game</p>
   *
   * @return Button to start the game.
   */
  private Button getStartGameButton(Button rollDice) {
    startGameButton = new Button(Constants.LABEL_START_GAME_BUTTON);
    startGameButton.setDisable(true);
    startGameButton.setOnAction(e -> {
      controller.initializeGame();
      updateBoard();
      rollDice.setDisable(false);
      startGameButton.setDisable(true);
    });
    return startGameButton;
  }

  /**
   * Creates a return button to go back to the game selection menu.
   *
   * <p>This button is used to navigate back to the game selection menu.
   * The button is created by the {@code ButtonFactory}. </p>
   *
   * @return Button to return to the game selection menu.
   */
  private Button createReturnButton() {
    return ButtonFactory.returnButtonFactory(controller::switchToGameSelection);
  }

  /**
   * Creates a button to display the game rules.
   *
   * <p>This button is used to show the game rules and information.
   * The button is created by the {@code ButtonFactory}. </p>
   *
   * @return Button to display the game rules.
   */
  private Button createGameRulesButton() {
    return ButtonFactory.informationButton(
        Constants.GAME_RULES,
        Constants.GAME_RULES,
        Constants.GAME_SNAKES_AND_LADDERS_HEADER,
        Constants.SNAKES_AND_LADDERS_RULES
    );
  }

  /**
   * Updates the game board in the main layout.
   *
   * <p>This method is called when the game state changes,
   * such as when a player rolls the dice or moves to a new tile.</p>
   */
  private void updateBoard() {
    GridPane boardGrid = createBoard();
    mainLayout.setCenter(boardGrid);
  }

  /**
   * Creates the top bar with buttons for returning to the game selection menu
   * and displaying game rules.
   *
   * @return HBox containing the top bar with buttons.
   */
  private HBox createTopHbox() {
    Button returnButton = createReturnButton();
    Button gameRulesButton = createGameRulesButton();
    return createTopBar(returnButton, gameRulesButton);
  }

  /**
   * Creates and returns the board grid for the Snakes and Ladders game.
   *
   * <p>Each tile displays its ID and action type, and shows any player currently on it.</p>
   *
   * @return the {@code GridPane} representing the game board.
   */
  private GridPane createBoard() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    int tileNumber = controller.getTotalTiles();

    for (int y = 0; y < controller.getRows(); y++) {
      if (y % 2 != 0) {
        for (int x = 0; x < controller.getColumns(); x++) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      } else {
        for (int x = controller.getColumns() - 1; x >= 0; x--) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      }
    }

    return grid;
  }

  /**
   * Creates and return a single tile for the board.
   *
   *<p>Each tile is represented by a {@code StackPane} containing a rectangle,
   * and information about the tile.</p>
   *
   * @param tileId the ID of the tile.
   * @return a {@code StackPane} representing the tile.
   */
  private StackPane createTile(int tileId) {
    final StackPane stack = new StackPane();
    Rectangle rect = new Rectangle(Constants
        .TILE_SIZE,
        Constants.TILE_SIZE);
    Color baseColor = (tileId % 2 == 0) ? Color.web(Constants.COLOR_TILE_EVEN)
        : Color.web(Constants.COLOR_TILE_ODD);

    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    TileAction landAction = controller.getTileAction(tileId);
    if (landAction != null) {
      rect.setFill(landAction.getColor());
      text.setWrappingWidth(Constants.TILE_SIZE);
      text.setText(landAction.getDescription());
    }

    stack.getChildren().addAll(rect, text);
    placePlayerOnTile(tileId, stack);

    return stack;
  }

  /**
   * Places a circle on a specified tile representing the player.
   *
   * @param tileId the ID of the tile
   * @param stack the {@code StackPane} representing the tile
   */
  private void placePlayerOnTile(int tileId, StackPane stack) {
    Iterator<Player> playerIterator = controller.getGame()
        .getPlayerIterator();
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next();
      if (player.getCurrentTile().getTileId() == tileId) {
        Circle circle = createPlayer(player.getColor());
        stack.getChildren().add(circle);
      }
    }
  }

  @Override
  public void observerPlayerMoved(String name, int rolledSum) {
    gameInformation.setText(MessageDisplay.rollDiceMessage(name, rolledSum));
  }

  @Override
  public void observerIsPlayerInJail(String name, boolean isInJail) {
    // not required for this game
  }
}
