package gui.game;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import gui.BaseGamePage;
import gui.BasePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kontroller.ControllerSnakesAndLadders;
import model.Player;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class SnakesAndLaddersPage extends BaseGamePage {
  private final ControllerSnakesAndLadders controller;
  private static final int TILE_SIZE = 60;
  private static final int ROWS = 9;
  private static final int COLUMNS = 10;
  private BoardGame boardGame;
  private GridPane boardGrid;

  public SnakesAndLaddersPage(ControllerSnakesAndLadders controllerSnakesAndLadders) {
    this.controller = controllerSnakesAndLadders;
    setAlignment(Pos.CENTER);
    getChildren().addAll();
    initializeGame();
    GridPane board = createBoard();
    getChildren().addAll(board, createControlPanel());
  }

  /**
   * Creates a menubar for the user to easily exit the game.
   *
   * @return menuBar.
   */
  private void initializeGame() {
    boardGame = new BoardGame();

    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of("src/main/resources/board/board.json")));
      boardGame.createDice(1);
      playerReader.readCsvBuffered("src/main/resources/players/playersInGameFile.csv", boardGame);
      for (Player player : boardGame.getPlayers()) {
        player.placeOnTile(boardGame.getBoard().getTile(1));
      }
    } catch (IOException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
  }

  private HBox createControlPanel() {
    HBox controlPanel = new HBox();
    controlPanel.setPadding(new Insets(10));
    controlPanel.setSpacing(10);
    controlPanel.setAlignment(Pos.CENTER);

    Button startButton = new Button("Start Game");
    startButton.setOnAction(e ->
    {
      System.out.println("Game started!");
      initializeGame();
      updateBoard();
    });

    Button rollDice = new Button("Roll Dice");
    rollDice.setOnAction(e -> {
      boardGame.play();
      updateBoard();
    });

    controlPanel.getChildren().addAll(startButton, rollDice);
    return controlPanel;
  }

  private void updateBoard() {
    boardGrid = createBoard();
    getChildren().set(0, boardGrid);
  }

  private Circle createPlayer() {
    Circle player = new Circle(15);
    player.setFill(Color.RED);
    return player;
  }

  private GridPane createBoard() {
    GridPane grid = new GridPane();
    int tileNumber = 90;

    for (int y = 0; y < ROWS; y++) {
      if (y % 2 != 0) {
        for (int x = 0; x < COLUMNS; x++) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      } else {
        for (int x = COLUMNS - 1; x >= 0; x--) {
          StackPane tile = createTile(tileNumber--);
          grid.add(tile, x, y);
        }
      }
    }

    return grid;
  }

  private StackPane createTile(int tileid) {
    Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
    if (tileid % 2 == 0) {
      rect.setFill(Color.LIGHTBLUE);
    } else {
      rect.setFill(Color.WHITE);
    }
    if (boardGame.getBoard().getTiles().get(tileid).getLandAction() != null) {
      rect.setFill(Color.BROWN);
    }
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileid));

    StackPane stack = new StackPane();
    stack.getChildren().addAll(rect, text);

    for (Player player : boardGame.getPlayers()) {
      if (player.getCurrentTile().getTileId() == tileid) {
        Circle playerCircle = createPlayer();
        stack.getChildren().add(playerCircle);
      }
    }
    return stack;
  }

}
