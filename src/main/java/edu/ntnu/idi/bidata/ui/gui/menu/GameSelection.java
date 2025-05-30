package edu.ntnu.idi.bidata.ui.gui.menu;

import edu.ntnu.idi.bidata.ui.controller.ControllerGameSelection;
import edu.ntnu.idi.bidata.ui.gui.base.BasePage;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Represents the game selection interface.
 *
 * <p>This class provides a graphical view of available games,
 * allowing users to select and start a game with a single button click.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 * @since 0.0.1
 */
public class GameSelection extends BasePage {
  private final ControllerGameSelection controller;

  /**
   * Constructs a {@code GameSelection} instance with a given game controller.
   *
   * @param controllerGameChoice the game controller responsible for game selection logic
   */
  public GameSelection(ControllerGameSelection controllerGameChoice) {
    this.controller = controllerGameChoice;
    setCenter(createGameSelectionPane());
  }

  /**
   * Creates a pane containing the game selection UI elements.
   *
   * @return a {@code Pane} containing game buttons and logo
   */
  private Pane createGameSelectionPane() {
    VBox container = new VBox();
    container.setAlignment(Pos.CENTER);
    container.setSpacing(10);
    container.setPadding(new Insets(10, 10, 10, 10));
    container.getChildren().addAll(createLogoPane(), createTitlePane());
    return container;
  }

  /**
   * Fetches and displays the application logo.
   *
   * @return a {@code FlowPane} containing the logo image
   */
  private FlowPane createLogoPane() {
    FlowPane logoPane = new FlowPane();
    ImageView logo = new ImageView(Objects
        .requireNonNull(getClass()
            .getResource("/image/logo.png"))
        .toExternalForm());
    logoPane.getChildren().add(logo);
    logoPane.setAlignment(Pos.CENTER);
    return logoPane;
  }

  /**
   * Creates the title pane containing the game selection label and buttons.
   *
   * @return a {@code VBox} containing title and buttons
   */
  private VBox createTitlePane() {
    VBox titlePane = new VBox();
    Label title = new Label("Choose a Game");
    title.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
    titlePane.getChildren().addAll(title, createButtonPane());
    titlePane.setAlignment(Pos.CENTER);
    return titlePane;
  }

  /**
   * Creates the button pane containing options to start a game.
   *
   * @return an {@code HBox} containing game selection buttons
   */
  private HBox createButtonPane() {
    HBox buttonPane = new HBox();
    buttonPane.setSpacing(30);

    Button snakesLaddersButton = new Button("Snakes and Ladders");
    snakesLaddersButton.setPrefHeight(30);
    snakesLaddersButton.setPrefWidth(300);

    Button monopolyButton = new Button("Monopoly");
    monopolyButton.setPrefHeight(30);
    monopolyButton.setPrefWidth(300);

    snakesLaddersButton.setOnAction(event ->  controller.goToCreateUser(1));
    monopolyButton.setOnAction(event -> controller.goToCreateUser(2));

    buttonPane.getChildren().addAll(snakesLaddersButton, monopolyButton);
    buttonPane.setAlignment(Pos.CENTER);
    return buttonPane;
  }
}
