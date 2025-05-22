package edu.ntnu.idi.bidata.ui.controller;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Manages scene transitions within a JavaFX application.
 *
 * <p>This class provides functionality to switch between different views using
 * an {@code AnchorPane} as the root container.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.2.0
 */
public class SceneManager {
  private final Stage stage;

  /**
   * Constructs a {@code SceneManager} with the specified stage.
   *
   * <p>This constructor initializes the {@code SceneManager} with the provided
   * primary stage, which will be used to manage scene transitions.</p>
   *
   * @param newRoot the primary stage for this application
   */
  public SceneManager(Stage newRoot) {
    this.stage = newRoot;
  }

  /**
   * Sets the current view within the scene.
   *
   * <p>This method clears the existing children of the root container and sets the
   * provided {@code Parent} view as the new content, aligning it to the edges.
   *
   * @param newRoot the new root container to be set as the current view
   */
  public void setView(Pane newRoot) {
    Scene newScene = new Scene(newRoot, 800, 800);
    fadeTransition(newScene);
    stage.setScene(newScene);
    stage.show();
  }

  /**
   * Applies a fade transition effect to the specified view.
   *
   * <p>This method creates a {@code FadeTransition} that animates the opacity of the
   * provided {@code Parent} view from 0 to 1 over a duration of 500 milliseconds.</p>
   * */
  private void fadeTransition(Scene scene) {
    FadeTransition fade = new FadeTransition(Duration.millis(500));
    fade.setFromValue(0);
    fade.setToValue(1);
    fade.setNode(scene.getRoot());
    fade.play();
  }
}

