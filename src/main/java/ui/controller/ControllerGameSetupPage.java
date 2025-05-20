package ui.controller;

import filehandler.PlayerFileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Player;
import ui.gui.game.MonopolyPage;
import ui.gui.game.SnakesAndLaddersPage;
import utils.Constants;
import utils.exception.GUIInvalidNameException;
import utils.exception.InvalidPlayerFields;

/**
 * Controller for handling game setup page interactions.
 *
 * <p>This includes player creation, game board selection,
 * loading saved games, and validation for user input.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.4
 */
public class ControllerGameSetupPage {
  private final SceneManager sceneManager;
  private final int gameId;

  /**
   * Constructs a {@code ControllerGameSetupPage} with a scene manager and game ID.
   *
   * @param sceneManager the {@code SceneManager} responsible for handling scene transitions
   * @param gameId the ID of the game (1 for Snakes and Ladders, 2 for Monopoly)
   */
  public ControllerGameSetupPage(SceneManager sceneManager, int gameId) {
    this.sceneManager = sceneManager;
    this.gameId = gameId;
  }

  /**
   * Navigates to the game page based on the game ID.
   *
   * <p>This method sets the view to the correct game page based on the game ID,
   * and uses the gameBoardPath for selecting the type of board to use for Snakes And Ladders.</p>
   *
   * @param gameBoardPath the path to the selected game board
   */
  public void goToGame(String gameBoardPath) {
    if (gameId == 1) {
      sceneManager.setView(new SnakesAndLaddersPage(
          new ControllerSnakesAndLadders(sceneManager, gameBoardPath)));
    } else if (gameId == 2) {
      sceneManager.setView(new MonopolyPage(new ControllerMonopoly(sceneManager,
          Constants.MONOPOLY_PLAYER_SAVED_CSV)));
    }
  }

  /**
   * Loads the saved game based and navigates to the game page.
   *
   * <p>This method sets the view to the correct game page based on the game ID
   * and loads the saved game data.</p>
   *
   * @throws IllegalStateException if no saved game is found
   */
  public void loadSavedGame() {
    if (!canLoadSavedGame()) {
      throw new IllegalStateException("No saved game found.");
    } else {
      if (gameId == 1) {
        sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager,
            Constants.BOARD_SAVED_FILEPATH)));
      } else if (gameId == 2) {
        sceneManager.setView(new MonopolyPage(new ControllerMonopoly(sceneManager,
            Constants.MONOPOLY_PLAYER_SAVED_CSV)));
      }
    }
  }

  /**
   * Checks if a saved game file exists and can be loaded for the selected game.
   *
   * @return {@code true} if a saved game can exists, otherwise {@code false}
   */
  private boolean canLoadSavedGame() {
    if (gameId == 1) {
      return Files.exists(Paths.get(Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV));
    } else if (gameId == 2) {
      return Files.exists(Paths.get(Constants.MONOPOLY_PLAYER_SAVED_CSV));
    }
    return false;
  }

  /**
   * Validates user input for player names and game board selection.
   *
   * <p>This method checks if the number of player fields is valid,
   * and throws an exception if the user input is invalid.</p>
   *
   * @param numberOfPlayerFields the number of player fields
   * @param gameBoardText the text of the selected game board
   * @param playerAmount the number of players
   * @param playerFields the list of player fields
   */
  public void validateUserInput(int numberOfPlayerFields,
                                String gameBoardText,
                                int playerAmount,
                                List<HBox> playerFields) {
    if (numberOfPlayerFields == 0) {
      throw new InvalidPlayerFields("Please select a number of players.");
    }

    if ("Choose Game Board".equals(gameBoardText) && gameId == 1) {
      throw new InvalidPlayerFields("Please select a game board.");
    }

    for (int i = 0; i < playerAmount; i++) {
      HBox playerFieldBox = playerFields.get(i);
      TextField playerField = (TextField) playerFieldBox.getChildren().get(1);
      String userName = playerField.getText().trim();
      if (userName.isEmpty()) {
        throw new GUIInvalidNameException("Player " + (i + 1) + " name is empty.");
      }
    }
  }

  /**
   * Creates {@link Player} objects based on user input and writes them to a CSV file.
   *
   * @param playerAmount the number of players
   * @param playerFields the list of player fields
   * @param listOfColors the list of colors for players
   */
  public void createPlayer(int playerAmount, List<HBox> playerFields, List<String> listOfColors) {
    List<Player> playerList = new ArrayList<>();

    for (int i = 0; i < playerAmount; i++) {
      HBox playerFieldBox = playerFields.get(i);
      TextField playerField = (TextField) playerFieldBox.getChildren().get(1);
      String userName = playerField.getText().trim();
      Player player = new Player(userName, listOfColors.get(i), null, 0);
      playerList.add(player);
    }
    if (gameId == 1) {
      PlayerFileWriter.writeToCsv(playerList, Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV);
    } else if (gameId == 2) {
      PlayerFileWriter.writeToCsv(playerList, Constants.MONOPOLY_PLAYER_SAVED_CSV);
    }
  }

  /**
   * Returns the ID of the selected game.
   *
   * @return the {@code SceneManager} instance
   */
  public int getGameId() {
    return gameId;
  }
}
