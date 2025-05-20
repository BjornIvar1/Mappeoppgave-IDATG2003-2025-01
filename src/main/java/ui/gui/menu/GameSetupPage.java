package ui.gui.menu;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.controller.ControllerGameSetupPage;
import ui.factory.ButtonFactory;
import ui.gui.BasePage;
import utils.Constants;
import utils.exception.GUIInvalidNameException;
import utils.exception.InvalidPlayerFields;

/**
 * Represents the player creation interface.
 *
 * <p>This class provides a graphical view for creating players and selecting a game board.
 * The controller supplies a <code>gameID</code> which determines whether the creation page
 * is for Snakes and Ladders or Monopoly. It includes a "back" button to return to the
 * {@code GameSelection} page.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.3.0
 * @since 0.0.1
 */
public class GameSetupPage extends BasePage {
  private final ControllerGameSetupPage controller;
  private final Spinner<Integer> playerAmount = new Spinner<>(0, 4, 0);
  private final Alert alertWarning = new Alert(Alert.AlertType.WARNING);
  private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
  private final ObservableList<HBox> playerFields = FXCollections.observableArrayList();
  private final List<String> listOfColors = List.of("RED", "BLUE", "GREEN", "YELLOW");
  private SplitMenuButton chooseGameBoard = new SplitMenuButton();
  private String gameBoardPath;

  /**
   * Constructs a {@code GameSetupPage} instance with a given game controller.
   *
   * @param controllerGameChoice the game controller responsible for player creation logic
   */
  public GameSetupPage(ControllerGameSetupPage controllerGameChoice) {
    this.controller = controllerGameChoice;
    setCenter(createUserPane());
    setTop(backToGameSelectionPage());
  }

  /**
   * Creates a pane containing the player creation UI elements.
   *
   * @return a {@code Pane} containing player fields and buttons
   */
  private Pane createUserPane() {
    VBox container = new VBox(20);

    setupPlayerField(container);
    setupPlayerAmount();

    chooseGameBoard = getSplitMenuButton();
    if (controller.getGameId() == 2) {
      chooseGameBoard.setVisible(false);
    }

    Button createUserButton = getStartGameButton();
    Button loadGameButton = getLoadGameButton();


    container.getChildren().addAll(playerAmount, chooseGameBoard,
        createUserButton, loadGameButton);

    container.setAlignment(Pos.CENTER);
    return container;
  }

  private Button getLoadGameButton() {
    Button loadGameButton = new Button("Load Last Saved Game");

    loadGameButton.setOnAction(event -> {
      try {
        controller.loadSavedGame();
        alertConfirmation.setContentText("Game loaded successfully!");
        alertConfirmation.show();
      } catch (Exception e) {
        alertWarning.setContentText(e.getMessage());
        alertWarning.show();
      }
    });
    return loadGameButton;
  }

  /**
   * Sets up the player fields for user input.
   *
   * @param container the container to hold the player fields
   */
  private void setupPlayerField(VBox container) {
    for (int i = 0; i < 4; i++) {
      Label nameLabel = new Label("Game Piece " + listOfColors.get(i) + ":");
      nameLabel.setPrefWidth(122);

      TextField nameField = new TextField();
      nameField.setMaxWidth(160);
      nameField.setPromptText("Player " + (i + 1) + " name");

      HBox playerField = new HBox(10, nameLabel, nameField);
      playerField.setVisible(false);
      playerFields.add(playerField);
      playerField.setAlignment(Pos.CENTER);

      container.getChildren().add(playerField);
    }
  }

  /**
   * Sets up the spinner control for selecting the number of players.
   */
  private void setupPlayerAmount() {
    playerAmount.setPromptText("Number of players");
    playerAmount.setEditable(true);

    playerAmount.valueProperty().addListener((obs, oldValue, newValue) -> {
      for (int i = 0; i < playerFields.size(); i++) {
        playerFields.get(i).setVisible(i < newValue);
      }
    });
  }

  /**
   * Creates a SplitMenuButton for selecting the game board.
   *
   * @return a {@code SplitMenuButton} for game board selection
   */
  private SplitMenuButton getSplitMenuButton() {
    chooseGameBoard.setText("Choose Game Board");
    MenuItem snakesAndLaddersEasy = new MenuItem("Easy Snakes and Ladders");
    MenuItem snakesAndLaddersNormal = new MenuItem("Normal Snakes and Ladders");
    MenuItem snakesAndLaddersHard = new MenuItem("Hard Snakes and Ladders");

    snakesAndLaddersEasy.setOnAction(actionEvent -> {
      gameBoardPath = Constants.SNAKES_AND_LADDERS_EASY_BOARD_FILE_PATH;
      chooseGameBoard.setText(snakesAndLaddersEasy.getText());
    });
    snakesAndLaddersNormal.setOnAction(actionEvent -> {
      gameBoardPath = Constants.SNAKES_AND_LADDERS_NORMAL_BOARD_FILE_PATH;
      chooseGameBoard.setText(snakesAndLaddersNormal.getText());
    });
    snakesAndLaddersHard.setOnAction(actionEvent -> {
      gameBoardPath = Constants.SNAKES_AND_LADDERS_HARD_BOARD_FILE_PATH;
      chooseGameBoard.setText(snakesAndLaddersHard.getText());
    });


    chooseGameBoard.getItems().addAll(snakesAndLaddersEasy,
        snakesAndLaddersNormal,
        snakesAndLaddersHard);
    return chooseGameBoard;
  }

  /**
   * Creates a button to start the game.
   *
   * <p>This button validates the user input, including the number of players,
   * their names and checking if a board has been selected.
   * If the input is valid, it creates player objects and writes them to a CSV file,
   * and sends the user to the game page.
   * If the input is invalid, it shows an alert with the error message.</p>
   *
   * @return a {@code Button} to start the game
   */
  private Button getStartGameButton() {
    Button startGameButton = new Button("Start Game");
    startGameButton.setOnAction(event -> {
      try {
        controller.validateUserInput(playerAmount.getValue(),
            chooseGameBoard.getText(),
            playerAmount.getValue(),
            playerFields);
        controller.createPlayer(playerAmount.getValue(), playerFields, listOfColors);

        alertConfirmation.setContentText("Game created successfully!");
        alertConfirmation.show();
        controller.goToGame(gameBoardPath);
      } catch (GUIInvalidNameException | InvalidPlayerFields e) {
        alertWarning.setContentText(e.getMessage());
        alertWarning.show();
      }
    });
    return startGameButton;
  }

  /**
   * Creates a FlowPane with a button to return to the game selection page.
   *
   * @return a {@code FlowPane} containing the return button
   */
  private FlowPane backToGameSelectionPage() {
    Button returnButton = ButtonFactory
        .returnButtonFactory("Back", controller::returnToGameSelection);
    FlowPane flowPane = new FlowPane();
    flowPane.getChildren().add(returnButton);
    flowPane.setAlignment(Pos.TOP_LEFT);
    return flowPane;
  }
}
