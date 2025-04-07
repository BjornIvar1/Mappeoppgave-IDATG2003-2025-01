package kontroller;

import gui.GameChoice;
/**
 * Controls the landing page behavior and scene transitions.
 *
 * <p>This class manages navigation from the landing page to the game selection interface.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.3
 */
public class ControllerLandingPage {
  private final SceneManager sceneManager;

  /**
   * Constructs a {@code ControllerLandingPage} with a specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for handling scene transitions
   */
  public ControllerLandingPage(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  /**
   * Switches to the game selection view.
   *
   * <p>This method updates the scene to display the {@code GameChoice} interface,
   * allowing users to choose and start a game.</p>
   */
  public void switchToGameChoice() {
    sceneManager.setView(new GameChoice(new ControllerGameChoice(sceneManager)));
  }
}
