package edu.ntnu.idi.bidata.ui.gui.base;

import edu.ntnu.idi.bidata.model.engine.BoardGame;
import edu.ntnu.idi.bidata.model.engine.Dice;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.utils.Constants;
import edu.ntnu.idi.bidata.utils.MessageDisplay;
import java.util.Iterator;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class represents the base page for the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.4.5
 */
public class BaseGamePage extends BasePage {

  /**
   * Empty constructor for the BaseGamePage class.
   */
  public BaseGamePage() {
    // empty constructor
  }

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

  /**
   * Updates the dice and player information.
   *
   * <p>This method updates the game information label,
   * the images of the dice, and the visibility of the player skipped image.</p>
   *
   * @param gameInformation the label to display game information
   * @param imageDice1 the image view for the first die
   * @param imageDice2 the image view for the second die
   * @param imageSkipPlayer the image view for the player skipped
   * @param name the name of the player
   * @param dice the dice object containing the rolled values
   */
  protected void updateDiceAndPlayerInfo(Label gameInformation,
                                         ImageView imageDice1,
                                         ImageView imageDice2,
                                         ImageView imageSkipPlayer,
                                         String name, Dice dice) {
    gameInformation.setText(MessageDisplay.rollDiceMessage(name));
    imageDice1.setImage(loadImage(Constants.getImageOfDice(dice.getDie(0))));
    imageDice2.setImage(loadImage(Constants.getImageOfDice(dice.getDie(1))));

    imageDice1.setVisible(true);
    imageDice2.setVisible(true);
    imageSkipPlayer.setVisible(false);
  }

  /**
   * Updates the game information when a players turn is skipped.
   *
   *
   * <p>This method updates the images of the dice,
   * and the visibility of the player skipped image.</p>
   *
   * @param imageDice1 the image view for the first die
   * @param imageDice2 the image view for the second die
   * @param imageSkipPlayer the image view for the player skipped
   */
  protected void updatePlayerSkipped(ImageView imageDice1,
                                     ImageView imageDice2,
                                     ImageView imageSkipPlayer) {
    imageSkipPlayer.setVisible(true);
    imageDice1.setVisible(false);
    imageDice2.setVisible(false);
  }

  /**
   * Creates a VBox with the game information and images of the dice.
   *
   * <p>This method creates a VBox with the game information label,
   * the images of the dice, and the image of the player skipped.
   * It sets the alignment and maximum width of the VBox.</p>
   *
   * @param imageDice1 the image view for the first die
   * @param imageDice2 the image view for the second die
   * @param imagePlayerSkipped the image view for the player skipped
   * @param gameInformation the label to display game information
   * @return the created VBox
   */
  protected VBox getVbox(Label gameInformation,
                         ImageView imageDice1,
                         ImageView imageDice2,
                         ImageView imagePlayerSkipped) {
    HBox diceBox = new HBox(5, imageDice1, imageDice2);
    diceBox.setAlignment(Pos.CENTER);

    StackPane imageStack = new StackPane(diceBox, imagePlayerSkipped);
    imageStack.setAlignment(Pos.CENTER);

    gameInformation.setText(Constants.LABEL_LAST_ROLLED_BUTTON);

    VBox box = new VBox(5);
    box.setAlignment(Pos.CENTER);
    box.setMaxWidth(200);
    box.getChildren().addAll(gameInformation, imageStack);
    return box;
  }
}
