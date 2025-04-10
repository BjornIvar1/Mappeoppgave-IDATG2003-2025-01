package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BaseGamePage extends BasePage {
  //TODO get menu bare from basePage...
  protected Circle createPlayer(String playerColor) {
    Circle gamePiece = new Circle(15);
    Color color;

    color = getColor(playerColor);
    gamePiece.setFill(color);
    return gamePiece;
  }

  private Color getColor(String playerColor) {
    Color color;
    switch (playerColor.toUpperCase()) {
      case "RED":
        color = Color.RED;
        break;
      case "BLUE":
        color = Color.BLUE;
        break;
      case "GREEN":
        color = Color.GREEN;
        break;
      case "YELLOW":
        color = Color.YELLOW;
        break;
      default:
        color = Color.BLACK;
        break;
    }
    return color;
  }
}
