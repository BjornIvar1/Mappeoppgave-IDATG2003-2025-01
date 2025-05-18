package ui.gui.menu;

import filehandler.PlayerFileWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Player;
import ui.controller.ControllerCreatePlayer;
import ui.gui.BasePage;
import utils.Constants;
import utils.exception.GUIInvalidNameException;
import utils.exception.InvalidPlayerFields;

public class CreatePlayer extends BasePage {
  private final ControllerCreatePlayer controller;
  private final Spinner<Integer> playerAmount = new Spinner<>(0,4,0);
  private final Alert alertWarning = new Alert(Alert.AlertType.WARNING);
  private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
  private final ObservableList<TextField> playerFields = FXCollections.observableArrayList();
  private final List<String> listOfColors = List.of("RED", "BLUE", "GREEN", "YELLOW");
  private String gameBoardPath;


  public CreatePlayer(ControllerCreatePlayer controllerGameChoice) {
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
    SplitMenuButton chooseGameBoard = getSplitMenuButton();
    if (controller.getGameID() == 2) {
      chooseGameBoard.setVisible(false);
    }

    container.getChildren().addAll(playerAmount, createUserButton, chooseGameBoard);
    container.setAlignment(Pos.CENTER);
    return container;
  }

  private SplitMenuButton getSplitMenuButton() {
    SplitMenuButton chooseGameBoard = new SplitMenuButton();
    chooseGameBoard.setText("Choose Game Board");
    MenuItem snakesAndLaddersNormal = new MenuItem("Normal Snakes and Ladders");
    MenuItem snakesAndLaddersSmall = new MenuItem("Small Snakes and Ladders");

    snakesAndLaddersNormal.setOnAction(actionEvent -> {
      gameBoardPath = Constants.SNAKES_AND_LADDERS_BOARD_FILE_PATH;
      System.out.println("Normal");
      chooseGameBoard.setText(snakesAndLaddersNormal.getText());
    });
    snakesAndLaddersSmall.setOnAction(actionEvent -> {
      gameBoardPath = Constants.SNAKES_AND_LADDERS_SMALL_BOARD_FILE_PATH;
      System.out.println("Small");
      chooseGameBoard.setText(snakesAndLaddersSmall.getText());
    });

    chooseGameBoard.getItems().addAll(snakesAndLaddersNormal, snakesAndLaddersSmall);
    return chooseGameBoard;
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
        PlayerFileWriter.writeToCsv(playerList, Constants.PLAYER_FILE_PATH);
        controller.goToGame(gameBoardPath);
      } catch (GUIInvalidNameException | InvalidPlayerFields e) {
        alertWarning.setContentText(e.getMessage());
        alertWarning.show();
      }
    });
    return createUserButton;
  }
}
