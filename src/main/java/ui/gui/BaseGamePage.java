package ui.gui;

import engine.BoardGame;
import filehandler.board.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Player;
import utils.exception.NullOrBlankException;

/**
 * This class represents the base page for the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.4.3
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
   * <p>This method is used to display the players in the game
   * for the Snakes and Ladders game.</p>
   *
   * @param boardGame the board game
   * @return a string representation of the players
   */
  protected String displayPlayers(BoardGame boardGame) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<Player> playerIterator = boardGame.getPlayerIterator();
    while (playerIterator.hasNext()) {
      Player player = playerIterator.next();
      stringBuilder.append("Name: ").append(player.getName()).append("; ")
          .append("Game Piece: ").append(player.getColor()).append("\n");
    }
    return stringBuilder.toString();
  }

  /**
   * Displays the players in the game for Monopoly.
   *
   * <p>This method is used to display the players in the game
   * for the Monopoly game.</p>
   *
   * @param boardGame the board game
   * @return a string representation of the players
   */
  protected String displayPlayerInfoMonopoly(BoardGame boardGame) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<Player> playerIteratorInMonopoly = boardGame.getPlayerIterator();
    while (playerIteratorInMonopoly.hasNext()) {
      Player player = playerIteratorInMonopoly.next();
      stringBuilder.append("Name: ").append(player.getName()).append("; ")
          .append("Game Piece: ").append(player.getColor()).append("; ")
          .append("Balance: ").append(player.getBalance()).append("\n");
    }
    return stringBuilder.toString();
  }

  /**
   * Initializes the board game with players and board.
   *
   * <p>This method reads the board and player files, creates the board,
   * and initializes the players.<p>
   *
   * @param boardFilePath of the board file.
   * @param playerFilePath of the player file.
   * @return the initialized board game.
   */
  protected BoardGame initializeBoardGame(String boardFilePath, String playerFilePath) {
    BoardGame boardGame = new BoardGame();
    BoardFileReaderGson reader = new BoardFileReaderGson();
    PlayerFileReader playerReader = new PlayerFileReader();
    try {
      boardGame.createBoard(reader.readBoard(Path.of(boardFilePath)));
      boardGame.createDice(2);
      playerReader.readCsvBuffered(playerFilePath, boardGame);
      boardGame.getPlayers().forEach(player -> player.placeOnTile(boardGame.getBoard().getTile(1)));
    } catch (IOException | NullPointerException | NullOrBlankException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
  }

  protected static Color getColor(int tileId) {
    return (tileId % 2 == 0) ? Color.web("#32bff5") : Color.web("#bbd962");
  }

}
