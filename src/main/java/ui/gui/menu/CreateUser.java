package ui.gui.menu;

import filehandler.PlayerFileWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Player;
import model.exception.InvalidNameException;
import model.exception.NullOrBlankColorException;
import ui.controller.ControllerCreateUser;
import ui.gui.BasePage;
import ui.gui.exception.GUIInvalidNameException;
import ui.gui.exception.InvalidPlayerFields;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CreateUser extends BasePage {
  private final ControllerCreateUser controller;
  private final Spinner<Integer> playerAmount = new Spinner<>(0,4,0);
  private final Alert alertWarning = new Alert(Alert.AlertType.WARNING);
  private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
  private final ObservableList<TextField> playerFields = FXCollections.observableArrayList();
  private final List<String> listOfColors = List.of("RED", "BLUE", "GREEN", "YELLOW");


  public CreateUser(ControllerCreateUser controllerGameChoice) {
    //TODO lag en id for spillet de skal spille sliik den sender deg til rikitg spill i kontrolleren til createusercontroller?
    //TODO Deretter så er det een if statement for hvor de sendes if game === monoply go to monoply else go to snakes and ladders?
    this.controller = controllerGameChoice;
    setCenter(createUserPane());
  }

  private Pane createUserPane() {
    VBox container = new VBox();

    playerFields.addAll(new TextField(), new TextField(), new TextField(), new TextField());
    for (TextField playerField : playerFields) {
      playerField.setVisible(false);
      playerField.setMaxWidth(160);
      playerField.setPromptText("Player " + (playerFields.indexOf(playerField) + 1));
      container.getChildren().add(playerField);
    }

    playerAmount.setPromptText("Number of players");
    playerAmount.setEditable(true);

    //Løsning Inspireret av:
    //https://stackoverflow.com/questions/43745272/generate-plain-text-fields-based-on-chosen-spinner-value
    playerAmount.valueProperty().addListener((obs, oldValue, newValue) -> {
      for (int i = 0; i < playerFields.size(); i++) {
        playerFields.get(i).setVisible(i < newValue);
      }
    });

    Button createUserButton = getCreateUserButton();

    container.getChildren().addAll(playerAmount, createUserButton);
    container.setAlignment(Pos.CENTER);
    return container;
  }

  private Button getCreateUserButton() {
    Button createUserButton = new Button("Create User");
    createUserButton.setOnAction(event -> {
      try {
        int numberOfPlayerFields = playerAmount.getValue();
        List <Player> playerList = new ArrayList<>();
        if (numberOfPlayerFields == 0) {
          throw new InvalidPlayerFields("Please select a number of players.");
        }

        for (int i = 0; i < playerAmount.getValue(); i++) {
          TextField playerField = playerFields.get(i);
          String userName = playerField.getText().trim();
          System.out.println(i);
          if (userName.isEmpty()) {
            throw new GUIInvalidNameException("Player " + (i+1) + " name is empty.");
          }
        }

        for (int i = 0; i < playerAmount.getValue(); i++) {
          TextField playerField = playerFields.get(i);
          String userName = playerField.getText().trim();
          Player player = new Player(userName, listOfColors.get(i), null, 0);
          playerList.add(player);
        }

        alertConfirmation.setContentText("Users created successfully!");
        alertConfirmation.show();
        PlayerFileWriter.writeToCSV(playerList, Constants.PLAYER_FILE_PATH);
        controller.goToSnakesAndLadders();
      } catch (NullOrBlankColorException | GUIInvalidNameException | InvalidPlayerFields e) {
        alertWarning.setContentText(e.getMessage());
        alertWarning.show();
      }
    });
    return createUserButton;
  }
}
