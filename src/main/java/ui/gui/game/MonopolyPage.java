package ui.gui.game;

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
import ui.controller.ControllerMonopoly;
import ui.factory.ButtonFactory;
import ui.gui.BaseGamePage;
import utils.Constants;
import java.util.Iterator;

/**
 * Represents the Monopoly game page in the GUI.
 *
 * <p>This class is responsible for displaying the game board and handling user interactions.</p>
 *
 * <p>The user is introduced to three buttons, a control panel and the board game.</p>
 * <ul>
 *   <li>Start Game: Initializes a new game</li>
 *   <li>Roll Dice: Plays the game by rolling the dice</li>
 *   <li>Control panel: Lets the user know who has rolled and the score.</li>
 *   <li>Board: Displays the game board with player pieces and different tiles</li>
 *   <li>Game Rules: Displays the rules of the game</li>
 *   <li>Save game: Allows the user to save the game</li>
 *   <li>Return: Returns to the game selection menu</li>
 * </ul>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.9.1
 * @since 0.0.1
 */
public class MonopolyPage extends BaseGamePage implements BoardGameObserver {
  private final BorderPane mainLayout;
  private Label gameInformation;
  private final ControllerMonopoly controllerMonopoly;
  private Button startGameButton;
  private Button rollDiceButton;
  /**
   * Constructor for the MonopolyPage class.
   *
   * <p>This class sets up the Monopoly game page, including the game board,
   * control panel, and buttons for user interaction.</p>
   *
   * @param controllerMonopoly the controller for the Monopoly game.
   */
  public MonopolyPage(ControllerMonopoly controllerMonopoly) {
    this.controllerMonopoly = controllerMonopoly;
    controllerMonopoly.initializeMonopoly();
    GridPane board = createBoard();
    HBox controlPanel = createControlPanel();
    mainLayout = new BorderPane();
    getMainLayout(board, controlPanel);

    BorderPane.setAlignment(board, Pos.CENTER);
    BorderPane.setAlignment(controlPanel, Pos.CENTER);
    setPageContent(mainLayout);
  }

  /**
   * Creates the main layout for the Monopoly game page.
   *
   * <p>This method sets up the main layout of the game page, including the board,
   * control panel, and return button.</p>
   *
   * @param board       The grid containing the Monopoly board.
   * @param controlPanel The horizontal box containing control buttons and labels.
   */
  private void getMainLayout(GridPane board, HBox controlPanel) {
    mainLayout.setTop(createTopHBox());
    mainLayout.setCenter(board);
    mainLayout.setBottom(controlPanel);
    mainLayout.setPadding(new Insets(10));
    mainLayout.setMinHeight(600); // Set minimum height for the layout
    mainLayout.setPrefHeight(800); // Set preferred height for the layout
  }

  /**
   * Creates the top bar of the game page.
   *
   * <p>This method creates a horizontal box layout containing buttons for returning
   * to the game selection menu and displaying game rules.</p>
   *
   * @return A HBox containing the top bar elements.
   */
  private HBox createTopHBox() {
    Button returnButton = createReturnButton();
    Button gameRulesButton = createGameRulesButton();
    return createTopBar(returnButton, gameRulesButton);
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
    grid.setAlignment(Pos.CENTER);
    grid.setScaleX(0.8); // Set a scale for the horizontal axis
    grid.setScaleY(0.8); // Set a scale for the vertical axis
    int gridSize = 11; // Size of the grid (11x11 for Monopoly)
    int firstTileId = 1; // Start with tile ID 1

    //First row from left to right. Tile from 1-10.
    for (int x_coordinateRow = gridSize - 2; x_coordinateRow >= 0; x_coordinateRow--) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, x_coordinateRow, gridSize - 1);
      }
    }

    //First column from bottom to top. Tile from 11-19.
    for (int y_coordinateColumn = gridSize - 2; y_coordinateColumn >= 0; y_coordinateColumn--) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, 0, y_coordinateColumn);
      }
    }

    //Second row from right to left. Tile from 20-30.
    for (int x_coordinateRow = 1; x_coordinateRow < gridSize - 1; x_coordinateRow++) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, x_coordinateRow, 0);
      }
    }

    //Second column from top to bottom. Tile from 31-40.
    for (int y_coordinateColumn = 0; y_coordinateColumn < gridSize; y_coordinateColumn++) {
      if (firstTileId <= 40) {
        StackPane tile = createTile(firstTileId++);
        grid.add(tile, gridSize - 1, y_coordinateColumn);
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
    controlPanel.setPadding(new Insets(10));
    controlPanel.setSpacing(10);
    controlPanel.setAlignment(Pos.CENTER);

    gameInformation = new Label(Constants.LABEL_LAST_ROLLED_BUTTON);
    Label playerInformation = new Label(displayPlayerInfoMonopoly(
        controllerMonopoly.getBoardGame()));

    Button rollDice = getRollDiceButton(playerInformation);
    Button startButton = getStartGameButton(playerInformation);
    Button saveGame = getSaveGameButton();


    controlPanel.getChildren().addAll(startButton, rollDice,
        gameInformation, playerInformation, saveGame);
    return controlPanel;
  }

  /**
   * A button to save the game.
   *
   * <p>This button allows the user to save the current game state.</p>
   *
   * @return Button to save the game.
   */
  private Button getSaveGameButton() {
    Button saveGame = new Button("Save Game");
    saveGame.setOnAction(e -> controllerMonopoly.saveGame());
    return saveGame;
  }


  /**
   * Creates the button to start the game.
   *
   * <p>This method initializes the game and enables the roll dice button.</p>
   *
   * @param playerInformation The label to display player information.
   * @return Button to start the game.
   */
  private Button getStartGameButton(Label playerInformation) {
    startGameButton = new Button("Start Game");
    startGameButton.setDisable(true);
    startGameButton.setOnAction(event -> {
      controllerMonopoly.initializeMonopoly();
      updateBoard();
      rollDiceButton.setDisable(false);
      startGameButton.setDisable(true);
      playerInformation.setText(displayPlayerInfoMonopoly(controllerMonopoly.getBoardGame()));
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
  private Button getRollDiceButton(Label playerInformation) {
    rollDiceButton = new Button("Roll Dice");
    rollDiceButton.setDisable(false);
    rollDiceButton.setOnAction(event -> {
      controllerMonopoly.initializeRollDice();
      Player player = controllerMonopoly.getBoardGame().getCurrentPlayer();
      int rollSum = controllerMonopoly.getBoardGame().getDice().getDie(0)
          + controllerMonopoly.getBoardGame().getDice().getDie(1);
      if (!controllerMonopoly.winnerFound()) {
        observerPlayerMoved(rollSum);
      } else {
        gameInformation.setText("Winner: " + player.getName());
        rollDiceButton.setDisable(true);
        startGameButton.setDisable(false);
      }
      playerInformation.setText(displayPlayerInfoMonopoly(controllerMonopoly.getBoardGame()));
      updateBoard();
    });
    return rollDiceButton;
  }

  /**
   * Updates the board display by creating a new board grid and replacing the old one.
   *
   * <p>This method is called to refresh the board display after a player rolls the dice.
   * It creates a new grid layout for the board
   * and sets it as the center of the main layout.</p>
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
    Rectangle rect = new Rectangle(Constants.TILE_SIZE, Constants.TILE_SIZE);
    Color baseColor = getColor(tileId);
    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    TileAction landAction = controllerMonopoly.getTileAction(tileId);
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
    Iterator<Player> playerIterator = controllerMonopoly.getPlayersIterator();
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next();
      if (player.getCurrentTile().getTileId() == tileId) {
        Circle circle = createPlayer(player.getColor());
        stack.getChildren().add(circle);
      }
    }
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
        controllerMonopoly::switchToGameSelection);
  }

  /**
   * A notification method that is called when a player moves.
   *
   * <p>This method updates the game information label
   * to display the rolled value.</p>
   *
   * @param tileId the ID of the tile the player moved to.
   */
  @Override
  public void observerPlayerMoved(int tileId) {
    gameInformation.setText("rolled: " + tileId);
  }

  /**
   * Create a button to display the game rules.
   *
   * <p>This button opens an alert dialog with the game rules when clicked.
   * The user is prompted:</p>
   *
   * <li>Game Rules</li>
   * <li>Monopoly Game Rules</li>
   * <li>Game rules text</li>
   *
   * @return a Button to display the game rules.
   */
  private Button createGameRulesButton() {
    return ButtonFactory.gameRulesButton(
        Constants.GAME_MONOPOLY_HEADER,
        Constants.GAME_RULES,
        Constants.MONOPOLY_RULES);
  }
}

