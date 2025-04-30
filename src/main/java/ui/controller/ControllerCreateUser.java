package ui.controller;

import ui.gui.game.SnakesAndLaddersPage;

public class ControllerCreateUser {
  private final SceneManager sceneManager;

  /**
   * Constructs a {@code ControllerCreateUser} with a specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for handling scene transitions
   */
  public ControllerCreateUser(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public void goToSnakesAndLadders() {
    sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager)));
  }
}
