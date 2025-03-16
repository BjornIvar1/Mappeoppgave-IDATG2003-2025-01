package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameChoice extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root);

    root.setCenter(allTHePanes());
    stage.setTitle("Choose a Game");
    stage.setScene(scene);
    stage.show();
  }

  private Pane allTHePanes() {
    VBox buttonAndImage = new VBox();
    buttonAndImage.setAlignment(Pos.CENTER);
    buttonAndImage.setSpacing(10);
    buttonAndImage.setPadding(new Insets(10, 10, 10, 10));
    buttonAndImage.getChildren().addAll(imagePaneCreate(),titlePaneCreate());
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
    ImageView image = new ImageView("logo.png");
    imagePane.getChildren().add(image);
    imagePane.setAlignment(Pos.TOP_LEFT);
    return imagePane;
  }

  private VBox titlePaneCreate() {
    VBox titlePane = new VBox();
    Label title = new Label("Choose a Game");
    title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
    titlePane.getChildren().addAll(title, buttonPaneCreate());
    titlePane.setAlignment(Pos.CENTER);
    return titlePane;
  }

  private HBox buttonPaneCreate() {
    HBox buttonPane = new HBox();
    Button button1 = new Button("Snakes and ladders");
    Button button2 = new Button("Monopoly");

    buttonPane.setSpacing(30);
    button1.prefHeight(30);
    button1.setPrefWidth(300);
    button2.prefHeight(30);
    button2.setPrefWidth(300);

    buttonPane.getChildren().addAll(button1, button2);
    buttonPane.setAlignment(Pos.CENTER);
    return buttonPane;
  }
}
