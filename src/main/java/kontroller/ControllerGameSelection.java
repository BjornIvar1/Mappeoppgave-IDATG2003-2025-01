package kontroller;

import gui.game.SnakesAndLaddersPage;

/**
 * Manages game selection interactions.
 *
 * <p>This controller facilitates navigation and scene management related to game choices.
 * It allows the user to choose and start a game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.2
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

  public void goToSnakesAndLadders() {
    sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager)));
  }

}

