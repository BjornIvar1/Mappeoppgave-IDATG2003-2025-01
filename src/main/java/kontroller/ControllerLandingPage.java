package kontroller;

import gui.GameChoice;

public class ControllerLandingPage {
  private SceneManager sceneManager;

  public ControllerLandingPage(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public void switchToGameChoice() {
    sceneManager.setView(new GameChoice());
  }

}
