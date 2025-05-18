package ui.controller;

import ui.gui.menu.CreateUser;

/**
 * Manages game selection interactions.
 *
 * <p>This controller facilitates navigation and scene management related to game choices.
 * It allows the user to choose and start a game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.4
 */
public class ControllerGameSelection {
  private final SceneManager sceneManager;

  /**
   * Constructs a {@code ControllerGameSelection} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerGameSelection(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  /**
   * Navigates to the create user page.
   *
   * @param gameID the ID of the game to be selected
   */
  public void goToCreateUser(int gameID) {
    sceneManager.setView(new CreateUser(new ControllerCreateUser(sceneManager, gameID)));
  }
}

