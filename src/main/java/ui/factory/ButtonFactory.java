package ui.factory;

import javafx.scene.control.Button;

/**
 * A button Factory.
 *
 * <p>A factory class that creates standard button that other classes
 * use. This is to reuse and modify actions with the buttons.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.1.0
 */
public class ButtonFactory {

  private ButtonFactory () {
    //empty constructor
  }

  /**
   * A button that sends the user back to the previous page.
   *
   * <p>Creates a button that sends the user back to
   * page they were in previously. The button is red,
   * but when hovered upon it turns green.</p>
   *
   * @param txt the name of the button.
   * @param action the action that will happen by clicking
   *               the button.
   * @return a button.
   */
  public static Button returnButtonFactory(String txt, Runnable action) {
    Button button = new Button(txt);
    button.setStyle("-fx-background-color: RED; -fx-text-fill: white; -fx-font-size: 16px;");
    button.setPrefSize(100, 40);
    button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: GREEN; -fx-text-fill: white; -fx-font-size: 16px;"));
    button.setOnMouseExited(e -> button.setStyle("-fx-background-color: RED; -fx-text-fill: white; -fx-font-size: 16px;"));
    button.setOnAction(event -> action.run());
    return button;
  }

  public static Button createGameInfoButton(String buttonTitle, Runnable action) {
    Button gameRulesButton = new Button(buttonTitle);
    gameRulesButton.setStyle("-fx-background-color: BLUE; -fx-text-fill: white; -fx-font-size: 16px;");
    gameRulesButton.setPrefSize(100, 40);
    gameRulesButton.setOnMouseEntered(e -> gameRulesButton
        .setStyle("-fx-background-color: LIGHTBLUE;" +
            " -fx-text-fill: white; -fx-font-size: 16px;"));
    gameRulesButton.setOnMouseExited(e -> gameRulesButton
        .setStyle("-fx-background-color: BLUE;" +
            " -fx-text-fill: white; -fx-font-size: 16px;"));
    gameRulesButton.setOnAction(event -> action.run());
    return gameRulesButton;
  }
}
