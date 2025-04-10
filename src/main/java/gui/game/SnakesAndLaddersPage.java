package gui.game;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import gui.BaseGamePage;
import java.io.IOException;
import java.nio.file.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import kontroller.ControllerSnakesAndLadders;
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
 * @version 0.0.2
 */
public class SnakesAndLaddersPage extends BaseGamePage {
  private static final int TILE_SIZE = 60;

  private static final String BOARD_FILE_PATH =
      "src/main/resources/board/SnakesAndLaddersBoard.json";
  private static final String PLAYER_FILE_PATH =
      "src/main/resources/players/playersInGameFile.csv";

  private BoardGame boardGame;
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
   * Initializes the game by creating a new BoardGame,
   * and creating a board, dice and adding the players.
   */
  private void initializeGame() {
    boardGame = new BoardGame();

    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of(BOARD_FILE_PATH)));
      boardGame.createDice(2);
      playerReader.readCsvBuffered(PLAYER_FILE_PATH, boardGame);
      boardGame.getPlayers().forEach(player -> player.placeOnTile(boardGame.getBoard().getTile(1)));
    } catch (IOException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
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
      updateBoard();
    });

    Button rollDice = new Button("Roll Dice");
    rollDice.setOnAction(e -> {
      boardGame.play();
      Player player = boardGame.getCurrentPlayer();
      int rollSum = boardGame.getDice().getDie(0) + boardGame.getDice().getDie(1);
      if (boardGame.getCurrentPlayer().getCurrentTile().getTileId() == 90) {
        gameInformation.setText("Winner: " + player.getName() + "\n" + "Press Start Game to play again");
      } else {
        gameInformation.setText(player.getName() + " rolled: " + rollSum);
      }
      updateBoard();
    });

    gameInformation = new Label("Last rolled: ---");
    Label playerInformation = new Label(displayPlayers(boardGame));

    controlPanel.getChildren().addAll(startButton, rollDice, gameInformation, playerInformation);
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
   * Creates the board grid for the Snakes and Ladders game.
   *
   * @return GridPane representing the game board.
   */
  private GridPane createBoard() {
    GridPane grid = new GridPane();
    int tileNumber = 90;

    for (int y = 0; y < boardGame.getBoard().getRows(); y++) {
      if (y % 2 != 0) {
        for (int x = 0; x < boardGame.getBoard().getColumns(); x++) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      } else {
        for (int x = boardGame.getBoard().getColumns() - 1; x >= 0; x--) {
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
    Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
    Color baseColor = (tileId % 2 == 0) ? Color.BEIGE : Color.WHITE;

    rect.setFill(baseColor);
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    TileAction landAction = boardGame.getBoard().getTiles().get(tileId).getLandAction();
    if (landAction != null) {
      rect.setFill(landAction.getColor());
      text.setWrappingWidth(TILE_SIZE);
      text.setText(landAction.getDescription());
    }


    StackPane stack = new StackPane();
    stack.getChildren().addAll(rect, text);

    boardGame.getPlayers().stream()
        .filter(player -> player.getCurrentTile().getTileId() == tileId)
        .forEach(player -> {
          Circle playerCircle = createPlayer(player.getColor());
          stack.getChildren().add(playerCircle);
        });
    return stack;
  }
}
