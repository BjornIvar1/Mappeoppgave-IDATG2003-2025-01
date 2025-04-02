package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import kontroller.ControllerLandingPage;

import java.util.Objects;

public class LandingPage extends FlowPane {

  public LandingPage(ControllerLandingPage controllerLandingPage) {
    setAlignment(Pos.CENTER);
    this.getChildren().addAll(buttonAndImage());
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
    startButton.prefHeight(30);
    startButton.prefWidth(300);
    buttonPane.getChildren().addAll(startButton);
    buttonPane.setAlignment(Pos.CENTER);
    return buttonPane;
  }
}
