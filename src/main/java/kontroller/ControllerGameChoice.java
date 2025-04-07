package kontroller;

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
public class ControllerGameChoice {
  private final SceneManager sceneManager;

  /**
   * Constructs a {@code ControllerGameChoice} with the specified scene manager.
   *
   * @param sceneManager the {@code SceneManager} responsible for managing scene transitions
   */
  public ControllerGameChoice(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }
}

