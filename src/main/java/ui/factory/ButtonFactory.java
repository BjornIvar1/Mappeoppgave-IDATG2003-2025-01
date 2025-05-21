package ui.factory;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import utils.Constants;

/**
 * A factory class for creating common buttons in the ui.
 *
 * <p>A factory class that creates standard button that other classes
 * use. This is to reuse and modify actions with the buttons.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.2.1
 */
public class ButtonFactory {
  private static final Alert gameRulesAlert = new Alert(Alert.AlertType.INFORMATION);

  private ButtonFactory() {
    //empty constructor
  }

  /**
   * A button that sends the user back to the previous page.
   *
   * <p>Creates a button that sends the user back to
   * page they were in previously. The button is red,
   * but when hovered upon it turns green.</p>
   *
   * @param action the action that will happen by clicking
   *               the button.
   * @return a button.
   */
  public static Button returnButtonFactory(Runnable action) {
    Button button = new Button(Constants.BACK);
    button.setStyle("-fx-background-color: RED; -fx-text-fill: white; -fx-font-size: 16px;");
    button.setPrefSize(100, 40);
    button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: GREEN;"
        + Constants.GET_COLOR_WHITE));
    button.setOnMouseExited(e -> button.setStyle("-fx-background-color: RED; "
        + Constants.GET_COLOR_WHITE));
    button.setOnAction(event -> action.run());
    return button;
  }

  /**
   * A button that gives the user information about the game.
   *
   * <p>Creates a button that sends the user to the game
   * information and rules. The button is blue, but when hovered
   * upon it turns light blue.</p>
   *
   * @param alertTitle  The title of the alert dialog.
   * @param alertHeader The header text of the alert dialog.
   * @param alertContent The content text of the alert dialog.
   * @return A configured Button instance.
   */
  public static Button informationButton(String buttonName, String alertTitle,
                                         String alertHeader, String alertContent) {
    Button gameRulesButton = new Button(buttonName);
    gameRulesButton.setStyle("-fx-background-color: BLUE; "
        + Constants.GET_COLOR_WHITE);
    gameRulesButton.setPrefSize(100, 40);
    gameRulesButton.setOnMouseEntered(e ->
        gameRulesButton.setStyle("-fx-background-color: LIGHTBLUE;"
        + Constants.GET_COLOR_WHITE));
    gameRulesButton.setOnMouseExited(e ->
        gameRulesButton.setStyle("-fx-background-color: BLUE;"
        + Constants.GET_COLOR_WHITE));

    gameRulesButton.setOnAction(event -> {
      gameRulesAlert.setTitle(alertTitle);
      gameRulesAlert.setHeaderText(alertHeader);
      gameRulesAlert.setContentText(alertContent);
      gameRulesAlert.setResizable(true);
      gameRulesAlert.getDialogPane().setPrefSize(400, 300);
      gameRulesAlert.showAndWait();
    });

    return gameRulesButton;
  }
}
