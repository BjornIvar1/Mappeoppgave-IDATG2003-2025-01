package kontroller;

import gui.LandingPage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneManager {
  private Stage stage;
  private BorderPane root;

  public SceneManager(Stage stage) {
    this.stage = stage;
    this.root = new BorderPane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void setView(LandingPage layout) {
    root.setCenter(layout);
  }

  public BorderPane getRootPane() {
    return root;
  }
}
