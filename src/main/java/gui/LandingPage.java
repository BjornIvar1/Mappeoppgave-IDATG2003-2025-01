package gui;

import controller.ControllerLandingPage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The {@code LandingPage} class creates a graphical user interface for visual interaction.
 * It contains a button to start playing, a logo to show the brand and menu bar.
 *
 * @since 0.0.1
 * @author Arpit & Bjørn
 * @version 0.0.1
 */
public class LandingPage extends Application {
  ControllerLandingPage controllerLP;


  /**
   * Starts the JavaFX application and initializes the user interface.
   *
   * @param primaryStage the primary stage for this application
   */
  @Override
  public void start(Stage primaryStage) {
    controllerLP = new ControllerLandingPage(this);
    BorderPane root = new BorderPane();
    root.setCenter(buttonAndImage());
    root.setTop(createmenuBar());

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("3Spill");
    primaryStage.show();
  }

  /**
   * Creates a menubar for the user to easily exit the game.
   * @return menuBar.
   */
  private MenuBar createmenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu fileItem = new Menu("file");

    MenuItem closeItem = new MenuItem("Exit");
    closeItem.setOnAction(event ->
        controllerLP.exitApplication());

    fileItem.getItems().add(closeItem);
    menuBar.getMenus().addAll(fileItem);
    return menuBar;
  }

  /**
   * Aligns the button and Logo under each other.
   *
   * @return buttonAndImage.
   */
  private Pane buttonAndImage() {
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
   * Adds a button, so the player can go to next page
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

  /**
   * The main entry point of the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }



}

