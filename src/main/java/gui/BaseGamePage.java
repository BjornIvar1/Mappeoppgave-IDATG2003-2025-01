package gui;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import javafx.geometry.Insets;
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
import model.Player;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class represents the base page for the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.0
 */
public class BaseGamePage extends BasePage {

  /**
   * Creates a player game piece.
   *
   * @param playerColor the color of the player
   * @return a Circle representing the player game piece
   */
  protected Circle createPlayer(String playerColor) {
    Circle gamePiece = new Circle(15);
    Color color;

    color = getColor(playerColor);
    gamePiece.setFill(color);
    return gamePiece;
  }

  /**
   * Switch statement to get the color of the player.
   *
   * @param playerColor the color of the player
   * @return the color of the player
   */
  private Color getColor(String playerColor) {
    return switch (playerColor.toUpperCase()) {
      case "RED" -> Color.RED;
      case "BLUE" -> Color.BLUE;
      case "GREEN" -> Color.GREEN;
      case "YELLOW" -> Color.YELLOW;
      default -> Color.BLACK;
    };
  }

  /**
   * Displays the players in the game.
   *
   * @param boardGame the board game
   * @return a string representation of the players
   */
  protected String displayPlayers(BoardGame boardGame) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Player player : boardGame.getPlayers()) {
      stringBuilder.append("Name: ").append(player.getName()).append("; ")
          .append("Game Piece: ").append(player.getColor())
          .append("\n");
    }
    return stringBuilder.toString();
  }

  protected BoardGame initializeBoardGame(String boardFilePath, String playerFilePath) {
    BoardGame boardGame = new BoardGame();
    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of(boardFilePath)));
      boardGame.createDice(2);
      playerReader.readCsvBuffered(playerFilePath, boardGame);
      boardGame.getPlayers().forEach(player -> player.placeOnTile(boardGame.getBoard().getTile(1)));
    } catch (IOException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }

  /**
   * Creates a visual representation of the game board.
   *
   * @param boardGame the board game instance
   * @return a GridPane representing the game board
   */
  protected GridPane createBoard(BoardGame boardGame) {
    GridPane grid = new GridPane();
    int tileNumber = boardGame.getBoard().getRows() * boardGame.getBoard().getColumns();

    for (int y = 0; y < boardGame.getBoard().getRows(); y++) {
      if (y % 2 != 0) {
        for (int x = 0; x < boardGame.getBoard().getColumns(); x++) {
          StackPane tile = createTile(tileNumber--, boardGame); // help from GIT copilot for refactoring
          grid.add(tile, x, y);
        }
      } else {
        for (int x = boardGame.getBoard().getColumns() - 1; x >= 0; x--) {
          StackPane tile = createTile(tileNumber--, boardGame);
          grid.add(tile, x, y);
        }
      }
    }

    return grid;
  }

  /**
   * Creates a visual representation of a game tile.
   *
   * @param tileId    the unique identifier of the tile
   * @param boardGame the board game instance containing the tile and players
   * @return a StackPane representing the tile, including its background, label, and any player pieces
   */
  protected StackPane createTile(int tileId, BoardGame boardGame) {
    Rectangle rect = new Rectangle(60, 60);
    Color baseColor = (tileId % 2 == 0) ? Color.LIGHTBLUE : Color.WHITE;

    rect.setFill(baseColor);

    if (boardGame.getBoard().getTiles().get(tileId).getLandAction() != null) {
      rect.setFill(Color.BROWN);
    }

    rect.setStroke(Color.BLACK);
    Text text = new Text(String.valueOf(tileId));

    StackPane stack = new StackPane();
    stack.getChildren().addAll(rect, text);

    // Add player pieces to the tile
    boardGame.getPlayers().stream()
        .filter(player -> player.getCurrentTile().getTileId() == tileId)
        .forEach(player -> {
          Circle playerCircle = createPlayer(player.getColor());
          stack.getChildren().add(playerCircle);
        });

    return stack;
  }

  /**
   * Updates the board display by creating a new board grid and replacing the old one.
   */
  protected void updateBoard(BorderPane mainLayout, BoardGame boardGame) {
    GridPane boardGrid = createBoard(boardGame);
    mainLayout.setCenter(boardGrid);
  }

  protected HBox createControlPanel(Button startButton, Button rollDice, Label gameInfo, Label playerInfo) {
    HBox controlPanel = new HBox();
    controlPanel.setPadding(new Insets(10));
    controlPanel.setSpacing(10);
    controlPanel.setAlignment(Pos.CENTER);

    controlPanel.getChildren().addAll(startButton, rollDice, gameInfo, playerInfo);
    return controlPanel;
  }

  protected Button getButton(String text, Runnable action) {
    Button button = new Button(text);
    button.setOnAction(e -> action.run());
    return button;
  }

}
