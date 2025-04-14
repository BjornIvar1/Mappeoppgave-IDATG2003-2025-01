package gui;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Player;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class represents the base page for the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.0
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

  protected static Color getColor(int tileId) {
    return (tileId % 2 == 0) ? Color.web("#32bff5") : Color.web("#bbd962");
  }
  protected static void setFill(Rectangle rect, Color baseColor) {
    rect.setFill(baseColor);
  }

  /**
   * Plays the game by rolling the dice and updating the game information.
   *
   * <p>This method simulates a turn in the game by rolling the dice,
   *
   * @param boardGame the board game instance.
   * @param gameInformation the label to display game information.
   */
  protected void playGame(BoardGame boardGame, Label gameInformation) {
    boardGame.play();
    Player player = boardGame.getCurrentPlayer();
    int rollSum = boardGame.getDice().getDie(0) + boardGame.getDice().getDie(1);
    if (boardGame.getCurrentPlayer().getCurrentTile().getTileId() == 90) {
      //TODO find a way to difine a winner by checking max balance.
      //TODO add if sentence of which game the player is playing.
      gameInformation.setText("Winner: " + player.getName() + "\n" + "Press Start Game to play again");
    } else {
      gameInformation.setText(player.getName() + " rolled: " + rollSum);
    }
  }

}
