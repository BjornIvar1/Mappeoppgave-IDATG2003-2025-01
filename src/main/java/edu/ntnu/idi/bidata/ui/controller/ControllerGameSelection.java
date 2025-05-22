package edu.ntnu.idi.bidata.ui.controller;

import edu.ntnu.idi.bidata.ui.gui.menu.GameSetupPage;

/**
 * Manages game selection interactions.
 *
 * <p>Allows the user to select a game and navigates to the game setup page.</p>
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
   * @param gameId the ID of the game to be selected
   */
  public void goToCreateUser(int gameId) {
    sceneManager.setView(new GameSetupPage(new ControllerGameSetupPage(sceneManager, gameId)));
  }
}

