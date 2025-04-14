package controller;

import gui.game.MonopolyPage;
import gui.game.SnakesAndLaddersPage;

/**
 * Manages game selection interactions.
 *
 * <p>This controller facilitates navigation and scene management related to game choices.
 * It allows the user to choose and start a game.</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.0.3
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
   * Navigates to the Snakes and Ladders game page.
   *
   * <p>This method updates the scene to display the {@code SnakesAndLaddersPage} interface,
   * allowing users to play the game.</p>
   */
  public void goToSnakesAndLadders() {
    sceneManager.setView(new SnakesAndLaddersPage(new ControllerSnakesAndLadders(sceneManager)));
  }

  public void goToMonopoly() {
    MonopolyPage monopolyPage = new MonopolyPage(new kontroller.ControllerMonopoly(sceneManager));
    sceneManager.setView(monopolyPage);
  }

}

