package gui.menu;

import controller.ControllerCreateUser;
import gui.BasePage;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class CreateUser extends BasePage {
  private final ControllerCreateUser controller;

  public CreateUser(ControllerCreateUser controller) {
    this.controller = controller;
    setAlignment(Pos.CENTER);
    BorderPane mainLayout = new BorderPane();

    mainLayout.setTop(createMenuBar());

    getChildren().add(mainLayout);
  }
}
