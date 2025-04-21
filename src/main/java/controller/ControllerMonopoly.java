package controller;

import gui.menu.GameSelection;

/**
 * Represents the controller for the Monopoly game.
 *
 * <p>This class is responsible for managing the interactions and logic specific to the
 * Monopoly game.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.1.0
 */
public class ControllerMonopoly {
  private final SceneManager sceneManager;

  /**
   * Constructs a {@code ControllerMonopoly} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerMonopoly(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public void switchToGameSelection() {
    sceneManager.setView(new GameSelection(new ControllerGameSelection(sceneManager)));
  }

  //TODO move logical methods to this class.
}
