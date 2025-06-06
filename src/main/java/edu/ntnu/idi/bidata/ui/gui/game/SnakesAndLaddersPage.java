package edu.ntnu.idi.bidata.ui.gui.game;

import edu.ntnu.idi.bidata.model.engine.Dice;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.tileactions.TileAction;
import edu.ntnu.idi.bidata.observer.BoardGameObserver;
import edu.ntnu.idi.bidata.ui.controller.ControllerSnakesAndLadders;
import edu.ntnu.idi.bidata.ui.factory.ButtonFactory;
import edu.ntnu.idi.bidata.ui.gui.base.BaseGamePage;
import edu.ntnu.idi.bidata.utils.Constants;
import edu.ntnu.idi.bidata.utils.MessageDisplay;
import java.io.IOException;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;



/**
 * Represents the Snakes and Ladders game page in the GUI.
 *
 * <p>This class is responsible for displaying the game board and handling user interactions,
 * such as rolling dice, saving a game, or restarting the game.</p>
 *
 * <ul>
 *   <li>Restart Game: Initializes a new game</li>
 *   <li>Control panel: Displays the three buttons the user can interact with,
 *   and information about the game</li>
 *   <li>Board: Displays the game board with player pieces</li>
 *   <li>Return: Returns back to the {@link edu.ntnu.idi.bidata.ui.gui.menu.GameSelection}/li>
 * </ul>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.6.0
 */
public class SnakesAndLaddersPage extends BaseGamePage implements BoardGameObserver {
  private final ControllerSnakesAndLadders controller;
  private BorderPane mainLayout;
  private Label gameInformation;
  private ImageView imageDice1;
  private ImageView imageDice2;
  private ImageView imagePlayerSkipped;
  private Button restartGameButton;
  private Button rollDiceButton;

  /**
   * Constructor for the SnakesAndLaddersPage class.
   *
   * @param controller the {@link ControllerSnakesAndLadders} which manges the game logic.
   */
  public SnakesAndLaddersPage(ControllerSnakesAndLadders controller) {
    this.controller = controller;
    controller.initializeGame();
    controller.getGame().addObserver(this);
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
   *   <li>Top: Contains a button to return and display the game rules</li>
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
   *   <li>Reset Game: Initializes a new game</li>
   *   <li>Roll Dice: Rolls the dice and updates the game state</li>
   *   <li>Game Info Box: A VBox displaying the last dice roll result and related imagery.</li>
   *   <li>Player Information: Displays the information of the players</li>
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
    controlPanel.setStyle(Constants.CONTROL_PANEL_STYLE);

    rollDiceButton = getRollDiceButton();
    restartGameButton = getResetGameButton();
    Button saveGame = getSaveGameButton();

    Label playerInformation = new Label(displayPlayers(controller.getGame()));
    VBox gameInfoBox = createGameInfoBox();
    gameInfoBox.setStyle(Constants.GAME_INFO_BOX_STYLE);
    playerInformation.setStyle(Constants.PLAYER_INFO_BOX_STYLE);

    controlPanel.getChildren().addAll(
        restartGameButton,
        rollDiceButton,
        gameInfoBox,
        playerInformation,
        saveGame
    );
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
    rollDiceButton.setStyle(Constants.ROLL_DICE_RESET_GAME_BUTTON_STYLE);
    rollDiceButton.setDisable(false);
    rollDiceButton.setOnAction(e -> {
      controller.rollDice();
      updateBoard();
    });
    return rollDiceButton;
  }

  /**
   * Creates the button to save the game.
   *
   * <p>Uses the controller to save the current game to a file.
   * Alerts the user if the save worked as intended or if it did not work.</p>
   *
   * @return Button to save the game.
   */
  private Button getSaveGameButton() {
    Button saveGame = new Button(Constants.LABEL_SAVE_GAME_BUTTON);
    saveGame.setStyle(Constants.SAVE_GAME_BUTTON_STYLE);
    saveGame.setOnAction(e -> {
      try {
        controller.saveGame();
        setAlertConfirmation(Constants.GAME_SAVED_SUCCESS_MESSAGE);
      } catch (IOException ex) {
        setAlertWarning(ex.getMessage());
      }
    });
    return saveGame;
  }

  /**
   * Creates the button to restarts the game.
   *
   * <p>Uses the controller to initialize a new game</p>
   *
   * @return Button to restart the game.
   */
  private Button getResetGameButton() {
    restartGameButton = new Button(Constants.LABEL_RESTART_GAME_BUTTON);
    restartGameButton.setStyle(Constants.ROLL_DICE_RESET_GAME_BUTTON_STYLE);
    restartGameButton.setDisable(true);
    restartGameButton.setOnAction(e -> {
      controller.resetGame();
      controller.getGame().addObserver(this);
      updateBoard();
      rollDiceButton.setDisable(false);
      restartGameButton.setDisable(true);
    });
    return restartGameButton;
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

  private VBox createGameInfoBox() {
    imageDice1 = createImage(Constants.getImage(Constants.DICE_ONE_IMAGE_FILE_PATH), 35, 35, true);
    imageDice2 = createImage(Constants.getImage(Constants.DICE_ONE_IMAGE_FILE_PATH), 35, 35, true);
    imagePlayerSkipped = createImage(Constants.getImage(
        Constants.PLAYER_SKIPPED_FILE_PATH), 50, 50, false);

    gameInformation = new Label();

    return getVbox(gameInformation, imageDice1, imageDice2, imagePlayerSkipped);
  }

  /**
   * A notification method that is called when a player moves.
   *
   * <p>This method updates the game information label
   * to display the rolled value, and updates the image to display the dice rolled.</p>
   *
   * @param name the name of the player.
   * @param dice the rolled dice.
   */
  @Override
  public void observerPlayerMoved(String name, Dice dice) {
    updateDiceAndPlayerInfo(gameInformation,
        imageDice1,
        imageDice2,
        imagePlayerSkipped,
        name, dice);
  }

  /**
   * A notification method that is called when a player is in skip.
   *
   * <p>This method updates the game information label
   * to display the player's skip status, and updates the image displaying the player in jail.</p>
   *
   * @param name the name of the player.
   * @param isSkipped true if the player is in skip, false otherwise.
   */
  @Override
  public void observerIsPlayerSkipped(String name, boolean isSkipped) {
    Platform.runLater(() -> { // Update the UI on the JavaFX Application Thread
      // This is necessary to ensure thread safety when updating the UI
      // https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
      if (isSkipped) { // Check if the player is in skip and update the game information label
        gameInformation.setText(MessageDisplay.playerSkippedMessage(name));
        updatePlayerSkipped(imageDice1,
            imageDice2,
            imagePlayerSkipped);
      }
    });
  }

  @Override
  public void observerPlayerWonInMonopoly(String name, boolean winner) {
    // This method is not used in Snakes and Ladders, but must be implemented.
  }

  /**
   * A notification method that is called when a player wins the game.
   *
   * <p>This method updates the game information
   * label to display the winning message. It also disables
   * {@code rollDiceButton} and enables {@code startGameButton}
   * to allow the user to start a new game.</p>
   *
   * @param name the name of the player who won.
   * @param winner the total steps the player moved to win.
   */
  @Override
  public void observerPlayerWonInSnakesAndLadders(String name, boolean winner) {
    if (winner) {
      Platform.runLater(() -> gameInformation.setText(MessageDisplay.winningMessage(name)));
      rollDiceButton.setDisable(true);
      restartGameButton.setDisable(false);
    }
  }
}
