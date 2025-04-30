package gui.menu;

import controller.ControllerCreateUser;
import filehandler.PlayerFileWriter;
import gui.BasePage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import model.Player;

public class CreateUser extends BasePage {
  private final ControllerCreateUser controller;

  public CreateUser(ControllerCreateUser controllerGameChoice) {
    this.controller = controllerGameChoice;
    setCenter(createUserPane());
  }

  private Pane createUserPane() {
    VBox container = new VBox();
    TextField textField = new TextField();
    textField.setPromptText("Enter your name");
    textField.setMaxWidth(200);
    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
    Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);

    Button createUserButton = new Button("Create User");
    createUserButton.setOnAction(event -> {
      try {
        String userName = textField.getText();
        Player player = new Player(userName, "red", null);
        alertConfirmation.setContentText("User " + player.getName() + " created successfully!");
        alertConfirmation.show();
      } catch (IllegalArgumentException e) {
        alertWarning.setContentText(e.getMessage());
        alertWarning.show();
      }
    });

    container.getChildren().addAll(textField, createUserButton);
    container.setAlignment(Pos.CENTER);
    return container;
  }
}
