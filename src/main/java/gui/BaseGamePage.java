package gui;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Player;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class represents the base page for the game.
 * @since 0.0.1
 * @version 0.1.0
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
}
