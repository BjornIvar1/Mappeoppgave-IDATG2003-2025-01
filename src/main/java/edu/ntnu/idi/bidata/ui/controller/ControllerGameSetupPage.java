package edu.ntnu.idi.bidata.ui.controller;

import edu.ntnu.idi.bidata.filehandler.player.PlayerFileWriter;
import edu.ntnu.idi.bidata.model.entity.Player;
import edu.ntnu.idi.bidata.ui.gui.game.MonopolyPage;
import edu.ntnu.idi.bidata.ui.gui.game.SnakesAndLaddersPage;
import edu.ntnu.idi.bidata.ui.gui.menu.GameSelection;
import edu.ntnu.idi.bidata.utils.Constants;
import edu.ntnu.idi.bidata.utils.exception.GUIInvalidNameException;
import edu.ntnu.idi.bidata.utils.exception.InvalidPlayerFields;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Controller for handling game setup page interactions.
 *
 * <p>This includes handling player creation, game board selection,
 * loading saved games, and validation for user input.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.5
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
          new ControllerSnakesAndLadders(sceneManager, gameBoardPath, Constants.PLAYER_FILE_PATH)));
    } else if (gameId == 2) {
      sceneManager.setView(new MonopolyPage(new ControllerMonopoly(sceneManager,
          Constants.PLAYER_FILE_PATH)));
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
      throw new IllegalStateException("No saved game found or the file is empty.");
    } else {
      if (gameId == 1) {
        sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager,
            Constants.BOARD_SAVED_FILEPATH, Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV)));
      } else if (gameId == 2) {
        sceneManager.setView(new MonopolyPage(new ControllerMonopoly(sceneManager,
            Constants.MONOPOLY_PLAYER_SAVED_CSV)));
      }
    }
  }

  /**
   * Checks if a file can be loaded.
   *
   * <p>Checks if a saved game file exists for the selected game,
   * and is not empty.</p>
   *
   * @return {@code true} if a saved game can exists, otherwise {@code false}
   */
  private boolean canLoadSavedGame() {
    File snakesFile = Paths.get(Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV).toFile();
    File monopolyFile = Paths.get(Constants.MONOPOLY_PLAYER_SAVED_CSV).toFile();
    if (gameId == 1) {
      return snakesFile.exists() && snakesFile.length() > 0;
    } else if (gameId == 2) {
      return monopolyFile.exists() && monopolyFile.length() > 0;
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

    if (Constants.LABEL_CHOOSE_GAME_BOARD.equals(gameBoardText) && gameId == 1) {
      throw new InvalidPlayerFields("Please select a game board.");
    }

    for (int i = 0; i < playerAmount; i++) {
      HBox playerFieldBox = playerFields.get(i);
      TextField playerField = (TextField) playerFieldBox.getChildren().get(1);
      String userName = playerField.getText().trim();
      if (userName.isEmpty()) {
        throw new GUIInvalidNameException("Player " + (i + 1) + " name is empty.");
      } else if (!userName.matches("^[A-Za-z0-9]+$")) {
        throw new GUIInvalidNameException("Player " + (i + 1)
            + " name contains invalid characters."
            + "\nOnly Letters and numbers are allowed.");
      }
    }
  }

  /**
   * Creates {@link Player} object based on user input and writes them to a CSV file.
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
      Player player = new Player(userName, listOfColors.get(i), null);
      playerList.add(player);
    }
    PlayerFileWriter.writeToCsv(playerList, Constants.PLAYER_FILE_PATH);
  }

  /**
   * Returns the ID of the selected game.
   *
   * @return the {@code SceneManager} instance
   */
  public int getGameId() {
    return gameId;
  }

  /**
   * Navigates back to the game selection page.
   *
   * <p>This method enables a button to return to the game selection page.</p>
   */
  public void returnToGameSelection() {
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }
}
