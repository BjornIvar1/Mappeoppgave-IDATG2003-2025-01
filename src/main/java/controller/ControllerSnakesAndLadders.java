package controller;

import gui.menu.GameSelection;

/**
 * Controller for the Snakes and Ladders game.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.1.0
 */
public class ControllerSnakesAndLadders {
  private final SceneManager sceneManager;

  /**
   * Constructs a {@code ControllerSnakesAndLadders} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerSnakesAndLadders (SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public void switchToGameSelection() {
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }

  //TODO move logical methods here.
}
