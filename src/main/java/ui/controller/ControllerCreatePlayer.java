package ui.controller;

import filehandler.PlayerFileWriter;
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
 * Manages user creation interactions.
 *
 * <p>This controller facilitates navigation and scene management related to user creation.
 * It allows the user to create a new player and start a game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.4
 */
public class ControllerCreatePlayer {
  private final SceneManager sceneManager;
  private final int gameId;

  /**
   * Constructs a {@code ControllerCreatePlayer} with a specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for handling scene transitions
   */
  public ControllerCreatePlayer(SceneManager sceneManager, int gameId) {
    this.sceneManager = sceneManager;
    this.gameId = gameId;
  }

  /**
   * Navigates to the game page based on the game ID.
   *
   * <p>This method sets the view to the correct game page based on the game ID,
   * and uses the gameBoardPath for selecting the type of board to use.</p>
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
   * Navigates to the last game saved.
   *
   * <p>This method sets the view to the correct game page based on the game ID</p>
   */
  public void goToGameSelection() {
    if (gameId == 1) {
      sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager,
          Constants.BOARD_SAVED_FILEPATH, Constants.SNAKES_AND_LADDERS_PLAYER_SAVED_CSV)));
    } else if (gameId == 2) {
      sceneManager.setView(new MonopolyPage(new ControllerMonopoly(sceneManager,
          Constants.MONOPOLY_PLAYER_SAVED_CSV)));
    }
  }

  /**
   * Validates user input for player names and game board selection.
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
   * Creates player objects based on user input and writes them to a CSV file.
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
    PlayerFileWriter.writeToCsv(playerList, Constants.PLAYER_FILE_PATH);
  }

  /**
   * Returns the {@code SceneManager} associated with this controller.
   *
   * @return the {@code SceneManager} instance
   */
  public int getGameId() {
    return gameId;
  }
}
