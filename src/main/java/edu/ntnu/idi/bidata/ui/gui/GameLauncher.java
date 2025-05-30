package edu.ntnu.idi.bidata.ui.gui;

import edu.ntnu.idi.bidata.ui.controller.ControllerLandingPage;
import edu.ntnu.idi.bidata.ui.controller.SceneManager;
import edu.ntnu.idi.bidata.ui.gui.menu.LandingPage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@code GameLauncher} class creates a graphical user interface for interacting that
 * allows the user to enter and leave the game.
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.1
 */
public class GameLauncher extends Application {
  /**
   * Starts the JavaFX application and initializes the user interface.
   *
   * @param primaryStage the primary stage for this application
   */
  @Override
  public void start(Stage primaryStage) {
    SceneManager sceneManager = new SceneManager(primaryStage);
    sceneManager.setView(new LandingPage(
        new ControllerLandingPage(sceneManager)));
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

