package ui.gui.menu;

import ui.gui.BasePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ui.controller.ControllerLandingPage;

import java.util.Objects;

/**
 * This class represents the landing page.
 *
 * <p>This class is the landing page were the user
 * starts, when the user launches the application.
 * The user will be introduced with logo and a button to start
 * playing the game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.1.0
 */
public class LandingPage extends BasePage {
  private final ControllerLandingPage controller;

  public LandingPage(ControllerLandingPage controller) {
    this.controller = controller;
    setCenter(buttonAndImage());
    setTop(createMenuBar());
  }

  /**
   * Aligns the button and Logo under each other.
   *
   * @return buttonAndImage.
   */
  private VBox buttonAndImage() {
    VBox buttonAndImage = new VBox();
    buttonAndImage.setAlignment(Pos.CENTER);
    buttonAndImage.setSpacing(10);
    buttonAndImage.setPadding(new Insets(10, 10, 10, 10));
    buttonAndImage.getChildren().addAll(imagePaneCreate(), startButtonPaneCreate());
    return buttonAndImage;
  }

  /**
   * Fetches the logo and adds it to the interface, so the
   * user can see what application they are on.
   *
   * @return imagePane.
   */
  private FlowPane imagePaneCreate() {
    FlowPane imagePane = new FlowPane();
    ImageView image = new ImageView(Objects.
        requireNonNull(getClass().
            getResource("/image/logo.png")).toExternalForm());
    imagePane.getChildren().add(image);
    imagePane.setAlignment(Pos.CENTER);
    return imagePane;
  }

  /**
   * Adds a button, so the player can go to the next page
   * and begin playing the game.
   *
   * @return buttonPane.
   */
  private FlowPane startButtonPaneCreate() {
    FlowPane buttonPane = new FlowPane();
    Button startButton = new Button("Start");
    startButton.setOnAction(event ->  controller.switchToGameChoice() );
    startButton.prefHeight(30);
    startButton.prefWidth(300);
    buttonPane.getChildren().addAll(startButton);
    buttonPane.setAlignment(Pos.CENTER);
    return buttonPane;
  }
}
