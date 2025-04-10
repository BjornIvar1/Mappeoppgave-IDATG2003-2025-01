package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BaseGamePage extends BasePage {
  protected Circle createPlayer(String playerColor) {
    Circle gamePiece = new Circle(15);
    Color color;

    color = getColor(playerColor);
    gamePiece.setFill(color);
    return gamePiece;
  }

  private Color getColor(String playerColor) {
    return switch (playerColor.toUpperCase()) {
      case "RED" -> Color.RED;
      case "BLUE" -> Color.BLUE;
      case "GREEN" -> Color.GREEN;
      case "YELLOW" -> Color.YELLOW;
      default -> Color.BLACK;
    };
  }
}
