package edu.ntnu.idi.bidata.ui.gui.game;

import edu.ntnu.idi.bidata.model.engine.Dice;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.model.tileactions.TileAction;
import edu.ntnu.idi.bidata.observer.BoardGameObserver;
import edu.ntnu.idi.bidata.ui.controller.ControllerMonopoly;
import edu.ntnu.idi.bidata.ui.factory.ButtonFactory;
import edu.ntnu.idi.bidata.ui.gui.base.BaseGamePage;
import edu.ntnu.idi.bidata.utils.Constants;
import edu.ntnu.idi.bidata.utils.MessageDisplay;
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
 * Represents the Monopoly game page in the GUI.
 *
 * <p>This class is responsible for displaying the game board and handling user interactions.</p>
 *
 * <p>The user is introduced to three buttons, a control panel and the board game.</p>
 * <ul>
 *   <li>Restart Game: Initializes a new game</li>
 *   <li>Roll Dice: Plays the game by rolling the dice</li>
 *   <li>Control panel: Lets the user know who has rolled and the score.</li>
 *   <li>Board: Displays the game board with player pieces and different tiles</li>
 *   <li>Game Rules: Displays the rules of the game</li>
 *   <li>Save game: Allows the user to save the game</li>
 *   <li>Return: Returns to the game selection menu</li>
 * </ul>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.10.2
 * @since 0.0.1
 */
public class MonopolyPage extends BaseGamePage implements BoardGameObserver {
  private final ControllerMonopoly controller;
  private BorderPane mainLayout;
  private Label gameInformation;
  private Button restartGameButton;
  private Button rollDiceButton;
  private ImageView imageDice1;
  private ImageView imageDice2;
  private ImageView imagePlayerInJail;

  /**
   * Constructor for the MonopolyPage class.
   *
   * <p>This class sets up the Monopoly game page, including the game board,
   * control panel, and buttons for user interaction.</p>
   *
   * @param controller the controller for the Monopoly game.
   */
  public MonopolyPage(ControllerMonopoly controller) {
    this.controller = controller;
    controller.initializeMonopoly();
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
    mainLayout.setPadding(new Insets(10));
    mainLayout.setMinHeight(600); // Set minimum height for the layout
    mainLayout.setPrefHeight(800); // Set preferred height for the layout
    return mainLayout;
  }

  /**
   * Creates the top bar of the game page.
   *
   * <p>This method creates a horizontal box layout containing buttons for returning
   * to the game selection menu and displaying game rules.</p>
   *
   * @return A HBox containing the top bar elements.
   */
  private HBox createTopHbox() {
    Button returnButton = createReturnButton();
    Button gameRulesButton = createGameRulesButton();
    return createTopBar(returnButton, gameRulesButton);
  }

  /**
   * Creates the control panel with buttons to play the game and game information.
   *
   * <p>The control panel includes:</p>
   * <ul>
   *   <li>Restart Game: Initializes a new game</li>
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

    Label playerInformation = new Label(displayPlayerInfoMonopoly(
        controller.getGame()));

    Button rollDice = getRollDiceButton(playerInformation);
    Button restartGame = getResetGameButton(playerInformation);
    Button saveGame = getSaveGameButton();

    VBox gameInfoBox = createGameInfoBox();
    gameInfoBox.setStyle(Constants.GAME_INFO_BOX_STYLE);
    playerInformation.setStyle(Constants.PLAYER_INFO_BOX_STYLE);


    controlPanel.getChildren().addAll(restartGame, rollDice,
        gameInfoBox, playerInformation, saveGame);
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
  private Button getRollDiceButton(Label playerInformation) {
    rollDiceButton = new Button(Constants.LABEL_ROLL_DICE_BUTTON);
    rollDiceButton.setStyle(Constants.ROLL_DICE_RESET_GAME_BUTTON_STYLE);
    rollDiceButton.setDisable(false);
    rollDiceButton.setOnAction(event -> {
      controller.rollDice();
      playerInformation.setText(displayPlayerInfoMonopoly(controller.getGame()));
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
      controller.saveGame();
      setAlertConfirmation(Constants.GAME_SAVED_SUCCESS_MESSAGE);
    });
    return saveGame;
  }

  /**
   * Creates the button to start the game.
   *
   * <p>Uses the controller to initialize a new game</p>
   *
   * @param playerInformation The label to display player information.
   * @return Button to start the game.
   */
  private Button getResetGameButton(Label playerInformation) {
    restartGameButton = new Button(Constants.LABEL_RESTART_GAME_BUTTON);
    restartGameButton.setStyle(Constants.ROLL_DICE_RESET_GAME_BUTTON_STYLE);
    restartGameButton.setDisable(true);
    restartGameButton.setOnAction(event -> {
      controller.resetGame();
      controller.getGame().addObserver(this);
      updateBoard();
      rollDiceButton.setDisable(false);
      restartGameButton.setDisable(true);
      playerInformation.setText(displayPlayerInfoMonopoly(controller.getGame()));
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
    return ButtonFactory.returnButtonFactory(
        controller::switchToGameSelection);
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
        Constants.GAME_MONOPOLY_HEADER,
        Constants.GAME_RULES,
        Constants.MONOPOLY_RULES);
  }

  /**
   * Creates a VBox containing game information.
   *
   * <p>The VBox contains:
   *   <ul>
   *     <li>A label displaying who rolled th dice</li>
   *     <li>Two images representing the rolled dice</li>
   *     <li>An image representing a player in jail</li>
   *   </ul>
   * </p>
   *
   * @return VBox containing game information.
   */
  private VBox createGameInfoBox() {
    imageDice1 = createImage(Constants.getImage(Constants.DICE_ONE_IMAGE_FILE_PATH), 35, 35, true);
    imageDice2 = createImage(Constants.getImage(Constants.DICE_ONE_IMAGE_FILE_PATH), 35, 35, true);
    imagePlayerInJail = createImage(Constants.getImage(
        Constants.PLAYER_IN_JAIL_FILE_PATH), 50, 50, false);

    gameInformation = new Label();

    return getVbox(gameInformation, imageDice1, imageDice2, imagePlayerInJail);
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
    GridPane grid = new GridPane(); // Recommended by GitHub Copilot v.1.5.45-241
    grid.setAlignment(Pos.CENTER);
    grid.setScaleX(0.8); // Set a scale for the horizontal axis
    grid.setScaleY(0.8); // Set a scale for the vertical axis
    int gridSize = 11; // Size of the grid (11x11 for Monopoly)
    int firstTileId = 1; // Start with tile ID 1

    //First row from left to right. Tile from 1-10.
    for (int xcoordinateRow = gridSize - 2; xcoordinateRow >= 0; xcoordinateRow--) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, xcoordinateRow, gridSize - 1);
      }
    }

    //First column from bottom to top. Tile from 11-19.
    for (int ycoordinateColumn = gridSize - 2; ycoordinateColumn >= 0; ycoordinateColumn--) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, 0, ycoordinateColumn);
      }
    }

    //Second row from right to left. Tile from 20-30.
    for (int xcoordinateRow = 1; xcoordinateRow < gridSize - 1; xcoordinateRow++) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, xcoordinateRow, 0);
      }
    }

    //Second column from top to bottom. Tile from 31-40.
    for (int ycoordinateColumn = 0; ycoordinateColumn < gridSize; ycoordinateColumn++) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, gridSize - 1, ycoordinateColumn);
      }
    }
    return grid;
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
    Rectangle rect = new Rectangle(Constants.TILE_SIZE, Constants.TILE_SIZE);
    Color baseColor = getColor(tileId);
    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    TileAction landAction = controller.getTileAction(tileId);
    if (landAction != null) {
      rect.setFill(landAction.getColor());
      text.setWrappingWidth(60);
      text.setText(landAction.getDescription());
    }

    StackPane stack = new StackPane();
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
    Iterator<Player> playerIterator = controller.getPlayersIterator();
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next();
      if (player.getCurrentTile().getTileId() == tileId) {
        Circle circle = createPlayer(player.getColor());
        stack.getChildren().add(circle);
      }
    }
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
        imagePlayerInJail,
        name, dice);
  }

  /**
   * A notification method that is called when a player is in skip.
   *
   * <p>This method updates the game information label
   * to display the player's skip status, and updates the image displaying the player in jail.</p>
   *
   * @param name the name of the player.
   * @param isInJail true if the player is in skip, false otherwise.
   */
  @Override
  public void observerIsPlayerSkipped(String name, boolean isInJail) {
    Platform.runLater(() -> { // Update the UI on the JavaFX Application Thread
      // This is necessary to ensure thread safety when updating the UI
      // https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
      if (isInJail) { // Check if the player is in skip and update the game information label
        gameInformation.setText(MessageDisplay.playerInJailMessage(name));
        updatePlayerSkipped(imageDice1,
            imageDice2,
            imagePlayerInJail);
      }
    });
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
  public void observerPlayerWonInMonopoly(String name, boolean winner) {
    if (winner) {
      Platform.runLater(() -> gameInformation.setText(MessageDisplay.winningMessage(name)));
      rollDiceButton.setDisable(true);
      restartGameButton.setDisable(false);
    }
  }

  @Override
  public void observerPlayerWonInSnakesAndLadders(String name, boolean winner) {
    // This method is not used in Monopoly, but it is required by the interface.
  }
}

