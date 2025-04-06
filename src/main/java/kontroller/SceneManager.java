package kontroller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Manages scene transitions within a JavaFX application.
 *
 * <p>This class provides functionality to switch between different views using
 * an {@code AnchorPane} as the root container.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.1.0
 */
public class SceneManager {
  private Stage stage;
  private AnchorPane root;

  /**
   * Constructs a {@code SceneManager} with the specified stage.
   *
   * @param stage the primary {@code Stage} where scenes will be displayed
   */
  public SceneManager(Stage stage) {
    this.stage = stage;
    this.root = new AnchorPane();
    Scene scene = new Scene(root, 800, 800);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the current view within the scene.
   *
   * <p>This method clears the existing children of the root container and sets the
   * provided {@code Parent} view as the new content, aligning it to the edges.
   *
   * @param view the {@code Parent} view to be displayed
   */
  public void setView(Parent view) {
    root.getChildren().clear();
    AnchorPane.setTopAnchor(view, 0.0);
    AnchorPane.setBottomAnchor(view, 0.0);
    AnchorPane.setLeftAnchor(view, 0.0);
    AnchorPane.setRightAnchor(view, 0.0);
    root.getChildren().add(view);
  }
}

