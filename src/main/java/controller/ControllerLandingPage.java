package controller;

import gui.LandingPage;

/**
 * The {@code ControllerLandingPage} class serves as a logical
 * class for the {@link LandingPage}.
 *
 * <p>It provides a method that allows the user
 * to gracefully exit the application.
 * </p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.2.1
 */
public class ControllerLandingPage {
  private final LandingPage landingPage;

  /**
   * Constructs a {@code ControllerLandingPage}.
   *
   * @param landingPage the user interface component responsible for the landing page.
   */
  public ControllerLandingPage(LandingPage landingPage) {
    this.landingPage = landingPage;
  }

  /**
   * Gracefully exits the application and it
   * does this by {@code System.exit(0)}.
   *
   */
  public void exitApplication() {
    System.exit(0);
  }
}
