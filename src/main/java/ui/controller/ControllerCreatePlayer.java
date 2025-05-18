package ui.controller;

import ui.gui.game.MonopolyPage;
import ui.gui.game.SnakesAndLaddersPage;

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
  private final int gameID;

  /**
   * Constructs a {@code ControllerCreatePlayer} with a specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for handling scene transitions
   */
  public ControllerCreatePlayer(SceneManager sceneManager, int gameID) {
    this.sceneManager = sceneManager;
    this.gameID = gameID;
  }

  public void goToGame(String gameBoardPath) {
    if (gameID == 1) {
      sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager, gameBoardPath)));
    } else if (gameID == 2) {
      sceneManager.setView(new MonopolyPage(new ControllerMonopoly(sceneManager)));
    }
  }

  public int getGameID() {
    return gameID;
  }
}
