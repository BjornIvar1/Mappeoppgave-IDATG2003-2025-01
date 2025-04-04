package gui;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The {@code MainPage} class creates a graphical user interface for interacting that
 * allows the user to enter and leave the game.
 *
 * @since 0.0.1
 * @author Arpit & Bjørn
 * @version 0.0.1
 */
public class SnakesAndLaddersGame extends Application {
  private static final int TILE_SIZE = 60;
  private static final int ROWS = 9;
  private static final int COLUMNS = 10;
  private BoardGame boardGame;

  /**
   * Starts the JavaFX application and initializes the user interface.
   *
   * @param primaryStage the primary stage for this application
   */
  @Override
  public void start(Stage primaryStage) {
    GridPane board = createBoard();

    VBox root = new VBox();
    root.getChildren().addAll(board, createControlPanel());

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Snakes and Ladders");
    primaryStage.show();
  }

  private void initializeGame() {
    boardGame = new BoardGame();

    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of("src/main/resources/board/board.json")));
      boardGame.createDice(9);
      playerReader.readCsvBuffered("src/main/resources/players/playersInGameFile.csv", boardGame);
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
    });

    Button rollDice = new Button("Roll Dice");
    rollDice.setOnAction(e -> {
      boardGame.play();
    });

    controlPanel.getChildren().addAll(startButton, rollDice);
    return controlPanel;
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
    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileid));

    StackPane stack = new StackPane();
    stack.getChildren().addAll(rect, text);

    if (tileid == 75) {
      Circle circle = createPlayer();
      stack.getChildren().add(circle);
    }

    return stack;
  }

  /**
   * The main entry point of the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}

