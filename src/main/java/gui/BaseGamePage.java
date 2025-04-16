package gui;

import engine.BoardGame;
import filehandler.BoardFileReaderGson;
import filehandler.PlayerFileReader;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Player;
import model.PlayerInMonopoly;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class represents the base page for the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.4.0
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

  /**
   * Displays the players in the game for Monopoly.
   *
   * @param boardGame the board game
   * @return a string representation of the players
   */
  protected String displayPlayerInfoMonopoly(BoardGame boardGame) {
    StringBuilder stringBuilder = new StringBuilder();
    for (PlayerInMonopoly p : boardGame.getPlayers()) {
      stringBuilder.append("Name: ").append(p.getName()).append("; ")
          .append("Game Piece: ").append(p.getColor()).append("; ")
          .append("Balance: ").append(p.getBalance()).append("\n");
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
    } catch (IOException e) {
      System.out.println("Could not read board or players from file: " + e.getMessage());
    }
    return boardGame;
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
      gameInformation.setText("Winner: " + player.getName() + "\n" + "Press Start Game to play again");
    } else {
      gameInformation.setText(player.getName() + " rolled: " + rollSum);
    }
  }

}
