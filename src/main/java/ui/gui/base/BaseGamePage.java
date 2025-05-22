package ui.gui.base;

import java.util.Iterator;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.engine.BoardGame;
import model.entity.Player;


/**
 * This class represents the base page for the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.4.5
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
   * Gets the color of the tile based on its ID.
   *
   * <p>This method returns a color based on the tile ID.
   * Even tiles are blue and odd tiles are green.</p>
   *
   * @param tileId the ID of the tile
   * @return the color of the tile
   */
  protected static Color getColor(int tileId) {
    return (tileId % 2 == 0) ? Color.web("#32bff5") : Color.web("#bbd962");
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
   * Creates an {@link ImageView} with a specified image path, width, height, and visibility.
   *
   * <p>The method loads an image from a given path,
   * sets its width and height and applies the visibility.
   * Returns the {@link ImageView}.</p>
   *
   * @param path the path to the image file
   * @param width the width of the image
   * @param height the height of the image
   * @param isVisible true if the image should be visible, false otherwise
   * @return the created {@link ImageView}
   */
  protected ImageView createImage(String path, int width, int height, boolean isVisible) {
    ImageView imageView = new ImageView(loadImage(path));
    imageView.setFitWidth(width);
    imageView.setFitHeight(height);
    imageView.setVisible(isVisible);
    return imageView;
  }

  /**
   * Loads an image from the specified path.
   *
   * @param path the path to the image file
   * @return the loaded {@link Image}
   */
  protected Image loadImage(String path) {
    return new Image(Objects
        .requireNonNull(getClass()
            .getResource(path)).toExternalForm());
  }
}
